package wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wallet.domain.Player;
import wallet.domain.Wallet;
import wallet.domain.WalletTransaction;
import wallet.exception.WalletException;
import wallet.repository.WalletRepository;
import wallet.repository.WalletTransactionRepository;
import wallet.service.dto.WalletDto;
import wallet.service.dto.WalletTransactionDto;
import wallet.service.mapper.TransactionMapper;
import wallet.service.mapper.WalletMapper;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final PlayerService playerService;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;

    private final UniqueConstraintHandler uniqueConstraintHandler;

    private final TransactionalHelper transactionalHelper;

    @Override
    public WalletDto createWallet(@NotNull Long playerId) {
        return transactionalHelper.createTransactionAndProceed(() -> createNewWallet(playerId));
    }

    private WalletDto createNewWallet(@NotNull Long playerId) {
        final Player player = playerService.getPlayerById(playerId);
        final Wallet wallet = new Wallet();
        wallet.setPlayer(player);
        wallet.setRegistrationDate(LocalDateTime.now());
        wallet.setAmount(BigDecimal.ZERO);
        walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    public WalletDto transfer(Long walletId, WalletTransactionDto dto) {
        return uniqueConstraintHandler.execute(() -> makeTransfer(walletId, dto),
                WalletTransaction.TRANSACTIONS_BUSINESS_ID_UNIQUE_INDEX_NAME,
                e -> transactionAlreadyExitsException(dto));
    }

    private WalletDto makeTransfer(Long walletId, WalletTransactionDto dto) throws ConstraintViolationException {
        final Wallet wallet =
                walletRepository.getWalletByIdForUpdate(walletId)
                        .orElseThrow(() -> new WalletException(HttpStatus.NOT_FOUND, "wallet not exists"));

        if (isTransactionExists(dto)) {
            throw transactionAlreadyExitsException(dto);
        }
        final BigDecimal newAmount = getNewAmount(dto, wallet);

        wallet.setAmount(newAmount);
        // After transaction will try to commit we still can get DataIntegrityViolationException, cos we are using
        // READ_COMMITTED isolation level and cover only case when somebody tries to create walletTransaction with
        // same business id in same Wallet(cos we get lock for current one), but not for others.
        // For this reason UniqueConstraintHandlerImpl was added to handle such cases.
        walletRepository.save(wallet);
        final WalletTransaction walletTransaction = transactionMapper.toEntity(dto);
        walletTransaction.setWallet(wallet);
        walletTransactionRepository.save(walletTransaction);
        return walletMapper.toDto(wallet);
    }

    private WalletException transactionAlreadyExitsException(WalletTransactionDto dto) {
        return new WalletException(HttpStatus.CONFLICT,
                String.format("WalletTransaction with id %s already exists", dto.businessId().toString()));
    }

    private BigDecimal getNewAmount(WalletTransactionDto dto, Wallet wallet) {
        final BigDecimal newAmount = switch (dto.type()) {
            case DEBIT -> wallet.getAmount().subtract(dto.amount());
            case CREDIT -> wallet.getAmount().add(dto.amount());
        };

        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new WalletException(HttpStatus.NOT_ACCEPTABLE, "Not enough money");
        }
        return newAmount;
    }

    private boolean isTransactionExists(WalletTransactionDto dto) {
        return walletTransactionRepository.existsByBusinessId(dto.businessId());
    }

    @Override
    public List<WalletTransactionDto> getAllTransactions(Long walletId) {
        return transactionalHelper.createReadTransactionAndProceed(() -> getTransactions(walletId));
    }

    private List<WalletTransactionDto> getTransactions(Long walletId) {
        return transactionMapper.toDto(walletTransactionRepository.findByWalletId(walletId));
    }
}

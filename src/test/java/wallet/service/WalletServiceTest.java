package wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import wallet.domain.TransactionType;
import wallet.domain.Wallet;
import wallet.exception.WalletException;
import wallet.repository.WalletRepository;
import wallet.repository.WalletTransactionRepository;
import wallet.service.dto.WalletDto;
import wallet.service.dto.WalletTransactionDto;
import wallet.service.mapper.TransactionMapper;
import wallet.service.mapper.TransactionMapperImpl;
import wallet.service.mapper.WalletMapper;
import wallet.service.mapper.WalletMapperImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WalletServiceTest {

    private final WalletMapper walletMapper = new WalletMapperImpl();
    private final TransactionMapper transactionMapper = new TransactionMapperImpl();
    private final WalletTransactionDto
            input = new WalletTransactionDto(UUID.randomUUID(), TransactionType.DEBIT, BigDecimal.ONE);
    private WalletServiceImpl walletService;
    @Mock
    private WalletTransactionRepository walletTransactionRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
        TransactionalHelper helper = new TransactionalHelper();
        walletService =
                new WalletServiceImpl(walletTransactionRepository, playerService, walletRepository, walletMapper,
                        transactionMapper, new UniqueConstraintHandler(helper), helper);
    }

    @Test
    void createWallet() {
        WalletDto actual = walletService.createWallet(1L);
        LocalDateTime time = LocalDateTime.now();
        WalletDto expected = new WalletDto(null, BigDecimal.ZERO, LocalDateTime.now());

        assertEquals(expected.amount(), actual.amount());
        assertTrue(time.isAfter(actual.registrationDate()));
    }

    @Test
    void transferPositiveCredit() {
        final WalletTransactionDto
                input = new WalletTransactionDto(UUID.randomUUID(), TransactionType.CREDIT, BigDecimal.ONE);
        final Wallet wallet = generateWallet(BigDecimal.ZERO);
        when(walletRepository.getWalletByIdForUpdate(any())).thenReturn(Optional.of(wallet));
        final WalletDto actual = walletService.transfer(1L, input);

        assertEquals(BigDecimal.ONE, actual.amount());
    }

    @Test
    void transferPositiveDebit() {
        final Wallet wallet = generateWallet(BigDecimal.ONE);
        when(walletRepository.getWalletByIdForUpdate(any())).thenReturn(Optional.of(wallet));
        final WalletDto actual = walletService.transfer(1L, input);

        assertEquals(BigDecimal.ZERO, actual.amount());
    }

    @Test
    void transferDebitNotEnoughMoney() {
        final Wallet wallet = generateWallet(BigDecimal.ZERO);
        when(walletRepository.getWalletByIdForUpdate(any())).thenReturn(Optional.of(wallet));

        final Exception ex = assertThrows(WalletException.class, () -> walletService.transfer(1L, input));
        assertEquals("406 Not enough money", ex.getMessage());
    }

    @Test
    void transferWalletNotExists() {
        when(walletRepository.getWalletByIdForUpdate(any())).thenReturn(Optional.empty());

        final Exception ex = assertThrows(WalletException.class, () -> walletService.transfer(1L, input));
        assertEquals("404 wallet not exists", ex.getMessage());
    }

    @Test
    void transferTransactionAlreadyExists() {
        final Wallet wallet = generateWallet(BigDecimal.ZERO);
        when(walletRepository.getWalletByIdForUpdate(any())).thenReturn(Optional.of(wallet));
        when(walletTransactionRepository.existsByBusinessId(any())).thenReturn(true);
        final Exception ex = assertThrows(WalletException.class, () -> walletService.transfer(1L, input));
        assertEquals(String.format("409 WalletTransaction with id %s already exists", input.businessId().toString()),
                ex.getMessage());
    }

    private Wallet generateWallet(BigDecimal initialAmount) {
        Wallet dbWallet = new Wallet();
        dbWallet.setRegistrationDate(LocalDateTime.now());
        dbWallet.setAmount(initialAmount);
        return dbWallet;
    }

}
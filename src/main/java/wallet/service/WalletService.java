package wallet.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import wallet.service.dto.WalletDto;
import wallet.service.dto.WalletTransactionDto;

public interface WalletService {

    WalletDto createWallet(@NotNull Long playerId);

    WalletDto transfer(@NotNull Long walletId, @NotNull WalletTransactionDto dto);

    List<WalletTransactionDto> getAllTransactions(@NotNull Long walletId);
}

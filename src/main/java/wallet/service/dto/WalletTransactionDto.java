package wallet.service.dto;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import wallet.domain.TransactionType;

public record WalletTransactionDto(@NotNull UUID businessId, @NotNull TransactionType type,
                                   @NotNull @DecimalMin(value = "0.01", message = "amount should be greater than 0") BigDecimal amount) {
}

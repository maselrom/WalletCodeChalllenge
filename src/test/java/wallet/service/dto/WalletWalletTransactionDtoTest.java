package wallet.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wallet.service.dto.ValidatorTest.validator;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import wallet.domain.TransactionType;

class WalletWalletTransactionDtoTest {

    @Test
    void invalidTransaction() {
        WalletTransactionDto walletTransactionDto = new WalletTransactionDto(null, null, null);
        Set<ConstraintViolation<WalletTransactionDto>> violations = validator.validate(walletTransactionDto);
        assertEquals(3, violations.size());
    }

    @Test
    void amountInvalid() {
        WalletTransactionDto walletTransactionDto =
                new WalletTransactionDto(UUID.randomUUID(), TransactionType.DEBIT, BigDecimal.ZERO);
        Set<ConstraintViolation<WalletTransactionDto>> violations = validator.validate(walletTransactionDto);
        assertEquals(1, violations.size());
    }

    @Test
    void transactionValid() {
        WalletTransactionDto walletTransactionDto =
                new WalletTransactionDto(UUID.randomUUID(), TransactionType.DEBIT, BigDecimal.ONE);
        Set<ConstraintViolation<WalletTransactionDto>> violations = validator.validate(walletTransactionDto);
        assertTrue(violations.isEmpty());
    }

}
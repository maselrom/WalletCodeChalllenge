package wallet.service.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static wallet.service.dto.ValidatorTest.validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

class WalletDtoTest {

    @Test
    void validWalletDto() {
        WalletDto walletDto =
                new WalletDto(1L, BigDecimal.ONE, LocalDateTime.now());
        Set<ConstraintViolation<WalletDto>> violations = validator.validate(walletDto);
        assertTrue(violations.isEmpty());
    }
}
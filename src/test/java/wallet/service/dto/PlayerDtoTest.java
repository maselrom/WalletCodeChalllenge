package wallet.service.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wallet.service.dto.ValidatorTest.validator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

class PlayerDtoTest {

    @Test
    void nameNull() {
        PlayerDto playerDto = new PlayerDto(null);
        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nameBlank() {
        PlayerDto playerDto = new PlayerDto("    ");
        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nameValid() {
        PlayerDto playerDto = new PlayerDto("Name");
        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);
        assertTrue(violations.isEmpty());
    }

}
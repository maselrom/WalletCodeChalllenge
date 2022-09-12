package wallet.service.dto;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorTest {
    final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
}

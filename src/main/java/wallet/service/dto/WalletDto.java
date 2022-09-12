package wallet.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WalletDto(Long id, @NotNull BigDecimal amount,
                        @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDateTime registrationDate) {
}

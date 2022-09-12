package wallet.service.dto;

import javax.validation.constraints.NotBlank;

public record PlayerDto(@NotBlank String name) {
}

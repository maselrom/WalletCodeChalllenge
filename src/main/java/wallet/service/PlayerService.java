package wallet.service;

import javax.validation.constraints.NotNull;
import wallet.domain.Player;
import wallet.service.dto.PlayerDto;

public interface PlayerService {
    PlayerDto registerPlayer(@NotNull PlayerDto playerDto);

    Player getPlayerById(@NotNull Long playerId);
}

package wallet.service;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wallet.domain.Player;
import wallet.exception.WalletException;
import wallet.repository.PlayerRepository;
import wallet.service.dto.PlayerDto;
import wallet.service.mapper.PlayerMapper;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    private final TransactionalHelper transactionalHelper;

    @Override
    public PlayerDto registerPlayer(@NotNull PlayerDto playerDto) {
        return transactionalHelper.createTransactionAndProceed(() -> registerNewPlayer(playerDto));
    }

    private PlayerDto registerNewPlayer(@NotNull PlayerDto playerDto) {
        final Player player = mapper.toEntity(playerDto);
        player.setRegistrationDate(LocalDateTime.now());
        repository.save(player);
        return mapper.toDto(player);
    }

    @Override
    public Player getPlayerById(@NotNull Long playerId) {
        return repository.findById(playerId)
                .orElseThrow(() -> new WalletException(HttpStatus.NOT_FOUND, "Player not found, id:" + playerId));
    }
}

package wallet.service.mapper;

import org.mapstruct.Mapper;
import wallet.domain.Player;
import wallet.service.dto.PlayerDto;

@Mapper(componentModel = "spring", uses = { PlayerMapper.class })
public interface PlayerMapper extends EntityMapper<PlayerDto, Player> {

    @Override
    Player toEntity(PlayerDto dto);

    @Override
    PlayerDto toDto(Player entity);
}

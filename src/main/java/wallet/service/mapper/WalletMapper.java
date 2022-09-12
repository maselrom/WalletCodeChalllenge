package wallet.service.mapper;

import org.mapstruct.Mapper;
import wallet.domain.Wallet;
import wallet.service.dto.WalletDto;

@Mapper(componentModel = "spring", uses = { WalletMapper.class })
public interface WalletMapper extends EntityMapper<WalletDto, Wallet> {
    @Override
    Wallet toEntity(WalletDto dto);

    @Override
    WalletDto toDto(Wallet entity);

}

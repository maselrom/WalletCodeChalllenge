package wallet.service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import wallet.domain.WalletTransaction;
import wallet.service.dto.WalletTransactionDto;

@Mapper(componentModel = "spring", uses = { TransactionMapper.class })
public interface TransactionMapper extends EntityMapper<WalletTransactionDto, WalletTransaction> {
    @Override
    WalletTransaction toEntity(WalletTransactionDto dto);

    @Override
    WalletTransactionDto toDto(WalletTransaction entity);

    @Override
    List<WalletTransactionDto> toDto(List<WalletTransaction> entityList);
}

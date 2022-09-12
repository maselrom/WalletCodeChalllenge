package wallet.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wallet.domain.WalletTransaction;

@Repository
public interface WalletTransactionRepository extends CrudRepository<WalletTransaction, Long> {

    boolean existsByBusinessId(UUID businessId);

    List<WalletTransaction> findByWalletId(Long walletId);
}

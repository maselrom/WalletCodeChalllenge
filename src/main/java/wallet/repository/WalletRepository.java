package wallet.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import wallet.domain.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "500") })
    Optional<Wallet> getWalletById(Long id);

    //JPA repository can't generate ForUpdate from method name, so adding default method with clear name
    default Optional<Wallet> getWalletByIdForUpdate(Long id) {
        return getWalletById(id);
    }
}

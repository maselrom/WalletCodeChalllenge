package wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}

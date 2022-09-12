package wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @OneToMany
    @JoinColumn(name = "wallet")
    private List<WalletTransaction> walletTransactions;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

}

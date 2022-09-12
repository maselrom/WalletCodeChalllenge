package wallet.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transactions", indexes = @Index(name = WalletTransaction.TRANSACTIONS_BUSINESS_ID_UNIQUE_INDEX_NAME, columnList = "business_id", unique = true))
public class WalletTransaction {

    public static final String TRANSACTIONS_BUSINESS_ID_UNIQUE_INDEX_NAME = "transactions_business_id_i";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "business_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID businessId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
}

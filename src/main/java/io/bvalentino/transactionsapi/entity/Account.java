package io.bvalentino.transactionsapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_accounts")
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, length = 11)
    private String documentNumber;

    @Column(nullable = false)
    private BigDecimal availableCreditLimit;

    @Deprecated
    public Account() {}

    public void updateLimit(BigDecimal limit) {
        this.availableCreditLimit = limit;
    }

}

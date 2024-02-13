package io.bvalentino.transactionsapi.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Deprecated
    public Account() {}

}

package io.bvalentino.transactionsapi.repository;

import io.bvalentino.transactionsapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByDocumentNumber(String documentNumber);

}

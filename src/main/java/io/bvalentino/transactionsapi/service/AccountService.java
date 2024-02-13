package io.bvalentino.transactionsapi.service;

import io.bvalentino.transactionsapi.entity.Account;

public interface AccountService {

    Account create(Account account);
    Account findAccountById(Long id);

}

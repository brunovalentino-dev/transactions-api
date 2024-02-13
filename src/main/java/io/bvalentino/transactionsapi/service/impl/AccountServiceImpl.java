package io.bvalentino.transactionsapi.service.impl;

import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.exception.AccountRegisteredException;
import io.bvalentino.transactionsapi.repository.AccountRepository;
import io.bvalentino.transactionsapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        var documentNumber = account.getDocumentNumber();
        var accountExists = accountRepository.existsByDocumentNumber(documentNumber);

        if (accountExists) {
            throw new AccountRegisteredException(String.format("Account %s already registered!", documentNumber));
        }

        return accountRepository.save(account);
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
    }

}

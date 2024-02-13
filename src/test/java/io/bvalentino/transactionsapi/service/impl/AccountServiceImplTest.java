package io.bvalentino.transactionsapi.service.impl;

import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.exception.AccountRegisteredException;
import io.bvalentino.transactionsapi.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Must return an account when requesting one by its ID.")
    void testCase1() {
        var account = Account.builder()
            .accountId(1L)
            .documentNumber("11122233344")
        .build();

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        var response = accountServiceImpl.findAccountById(1L);

        assertEquals(1L, response.getAccountId());
        assertEquals("11122233344", response.getDocumentNumber());
    }

    @Test
    @DisplayName("Must throw an exception when an account has not been found.")
    void testCase2() {
        when(accountRepository.findById(anyLong()))
            .thenThrow(new AccountNotFoundException("Account not found!"));

        var response = assertThrows(AccountNotFoundException.class, () -> accountServiceImpl.findAccountById(1L));

        assertEquals("Account not found!", response.getMessage());
    }

    @Test
    @DisplayName("Must return an account when creating a new one.")
    void testCase3() {
        var account = Account.builder()
            .accountId(1L)
            .documentNumber("11122233344")
        .build();

        when(accountRepository.existsByDocumentNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        var response = accountServiceImpl.create(account);

        assertEquals(1L, response.getAccountId());
        assertEquals("11122233344", response.getDocumentNumber());
    }

    @Test
    @DisplayName("Must throw an exception when registering an account that already was registered.")
    void testCase4() {
        var account = Account.builder()
            .accountId(1L)
            .documentNumber("11122233344")
        .build();

        when(accountRepository.existsByDocumentNumber(anyString())).thenReturn(true);

        var response = assertThrows(AccountRegisteredException.class,
            () -> accountServiceImpl.create(account));

        assertEquals("Account 11122233344 already registered!", response.getMessage());
    }

}
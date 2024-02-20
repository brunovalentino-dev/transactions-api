package io.bvalentino.transactionsapi.service.impl;

import io.bvalentino.transactionsapi.controller.TransactionController.TransactionRequest;
import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.entity.OperationType;
import io.bvalentino.transactionsapi.entity.Transaction;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.exception.InsufficientLimitException;
import io.bvalentino.transactionsapi.exception.InvalidOperationTypeException;
import io.bvalentino.transactionsapi.repository.AccountRepository;
import io.bvalentino.transactionsapi.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Must throw an exception when registering a transaction for an inexistent account.")
    void testCase1() {
        var request = new TransactionRequest(1L, 1L, BigDecimal.valueOf(50.00));

        when(accountRepository.findById(anyLong()))
            .thenThrow(new AccountNotFoundException("Account not found!"));

        var response = assertThrows(AccountNotFoundException.class, () -> transactionServiceImpl.register(request));

        assertEquals("Account not found!", response.getMessage());
    }

    @Test
    @DisplayName("Must throw an exception when registering a transaction for an inexistent operation type.")
    void testCase2() {
        var request = new TransactionRequest(1L, 5L, BigDecimal.valueOf(50.00));

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(Account.builder().build()));

        var response = assertThrows(InvalidOperationTypeException.class, () -> transactionServiceImpl.register(request));

        assertEquals("Operation Type invalid!", response.getMessage());
    }

    @Test
    @DisplayName("Must register a transaction successfully.")
    void testCase3() {
        var request = new TransactionRequest(1L, 2L, BigDecimal.valueOf(50.00));

        var account = Account.builder()
            .accountId(1L)
            .documentNumber("11122233344")
            .availableCreditLimit(BigDecimal.valueOf(100.00))
        .build();

        var transaction = Transaction.builder()
            .transactionId(1L)
            .operationType(OperationType.COMPRA_PARCELADA)
            .account(account)
            .amount(BigDecimal.valueOf(50.00).negate())
        .build();

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        var response = transactionServiceImpl.register(request);

        assertEquals(1L, response.getAccount().getAccountId());
        assertEquals("11122233344", response.getAccount().getDocumentNumber());
        assertEquals(OperationType.COMPRA_PARCELADA, response.getOperationType());
        assertEquals(BigDecimal.valueOf(-50.0), response.getAmount());
    }

    @Test
    @DisplayName("Must throw an exception when trying to register a transaction with not enough limit.")
    void testCase4() {
        var request = new TransactionRequest(1L, 2L, BigDecimal.valueOf(5000.01));

        var account = Account.builder()
            .accountId(1L)
            .documentNumber("11122233344")
            .availableCreditLimit(BigDecimal.valueOf(5000.00))
        .build();

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        var response = assertThrows(InsufficientLimitException.class, () -> transactionServiceImpl.register(request));

        assertEquals("Limit not supported!", response.getMessage());
    }

}
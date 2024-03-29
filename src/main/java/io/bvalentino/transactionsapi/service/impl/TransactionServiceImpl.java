package io.bvalentino.transactionsapi.service.impl;

import io.bvalentino.transactionsapi.controller.TransactionController.TransactionRequest;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.exception.InsufficientLimitException;
import io.bvalentino.transactionsapi.exception.InvalidOperationTypeException;
import io.bvalentino.transactionsapi.entity.OperationType;
import io.bvalentino.transactionsapi.entity.Transaction;
import io.bvalentino.transactionsapi.repository.AccountRepository;
import io.bvalentino.transactionsapi.repository.TransactionRepository;
import io.bvalentino.transactionsapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Transaction register(TransactionRequest request) {
        var account = accountRepository.findById(request.accountId())
            .orElseThrow(() -> new AccountNotFoundException("Account not found!"));

        var accountLimit = account.getAvailableCreditLimit();

        var operationType = OperationType.findByOperationTypeId(request.operationTypeId())
            .orElseThrow(() -> new InvalidOperationTypeException("Operation Type invalid!"));

        var amount = operationType.defineOperation(request.amount());

        var limitAfterTransaction = accountLimit.add(amount);

        if (limitAfterTransaction.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientLimitException("Limit not supported!");
        }

        account.updateLimit(limitAfterTransaction);

        var transaction = Transaction.builder()
            .account(account)
            .operationType(operationType)
            .amount(amount)
        .build();

        return transactionRepository.save(transaction);
    }

}

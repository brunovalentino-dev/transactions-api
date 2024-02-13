package io.bvalentino.transactionsapi.service;

import io.bvalentino.transactionsapi.controller.TransactionController;
import io.bvalentino.transactionsapi.controller.TransactionController.TransactionRequest;
import io.bvalentino.transactionsapi.entity.Transaction;

public interface TransactionService {

    Transaction register(TransactionRequest request);

}

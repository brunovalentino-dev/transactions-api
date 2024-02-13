package io.bvalentino.transactionsapi.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bvalentino.transactionsapi.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Transactional
    @PostMapping
    public ResponseEntity<?> registerTransaction(
        @RequestBody @Valid TransactionRequest request,
        UriComponentsBuilder builder) {
        var createdTransaction = transactionService.register(request);
        var uri = builder.path("/transactions/{transactionId}").buildAndExpand(createdTransaction.getTransactionId()).toUri();

        return ResponseEntity.created(uri).body("Transaction registered successfully!!!");
    }

    public record TransactionRequest(
        @NotNull
        @JsonProperty("account_id")
        Long accountId,

        @NotNull
        @JsonProperty("operation_type_id")
        Long operationTypeId,

        @NotNull
        @Positive
        BigDecimal amount
    ) {}

}

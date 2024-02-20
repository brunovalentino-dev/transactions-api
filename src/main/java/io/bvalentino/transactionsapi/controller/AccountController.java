package io.bvalentino.transactionsapi.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Transactional
    @PostMapping
    public ResponseEntity<?> createAccount(
        @RequestBody @Valid AccountRequest request,
        UriComponentsBuilder builder) {
        var createdAccount = accountService.create(request.toEntity());
        var uri = builder.path("/accounts/{accountId}").buildAndExpand(createdAccount.getAccountId()).toUri();

        return ResponseEntity.created(uri).body("Account created successfully!!!");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable Long accountId) {
        var account = accountService.findAccountById(accountId);

        return ResponseEntity.ok(new AccountResponse(
            account.getAccountId(),
            account.getDocumentNumber(),
            account.getAvailableCreditLimit()
        ));
    }

    public record AccountRequest(
        @NotBlank
        @Size(min = 11, max = 11)
        @Pattern(regexp = "^\\d+$")
        @JsonProperty("document_number")
        String documentNumber,

        @JsonProperty("available_credit_limit")
        BigDecimal availableCreditLimit
    ) {
        public Account toEntity() {
            return new Account(null, documentNumber, availableCreditLimit);
        }
    }

    public record AccountResponse(
        @JsonProperty("account_id")
        Long accountId,

        @JsonProperty("document_number")
        String documentNumber,

        @JsonProperty("available_credit_limit")
        BigDecimal availableCreditLimit
    ) {}

}

package io.bvalentino.transactionsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bvalentino.transactionsapi.controller.TransactionController.TransactionRequest;
import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.entity.OperationType;
import io.bvalentino.transactionsapi.entity.Transaction;
import io.bvalentino.transactionsapi.repository.AccountRepository;
import io.bvalentino.transactionsapi.repository.TransactionRepository;
import io.bvalentino.transactionsapi.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Must return HTTP Status Code 201 when registering a transaction successfully.")
    void testCase1() throws Exception {
        var request = new TransactionRequest(
            1L, 1L, BigDecimal.valueOf(50.00)
        );

        var transaction = Transaction.builder()
            .transactionId(1L)
            .account(
                Account.builder()
                    .accountId(2L)
                    .documentNumber("11122233344")
                .build()
            )
            .operationType(OperationType.COMPRA_A_VISTA)
            .amount(BigDecimal.valueOf(50.00))
        .build();

        when(accountRepository.existsByDocumentNumber(anyString())).thenReturn(true);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionService.register(request)).thenReturn(transaction);

        var response = mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
            )
            .andExpect(status().isCreated())
            .andReturn()
        .getResponse();

        assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Must return HTTP Status Code 400 when receiving an invalid accountId.")
    void testCase2() throws Exception {
        var request = new TransactionRequest(
            null, 1L, BigDecimal.valueOf(50.00)
        );

        var response = mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errors.accountId[0]", is("The account ID must not be null.")))
            .andReturn()
        .getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Must return HTTP Status Code 400 when receiving an invalid operationTypeId.")
    void testCase3() throws Exception {
        var request = new TransactionRequest(
            1L, null, BigDecimal.valueOf(50.00)
        );

        var response = mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errors.operationTypeId[0]", is("The operation type ID must not be null.")))
            .andReturn()
        .getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Must return HTTP Status Code 400 when receiving an invalid amount.")
    void testCase4() throws Exception {
        var request = new TransactionRequest(
            1L, null, BigDecimal.valueOf(-50.00)
        );

        var response = mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errors.amount[0]", is("The amount must be a value greater than zero.")))
            .andReturn()
        .getResponse();

        assertEquals(400, response.getStatus());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
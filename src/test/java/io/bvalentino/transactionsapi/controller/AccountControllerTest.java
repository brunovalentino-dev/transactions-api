package io.bvalentino.transactionsapi.controller;

import io.bvalentino.transactionsapi.entity.Account;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @DisplayName("Must return an account when requested by its ID.")
    void testCase1() throws Exception {
        var account = Account.builder()
            .accountId(1L)
            .documentNumber("12345678900")
        .build();

        when(accountService.findAccountById(any(Long.class)))
            .thenReturn(account);

        var response = mockMvc.perform(get("/accounts/{accountId}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.account_id", is(1)))
            .andExpect(jsonPath("$.document_number", is("12345678900")))
            .andReturn()
        .getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Must return an error when an account is not found.")
    void testCase2() throws Exception {
        when(accountService.findAccountById(any(Long.class)))
            .thenThrow(new AccountNotFoundException("Account not found!"));

        var response = mockMvc.perform(get("/accounts/{accountId}", 10L))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errors.message[0]", is("Account not found!")))
            .andReturn()
            .getResponse();

        assertEquals(404, response.getStatus());
    }

}
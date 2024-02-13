package io.bvalentino.transactionsapi.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bvalentino.transactionsapi.exception.AccountNotFoundException;
import io.bvalentino.transactionsapi.exception.AccountRegisteredException;
import io.bvalentino.transactionsapi.exception.InvalidOperationTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class TransactionsApiExceptionHandler {

    private final MessageSource messageSource;

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionHandlerResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        var map = new HashMap<String, List<String>>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            var field = error.getField();
            var message = this.errorMessageBuilder(error);

            if (map.containsKey(field)) {
                map.get(field).add(message);
            }
            else {
                var list = new ArrayList<String>();
                list.add(message);
                map.put(field, list);
            }
        });

        return new ExceptionHandlerResponse(map, LocalDateTime.now());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountRegisteredException.class)
    public ExceptionHandlerResponse accountRegisteredExceptionHandler(AccountRegisteredException ex) {
        var map = new HashMap<String, List<String>>();
        map.put("message", List.of(Objects.requireNonNull(ex.getMessage())));

        return new ExceptionHandlerResponse(map, LocalDateTime.now());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidOperationTypeException.class)
    public ExceptionHandlerResponse invalidOperationTypeExceptionHandler(InvalidOperationTypeException ex) {
        var map = new HashMap<String, List<String>>();
        map.put("message", List.of(Objects.requireNonNull(ex.getMessage())));

        return new ExceptionHandlerResponse(map, LocalDateTime.now());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ExceptionHandlerResponse accountNotFoundExceptionHandler(AccountNotFoundException ex) {
        var map = new HashMap<String, List<String>>();
        map.put("message", List.of(Objects.requireNonNull(ex.getMessage())));

        return new ExceptionHandlerResponse(map, LocalDateTime.now());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionHandlerResponse exceptionHandler(Exception ex) {
        var map = new HashMap<String, List<String>>();
        map.put("message", List.of("Sorry! An unexpected error occurred. Please try process your request later."));

        return new ExceptionHandlerResponse(map, LocalDateTime.now());
    }

    public record ExceptionHandlerResponse(
        @JsonProperty("errors") Map<String, List<String>> errors,
        @JsonProperty("registered_at") LocalDateTime registeredAt
    ) {}

    private String errorMessageBuilder(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
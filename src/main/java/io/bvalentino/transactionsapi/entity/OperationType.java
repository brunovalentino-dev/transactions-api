package io.bvalentino.transactionsapi.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OperationType {

    COMPRA_A_VISTA(1) {
        @Override
        public BigDecimal defineOperation(BigDecimal amount) {
            return amount.negate();
        }
    },
    COMPRA_PARCELADA(2) {
        @Override
        public BigDecimal defineOperation(BigDecimal amount) {
            return amount.negate();
        }
    },
    SAQUE(3) {
        @Override
        public BigDecimal defineOperation(BigDecimal amount) {
            return amount.negate();
        }
    },
    PAGAMENTO(4) {
        @Override
        public BigDecimal defineOperation(BigDecimal amount) {
            return amount;
        }
    };

    private final int operationTypeId;

    OperationType(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public static Optional<OperationType> findByOperationTypeId(Long id) {
        return Arrays.stream(values())
            .filter(operationType -> operationType.getOperationTypeId() == id)
            .findFirst();
    }

    public abstract BigDecimal defineOperation(BigDecimal amount);

}

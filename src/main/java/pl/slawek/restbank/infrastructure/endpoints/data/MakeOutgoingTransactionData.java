package pl.slawek.restbank.infrastructure.endpoints.data;

import java.math.BigDecimal;

public class MakeOutgoingTransactionData {

    private String destination;
    private BigDecimal amount;

    public String getDestination() {
        return destination;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

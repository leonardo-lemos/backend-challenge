package dev.leonardolemos.backendchallenge.model.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMode {
    @JsonProperty("bank slip")
    BANKSLIP("bank slip");

    PaymentMode(String paymentMode) {
    }
}

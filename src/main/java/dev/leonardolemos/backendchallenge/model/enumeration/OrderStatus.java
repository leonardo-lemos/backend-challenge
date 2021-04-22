package dev.leonardolemos.backendchallenge.model.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("pending confirmation")
    PENDING_CONFIRMATION("pending confirmation"),
    @JsonProperty("cancelled")
    CANCELLED("cancelled"),
    @JsonProperty("confirmed")
    CONFIRMED("confirmed");

    OrderStatus(String orderStatus) {
    }
}

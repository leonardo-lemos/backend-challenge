package dev.leonardolemos.backendchallenge.model.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeliveryMode {
    @JsonProperty("in-store withdrawal")
    INSTORE_WITHDRAW("in-store withdrawal");

    DeliveryMode(String deliveryMode) {
    }
}
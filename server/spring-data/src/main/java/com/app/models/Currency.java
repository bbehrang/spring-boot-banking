package com.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public enum  Currency implements Serializable {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH"),
    CHF("CHF"),
    GBP("GBP");

    @Getter
    @Setter
    private String value;

    Currency(String value) {
       this.value = value;
    }

}

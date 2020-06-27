package com.devlop.moneyspread.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class MoneySpreadDto {

    @JsonProperty("spreMoney")
    long spreMoney;
    @JsonProperty("spreUsers")
    Set<Long> spreUsers;
}

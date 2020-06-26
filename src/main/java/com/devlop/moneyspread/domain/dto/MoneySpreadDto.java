package com.devlop.moneyspread.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoneySpreadDto {

    @JsonProperty("spreMoney")
    long spreMoney;
    @JsonProperty("spreCount")
    long spreCount;
}

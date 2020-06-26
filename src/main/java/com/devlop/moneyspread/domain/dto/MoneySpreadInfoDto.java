package com.devlop.moneyspread.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneySpreadInfoDto {

    private Instant spreadDate;
    private long spreadMoney;
    private long receiveCompleteMoney;
    private List<ReceiveCompleteInfoDto> receiveCompleteInfo;
}

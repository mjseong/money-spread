package com.devlop.moneyspread.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneySpreadInfoDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Instant spreadDate;
    private long spreadMoney;
    private long receiveCompleteMoney;
    private List<ReceiveCompleteInfoDto> receiveCompleteInfo;
}

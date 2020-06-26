package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.ReceiveMoney;
import com.devlop.moneyspread.domain.dto.ReceiveCompleteInfoDto;

import java.util.List;

public interface ReceiveMoneyService {

    ReceiveMoney saveReceiveMoney(ReceiveMoney receiveMoney);
    ReceiveMoney findTop1BySpreIdAndState(String spreId, String recState);
    List<ReceiveCompleteInfoDto> findAllBySpreRoomAndSpreTokenAndSpreId(String spreRoom, String spreToken, String spreInfoId);
}

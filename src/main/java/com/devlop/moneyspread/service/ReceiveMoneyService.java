package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.ReceiveMoney;

public interface ReceiveMoneyService {

    ReceiveMoney saveReceiveMoney(ReceiveMoney receiveMoney);
    ReceiveMoney findTop1BySpreIdAndState(String spreId, String recState);
}

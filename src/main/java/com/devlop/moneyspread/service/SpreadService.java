package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;

public interface SpreadService {

    String getMoneySpreadToken(MoneySpreadDto moneySpreadDto, long spreUserId, String spreRoomId);
}

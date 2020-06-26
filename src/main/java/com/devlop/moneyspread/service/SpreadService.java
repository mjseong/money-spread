package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;

import java.util.List;

public interface SpreadService {

    String getMoneySpreadToken(MoneySpreadDto moneySpreadDto, long spreUserId, String spreRoomId);
    long getMoney(long spreUserId, String spreRoomId, String spreToken) throws Exception;
    MoneySpreadInfoDto getMoneySpreadInfos(long spreUserId, String spreRoomId, String spreToken);

}

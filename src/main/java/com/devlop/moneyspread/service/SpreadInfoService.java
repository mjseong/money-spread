package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.SpreadInfo;

public interface SpreadInfoService {

    SpreadInfo saveSpreadInfo(SpreadInfo spreadInfo);
    SpreadInfo findSpreadInfo(String spreRoomId, String spreToken);
    SpreadInfo findSpreadInfo(long spreUserId, String spreRoomId, String spreToken);

}

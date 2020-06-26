package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.dao.SpreadInfoRepository;
import com.devlop.moneyspread.domain.SpreadInfo;
import com.devlop.moneyspread.service.SpreadInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service("spreadInfoService")
public class SpreadInfoServiceImpl implements SpreadInfoService {

    private final SpreadInfoRepository spreadInfoRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public SpreadInfo saveSpreadInfo(SpreadInfo spreadInfo) {
        return spreadInfoRepository.save(spreadInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public SpreadInfo findSpreadInfo(String spreRoomId, String spreToken) {
        return spreadInfoRepository
                .findBySpreRoomAndSpreToken(spreRoomId, spreToken)
                .orElseThrow(()->new NoSuchElementException());
    }

    @Transactional(readOnly = true)
    @Override
    public SpreadInfo findSpreadInfo(long spreUserId, String spreRoomId, String spreToken) {
        return spreadInfoRepository
                        .findBySpreUserAndSpreRoomAndSpreToken(spreUserId, spreRoomId, spreToken)
                        .orElse(null);
    }
}

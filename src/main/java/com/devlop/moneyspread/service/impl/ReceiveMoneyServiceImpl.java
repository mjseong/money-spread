package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.dao.ReceiveMoneyRepository;
import com.devlop.moneyspread.domain.ReceiveMoney;
import com.devlop.moneyspread.domain.dto.ReceiveCompleteInfoDto;
import com.devlop.moneyspread.service.ReceiveMoneyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service("receiveMoneyService")
public class ReceiveMoneyServiceImpl implements ReceiveMoneyService {

    private final ReceiveMoneyRepository receiveMoneyRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ReceiveMoney saveReceiveMoney(ReceiveMoney receiveMoney) {
        return receiveMoneyRepository.save(receiveMoney);
    }

    @Transactional(readOnly = true)
    @Override
    public ReceiveMoney findTop1BySpreIdAndState(String spreId, String recState) {
        return receiveMoneyRepository
                .findTop1BySpreIdAndRecState(spreId, recState)
                .orElseThrow(()->new NoSuchElementException());
    }

    @Override
    public List<ReceiveCompleteInfoDto> findAllBySpreRoomAndSpreTokenAndSpreId(String spreRoom, String spreToken, String spreInfoId) {

        List<ReceiveCompleteInfoDto> receiveCompleteInfoDtos = receiveMoneyRepository
                                        .findAllBySpreRoomAndSpreTokenAndSpreId(spreRoom, spreToken, spreInfoId);

        return receiveCompleteInfoDtos;
    }

}

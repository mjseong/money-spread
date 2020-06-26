package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.common.UuidUtils;
import com.devlop.moneyspread.domain.ReceiveMoney;
import com.devlop.moneyspread.domain.ReceiveUser;
import com.devlop.moneyspread.domain.SpreadInfo;
import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@Slf4j
@Service("spreadService")
public class SpreadServiceImpl implements SpreadService {

    private final MoneyDistributionService moneyDistributionService;
    private final SpreadTokenService spreadTokenService;
    private final SpreadInfoService spreadInfoService;
    private final ReceiveMoneyService receiveMoneyService;
    private final ReceiveUserService receiveUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getMoneySpreadToken(MoneySpreadDto moneySpreadDto, long spreUserId, String spreRoomId) {

        String spreToken = spreadTokenService.generateToken();
        long spreTotalMoney = moneySpreadDto.getSpreMoney();
        List<Long> moneys = moneyDistributionService.distributeMoney(spreTotalMoney, moneySpreadDto.getSpreCount(), "AVG");

        try{
            String spreId = UuidUtils.generateUuid("spre_");

            SpreadInfo spreadInfo = SpreadInfo.builder()
                                    .spreId(spreId)
                                    .spreRoom(spreRoomId)
                                    .spreToken(spreToken)
                                    .spreUser(spreUserId)
                                    .spreMoney(spreTotalMoney)
                                    .build();

            spreadInfo = spreadInfoService.saveSpreadInfo(spreadInfo);

            if(spreadInfo == null){
                throw new Exception("");
            }

            Instant recCreateDate = Instant.now();

            int resultSum = moneys.stream()
                                    .mapToInt(money -> {
                                        ReceiveMoney receiveMoney = ReceiveMoney.builder()
                                                                                .recMoneyId(UuidUtils.generateUuid("recm_"))
                                                                                .spreId(spreId)
                                                                                .recMoney(money)
                                                                                .recCreateDate(recCreateDate)
                                                                                .build();
                                        receiveMoney = receiveMoneyService.saveReceiveMoney(receiveMoney);
                                            if(receiveMoney != null){
                                                return 1;
                                            }else{
                                                return 0;
                                            }
                                    })
                                    .sum();
            return spreToken;

        }catch (Exception e){
            log.error(e.getMessage());
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public long getMoney(long recUserId, String spreRoomId, String spreToken) throws Exception {

        SpreadInfo spreadInfo = spreadInfoService.findSpreadInfo(spreRoomId, spreToken);

        //token time expire check
//        if(spreadInfo.getSpreDate())

        //token 발급자 get not money check
        if(spreadInfo.getSpreUser() == recUserId)
        {
            throw new Exception("equal token by generation user");
        }

        ReceiveMoney receiveMoney = receiveMoneyService.findTop1BySpreIdAndState(spreadInfo.getSpreId(), "N");
        ReceiveUser receiveUser = ReceiveUser.builder()
                                    .receiveMoney(receiveMoney)
                                    .spreRoom(spreadInfo.getSpreRoom())
                                    .spreToken(spreadInfo.getSpreToken())
                                    .recUser(recUserId)
                                    .build();

        try{
            receiveUser = receiveUserService.saveReceiveUser(receiveUser);

            if(receiveUser!=null){
                //return is transaction commit recState : Y
                receiveMoney.setRecState("Y");
                return receiveMoney.getRecMoney();
            }
        }catch (Exception e){
            return -1;
        }
        return 0;
    }

    @Override
    public List<MoneySpreadInfoDto> getMoneySpreadInfos(long spreUserId, String spreRoomId, String spreToken) {
        return null;
    }
}

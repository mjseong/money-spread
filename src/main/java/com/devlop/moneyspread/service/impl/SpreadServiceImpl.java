package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.domain.ReceiveUser;
import com.devlop.moneyspread.domain.SpreadInfo;
import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Service("spreadService")
public class SpreadServiceImpl implements SpreadService {

    private final MoneyDistributionService moneyDistributionService;
    private final SpreadTokenService spreadTokenService;
    private final SpreadInfoService spreadInfoService;
    private final ReceiveUserService receiveUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getMoneySpreadToken(MoneySpreadDto moneySpreadDto, long spreUserId, String spreRoomId) {

        String spreToken = spreadTokenService.generateToken();
        List<Long> moneys = moneyDistributionService.distributeMoney(moneySpreadDto.getSpreMoney(), moneySpreadDto.getSpreCount(), "AVG");

        try{
            SpreadInfo spreadInfo = new SpreadInfo();
            spreadInfo = spreadInfoService.saveSpreadInfo(spreadInfo);

            if(spreadInfo == null){
                throw new Exception("");
            }

            int resultSum = moneys.stream()
                                    .mapToInt(money -> {
                                            ReceiveUser receiveUser = new ReceiveUser();
                                            receiveUser = receiveUserService.saveReceiveUser(receiveUser);
                                            if(receiveUser != null){
                                                return 1;
                                            }else{
                                                return 0;
                                            }
                                    })
                                    .sum();

        }catch (Exception e){
            return null;
        }

        return spreToken;
    }
}

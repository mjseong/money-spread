package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.service.MoneyDistributionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("moneyDistributionService")
public class MoneyDistributionServiceImpl implements MoneyDistributionService {

    @Override
    public List<Long> distributeMoney(long spreMoney, long spreUserCnt, String distributionType) {

        if(distributionType.equals("AVG")){
           return getAvgMoney(spreMoney, spreUserCnt);
        }

        return null;
    }

    private List<Long> getAvgMoney(long spreMoney, long spreUserCnt){

        List<Long> moneys = new ArrayList<>();

        long money = 0L;
        long remainMoney = spreMoney;

        for(int i=1; i<= spreUserCnt; i++){
            money = spreMoney/spreUserCnt;
            remainMoney = remainMoney - money;

            if(spreUserCnt == i){
                moneys.add(remainMoney+money);
                break;
            }
            moneys.add(money);
        }

        return moneys;
    }
}

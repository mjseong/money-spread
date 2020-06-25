package com.devlop.moneyspread.service;

import java.util.List;

public interface MoneyDistributionService {

    List<Long> distributeMoney(long spreMoney, long spreUserCnt, String distributionType) ;
}

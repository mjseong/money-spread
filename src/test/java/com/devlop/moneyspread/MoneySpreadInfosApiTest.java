package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.service.SpreadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
public class MoneySpreadInfosApiTest {

    @Autowired
    SpreadService spreadService;

    @Test
    void getMoneySpreadInfos(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 112;
        String token = "2hM";
        long reqUserMoney = 0L;

        MoneySpreadInfoDto moneySpreadInfoDto = spreadService.getMoneySpreadInfos(reqUserId, roomId, token);
        System.out.println(moneySpreadInfoDto);
    }
}

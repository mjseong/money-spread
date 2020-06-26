package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.service.SpreadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
public class MoneySpreadGetMoneyApiTest {

    @Autowired
    SpreadService spreadService;

    @Test
    void getMoney(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 113;
        String token = "otd";
        long reqUserMoney = 0L;

        try {

            for(int i=0; i<20; i++){
                reqUserId = random.nextInt(1000);
                reqUserMoney = spreadService.getMoney(reqUserId, roomId, token);
                Assertions.assertNotEquals(0,reqUserMoney);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Assertions.assertNotEquals(0,reqUserMoney);
    }
}

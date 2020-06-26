package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.service.SpreadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
public class MoneySpreadServiceTest {

    @Autowired
    SpreadService spreadService;

    @Test
    void getMoneySpreadTokenTest(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        String roomId = "room_id_01";
        long reqUserId = 112;
        moneySpreadDto.setSpreCount(30);
        moneySpreadDto.setSpreMoney(10000);


        String token = spreadService.getMoneySpreadToken(moneySpreadDto, 112, "room_id_01");
        Assertions.assertNotEquals(null, token);
        System.out.println("token : " + token);
    }


    @Test
    void getMoney(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 0;
        String token = "zim";
        long reqUserMoney = 0L;

        try {

            for(int i=0; i<20; i++){
                reqUserId = random.nextInt(1000);
                reqUserMoney = spreadService.getMoney(reqUserId, roomId, token);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertFalse(false);
        }
        Assertions.assertNotEquals(0,reqUserMoney);
    }

    @Test
    void getMoneySpreadInfos(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 112;
        String token = "4ju";
        long reqUserMoney = 0L;

        MoneySpreadInfoDto moneySpreadInfoDto = null;
        try {
            moneySpreadInfoDto = spreadService.getMoneySpreadInfos(reqUserId, roomId, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moneySpreadInfoDto);
    }
}

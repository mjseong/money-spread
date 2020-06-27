package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.service.SpreadService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class MoneySpreadServiceTest {

    @Autowired
    SpreadService spreadService;

    String token ="";

    @BeforeEach
    @Test
    void getMoneySpreadTokenTest(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        String roomId = "room_id_01";
        long reqUserId = 12;
        int userCount = 20;
        Set<Long> users = new HashSet<>();
        SecureRandom random = new SecureRandom();

        for(int i=0; i<userCount; i++){
            users.add(Long.valueOf(random.nextInt(50)));
        }

        moneySpreadDto.setSpreUsers(users);
        moneySpreadDto.setSpreMoney(50000);


        token = spreadService.getMoneySpreadToken(moneySpreadDto, reqUserId, "room_id_01");
        Assertions.assertNotEquals(null, token);
        System.out.println("token : " + token);
    }


    @Test
    void getMoney(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 1;
        long reqUserMoney = 0L;

        try {

//            for(int i=0; i<20; i++){
//                reqUserId = random.nextInt(10);
//                reqUserMoney = spreadService.getMoney(reqUserId, roomId, token);
//            }

            reqUserMoney = spreadService.getMoney(reqUserId, roomId, token);

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertFalse(false);
        }
        Assertions.assertNotEquals(0,reqUserMoney);
    }

    @AfterEach
    @Test
    void getMoneySpreadInfos(){

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

        SecureRandom random = new SecureRandom();
        String roomId = "room_id_01";
        long reqUserId = 12;
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

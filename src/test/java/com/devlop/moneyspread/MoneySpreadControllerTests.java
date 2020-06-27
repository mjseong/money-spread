package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoneySpreadControllerTests {

    @Autowired
    TestRestTemplate testRestTemplate;
    String token = "";

    @BeforeEach
    @DisplayName("postMoneySpreadTest")
    void postMoneySpreadTest(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-USER-ID", "12");
        httpHeaders.set("X-ROOM-ID", "room_11");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        int userCount = 20;
        Set<Long> users = new HashSet<>();
        SecureRandom random = new SecureRandom();

        for(int i=0; i<userCount; i++){
//            users.add(Long.valueOf(random.nextInt(50)));
            users.add(Long.valueOf(i));
        }

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();
        moneySpreadDto.setSpreUsers(users);
        moneySpreadDto.setSpreMoney(50000);

        HttpEntity httpEntity = new HttpEntity<>(moneySpreadDto, httpHeaders);

        ResponseEntity responseEntity= testRestTemplate.exchange("/v1/money-spread", HttpMethod.POST, httpEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            token = (String) objectMapper.readValue((String) responseEntity.getBody(), Map.class).get("spreadToken");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println("spreadToken : " + responseEntity.getBody());
    }

    @Test
    void getMoney(){

        int userCount = 20;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-ROOM-ID", "room_11");

        for(int i=0; i<userCount; i++) {
            httpHeaders.set("X-USER-ID", String.valueOf(i));
            HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity responseEntity = testRestTemplate.exchange("/v1/money/" + token, HttpMethod.GET, httpEntity, String.class);
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            System.out.println("receiveMoney : " + responseEntity.getBody());
        }


    }

    @AfterEach
    @DisplayName("getMoneySpreadInfos")
    void getMoneySpreadInfos(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-USER-ID", "12");
        httpHeaders.set("X-ROOM-ID", "room_11");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity responseEntity= testRestTemplate.exchange("/v1/money-spread/"+token, HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        System.out.println("Info : " + responseEntity.getBody());

    }

}

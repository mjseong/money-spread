package com.devlop.moneyspread;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoneySpreadControllerTests {

    @Autowired
    TestRestTemplate testRestTemplate;
    String token = "";

    @BeforeEach
    void postMoneySpreadTest(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-USER-ID", "123");
        httpHeaders.set("X-ROOM-ID", "room_11");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        MoneySpreadDto moneySpreadDto = new MoneySpreadDto();
        moneySpreadDto.setSpreCount(50);
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

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-USER-ID", "1234");
        httpHeaders.set("X-ROOM-ID", "room_11");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity responseEntity= testRestTemplate.exchange("/v1/money/"+token, HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println("receiveMoney : " + responseEntity.getBody());

    }

    @AfterEach
    void getMoneySpreadInfos(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-USER-ID", "123");
        httpHeaders.set("X-ROOM-ID", "room_11");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity responseEntity= testRestTemplate.exchange("/v1/money-spread/"+token, HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        System.out.println("Info : " + responseEntity.getBody());

    }

}

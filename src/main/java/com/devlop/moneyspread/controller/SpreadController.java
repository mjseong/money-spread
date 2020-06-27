package com.devlop.moneyspread.controller;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.exception.TokenExprieException;
import com.devlop.moneyspread.service.SpreadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class SpreadController {

    private final SpreadService spreadService;

    @RequestMapping(method = RequestMethod.GET, value = "/money-spread/{token}")
    @ResponseBody
    public ResponseEntity getMoneySpreadInfos(@RequestHeader(name= "X-USER-ID")long userId,
                                         @RequestHeader(name= "X-ROOM-ID")String roomId,
                                         @PathVariable(name= "token")String token){

        try{
            MoneySpreadInfoDto moneySpreadInfos = spreadService.getMoneySpreadInfos(userId, roomId, token);
            return new ResponseEntity(moneySpreadInfos, HttpStatus.OK);

        }catch (TokenExprieException tee){
            return new ResponseEntity(tee.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException le){
            return new ResponseEntity(le.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/money-spread")
    @ResponseBody
    public ResponseEntity postMoneySpread(@RequestHeader(name= "X-USER-ID")long userId,
                                          @RequestHeader(name= "X-ROOM-ID")String roomId,
                                          @RequestBody MoneySpreadDto moneySpreadDto){

        try{
            String spreadToken = spreadService.getMoneySpreadToken(moneySpreadDto, userId, roomId);
            Map<String, Object> response = new HashMap<>();
            response.put("spreadToken", spreadToken);

            return new ResponseEntity(response, HttpStatus.OK);

        }catch (NoSuchElementException le){
            return new ResponseEntity(le.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/money/{token}")
    @ResponseBody
    public ResponseEntity getMoney(@RequestHeader(name= "X-USER-ID")long userId,
                                          @RequestHeader(name= "X-ROOM-ID")String roomId,
                                          @PathVariable(name= "token")String token) {

        try {
            long receiveMoney = spreadService.getMoney(userId, roomId, token);
            Map<String, Object> response = new HashMap<>();
            response.put("receiveMoney", receiveMoney);

            return new ResponseEntity(response, HttpStatus.OK);

        }catch (TokenExprieException tee){
            return new ResponseEntity(tee.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException le){
            return new ResponseEntity(le.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

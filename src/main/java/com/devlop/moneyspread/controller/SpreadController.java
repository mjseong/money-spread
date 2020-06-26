package com.devlop.moneyspread.controller;

import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.service.SpreadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class SpreadController {

    private final SpreadService spreadService;

//    private SpreadController(SpreadService spreadService) {
//        this.spreadService = spreadService;
//    }

    @RequestMapping(method = RequestMethod.GET,value = "/money-spread/{token}")
    public ResponseEntity getMoneySpreads(@RequestHeader(name = "X-USER-ID")long userId,
                                         @RequestHeader(name="X-ROOM-ID")String roomId,
                                         @PathVariable(name = "token")String token){

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/money-spread")
    public ResponseEntity postMoneySpread(@RequestHeader(name = "X-USER-ID")long userId,
                                          @RequestHeader(name="X-ROOM-ID")String roomId,
                                          @RequestBody MoneySpreadDto moneySpreadDto){

        spreadService.getMoneySpreadToken(moneySpreadDto, userId, roomId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/money/{token}")
    public ResponseEntity getMoney(@RequestHeader(name = "X-USER-ID")long userId,
                                          @RequestHeader(name="X-ROOM-ID")String roomId,
                                          @PathVariable(name = "token")String token){

        return new ResponseEntity(HttpStatus.OK);
    }

}

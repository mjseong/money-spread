package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.service.TokenService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateToken() {
        return getRandomValue();
    }

    private String getRandomValue(){

        char[] charTable = "qwertyuiopasdfghjklzxcvbnm1234567890".toCharArray();

        SecureRandom random = new SecureRandom();
        StringBuilder strBuf = new StringBuilder();

        int numberLength = 3;

        for(int i = 0; i<numberLength; i++){
            char tmp = charTable[random.nextInt(charTable.length)];
            strBuf.append(tmp);
        }

        return strBuf.toString();
    }
}

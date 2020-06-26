package com.devlop.moneyspread.common;

import java.util.UUID;

public class UuidUtils {

    public static String generateUuid(String prefix){

        String uuid = String.valueOf(UUID.randomUUID());
        uuid = uuid.replace("-", "");

        return prefix+uuid;

    }
}

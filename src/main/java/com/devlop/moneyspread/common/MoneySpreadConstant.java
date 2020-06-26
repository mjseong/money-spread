package com.devlop.moneyspread.common;

public class MoneySpreadConstant {

    /**
     * 난수 후보 문자 테이블
     */
    public final static char[] charTable = "qwertyuiopasdfghjklzxcvbnm1234567890AQZXSWEDCRFVTGBYHNUJMIKLOP".toCharArray();
    public final static int EXPIRE_RECORD_HIST_DAY = 7;
    public final static int EXPIRE_TOKEN_TIME_MINUTE = 10;

    public final static String SPREAD_INFO_STATE_DELETE = "D";
    public final static String RECEIVE_MONEY_STATE_USED = "Y";

    public final static String ID_PREFIX_RECEIVEMONEY = "recm_";
    public final static String ID_PREFIX_SPREADINFO = "spre_";
}

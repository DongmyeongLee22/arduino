package com.example.windowproject.common;

public class StringUtils {
    public static int transferToNumber(String str) throws IllegalAccessException {
        try{
            return Integer.parseInt(str);
        }catch (NumberFormatException e){
            throw new IllegalAccessException();
        }
    }
    public static boolean transferToBoolean(String string) {
        return string.equals("true");
    }
}

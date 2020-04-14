package com.example.windowproject.common;

public class StringUtils {
    public static int transToNumber(String str) throws IllegalAccessException {
        try{
            return Integer.parseInt(str);
        }catch (NumberFormatException e){
            throw new IllegalAccessException();
        }
    }

    public static boolean transToBoolean(String string) {
        return string.equals("true");
    }
}

package com.mindhub.homebanking.utils;

public class AccountUtils {
    public static int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)+min));
    }
}

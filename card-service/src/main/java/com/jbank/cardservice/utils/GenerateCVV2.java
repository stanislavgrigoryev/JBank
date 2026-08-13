package com.jbank.cardservice.utils;

import java.util.Random;

public class GenerateCVV2 {

    public static String generateCVV2(){
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }
}

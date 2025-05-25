package com.vnpt.mini_project_java.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class DiscountCodeGenerator {

    private static String generateRandomString(int length) {
        String characters = "LOCADATESHOPUSTORADEMO/1/2/3+5/6";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public static String generateBase64DiscountCode(int length) {
        String randomString = generateRandomString(length);
        return Base64.getEncoder().encodeToString(randomString.getBytes(StandardCharsets.UTF_8));
    }
}

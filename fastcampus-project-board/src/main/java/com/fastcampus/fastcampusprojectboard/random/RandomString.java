package com.fastcampus.fastcampusprojectboard.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomString {
    public static final char[] chars;
    static {
        StringBuilder builder = new StringBuilder();
        builder.append("0123456789"
                +"abcdefghijklmnopqrstuvwxyz"+"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        chars=builder.toString().toCharArray();
    }

    public String randomToString(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int charArrayPos = random.nextInt(chars.length);
            char randomCharacter = chars[charArrayPos];
             builder.append(randomCharacter);
        }
        return builder.toString();
    }
    public String randomSizeString(int maxLength) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int length = randomLength(maxLength);
        for (int i = 0; i < length ; i++) {
            int charArrayPos = random.nextInt(chars.length);
            char randomCharacter = chars[charArrayPos];
            builder.append(randomCharacter);
        }
        return builder.toString();
    }

    private int randomLength(int maxLength) {
        Random random = new Random();
        while(true) {
            int length = random.nextInt(maxLength);
            if(length <= 0) continue;
            return length;
        }
    }

    public Long randomLongLength(int maxLength) {
        Random random = new Random();
        while(true) {
            Long length = random.nextLong(maxLength);
            if(length <= 0) continue;
            return length;
        }
    }



    public List<String> randomSizeStrings(int randomPriority, int maxLength) {
        List<String> ranodmStrings = new ArrayList<>();
        for (int i = 0; i < randomPriority; i++) {
            ranodmStrings.add(randomSizeString(maxLength));
        }
        return ranodmStrings;
    }

}

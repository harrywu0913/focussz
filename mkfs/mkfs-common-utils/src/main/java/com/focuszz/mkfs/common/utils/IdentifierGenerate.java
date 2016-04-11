package com.focuszz.mkfs.common.utils;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class IdentifierGenerate {
    public static String generate(int id, String target, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(target);
        sb.append(type);
        sb.append(System.nanoTime());
        sb.append(getRandomSalt(10));
        return DigestUtils.md5Hex(sb.toString());
    }

    public synchronized static String generateSid(String target, String type) {
        return generate(0, target, type);
    }

    static String getRandomSalt(final int num) {
        final StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(new Random().nextInt(10));
        }
        return saltString.toString();
    }
}

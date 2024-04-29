package org.example.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public abstract class HashPassword {
    public static String sha256Hash(String str) {
        return Hashing
                .sha256()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();
    }
}
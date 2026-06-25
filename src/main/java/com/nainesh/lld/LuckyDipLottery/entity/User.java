package com.nainesh.lld.LuckyDipLottery.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author Nainesh
 */

public class User {
    public final String aadharHash;
    public final String email;
    public final String phone;
    public User(String aadharId, String email, String phone) {
        this.aadharHash = hash(aadharId);
        this.email = email; this.phone = phone;
    }
    static String hash(String s) {
        try {
            return Base64.getEncoder().encodeToString(
                    MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));
//            return s;
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
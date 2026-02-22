package com.nainesh.lld.UrlShortenerSnippet;


import java.util.HashMap;
import java.util.Random;

//will  break this later to components/classes
public class Main {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 7;
    private final HashMap<String, String> urlMap = new HashMap<>();
    private final Random random = new Random();
    private long counter = 1; // simple incrementing ID

    private String base62(long num) {
        StringBuilder sb = new StringBuilder();
        int BASE = ALPHABET.length();
        while (num > 0) {
            sb.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    // Decode Base62 string back to number
    public static long decode(String str) {
        long num = 0;
        int BASE = 62;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    //replace by base62 encoder
    String shorten(String url) {
//        String shortCode = getCode();
        String shortCode = base62(counter++);
        urlMap.put(shortCode, url);
        return "http://bit.ly/" + shortCode;
    }

    private String getCode() {
        String shortCode;
        StringBuilder sb = new StringBuilder();
        do {
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            shortCode = sb.toString();
        } while (urlMap.containsKey(shortCode));
        return sb.toString();
    }

    private String getOriginalUrl(String shortUrl) {
        int idx = shortUrl.lastIndexOf("/");
        String shortCode = shortUrl.substring(idx + 1);
        return urlMap.get(shortCode);
    }

    public static void main(String[] args) {
        Main shortener = new Main();
        String shortUrl = shortener.shorten("https://naines.github.io");
        System.out.println("Shortened: " + shortUrl);
        String originalUrl = shortener.getOriginalUrl(shortUrl);
        System.out.println("Original: " + originalUrl);
    }
}

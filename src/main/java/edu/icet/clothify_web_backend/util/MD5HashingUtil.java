package edu.icet.clothify_web_backend.util;

public interface MD5HashingUtil {
     String hashWithMD5(String input);
    boolean compareMD5(String rawInput, String storedHash);
}

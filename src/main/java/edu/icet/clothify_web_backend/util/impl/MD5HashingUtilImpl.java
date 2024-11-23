package edu.icet.clothify_web_backend.util.impl;

import edu.icet.clothify_web_backend.util.MD5HashingUtil;

import java.security.MessageDigest;

public class MD5HashingUtilImpl implements MD5HashingUtil {

    // Method to hash input with MD5
    public String hashWithMD5(String input) {
        try {
            // Normalize input to avoid formatting issues
            input = input.trim();

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    // Method to compare a raw input with the stored hash
    public boolean compareMD5(String rawInput, String storedHash) {
        if (rawInput == null || storedHash == null) {
            System.out.println("One or both inputs are null!");
            return false;
        }

        // Normalize inputs
        rawInput = rawInput.trim();
        storedHash = storedHash.trim();
        return hashWithMD5(rawInput).equals(storedHash);
    }
}

package com.example.rxopencv;

import java.util.HashMap;
import java.util.Map;

public class binarydecoder {

    private static final Map<String, Character> binaryToCharMap = new HashMap<>();

    static {
        binaryToCharMap.put("00110000", '0');
        binaryToCharMap.put("00110001", '1');
        binaryToCharMap.put("00110010", '2');
        binaryToCharMap.put("00110011", '3');
        binaryToCharMap.put("00110100", '4');
        binaryToCharMap.put("00110101", '5');
        binaryToCharMap.put("00110110", '6');
        binaryToCharMap.put("00110111", '7');
        binaryToCharMap.put("00111000", '8');
        binaryToCharMap.put("00111001", '9');
        binaryToCharMap.put("01000001", 'A');
        binaryToCharMap.put("01000010", 'B');
        binaryToCharMap.put("01000011", 'C');
        binaryToCharMap.put("01000100", 'D');
        binaryToCharMap.put("01000101", 'E');
        binaryToCharMap.put("01000110", 'F');
        binaryToCharMap.put("01000111", 'G');
    }

    public static String decodeBinary(String binarySequence, int length) {
        if (binarySequence.length() != length * 8) {
            throw new IllegalArgumentException("Binary sequence length does not match the provided length.");
        }

        StringBuilder decodedMessage = new StringBuilder();

        for (int i = 0; i < binarySequence.length(); i += 8) {
            String block = binarySequence.substring(i, i + 8);
            Character decodedChar = binaryToCharMap.get(block);

            if (decodedChar == null) {
                throw new IllegalArgumentException("Invalid binary sequence: " + block);
            }

            decodedMessage.append(decodedChar);
        }

        return decodedMessage.toString();
    }
}
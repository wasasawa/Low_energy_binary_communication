package com.example.rxopencv;

import java.util.HashMap;
import java.util.Map;

public class binarydecoder {

    private static final Map<String, Character> binaryToCharMap = new HashMap<>();

static {
    // Nombres de 0 à 9
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

    // Lettres majuscules A-Z
    binaryToCharMap.put("01000001", 'A');
    binaryToCharMap.put("01000010", 'B');
    binaryToCharMap.put("01000011", 'C');
    binaryToCharMap.put("01000100", 'D');
    binaryToCharMap.put("01000101", 'E');
    binaryToCharMap.put("01000110", 'F');
    binaryToCharMap.put("01000111", 'G');
    binaryToCharMap.put("01001000", 'H');
    binaryToCharMap.put("01001001", 'I');
    binaryToCharMap.put("01001010", 'J');
    binaryToCharMap.put("01001011", 'K');
    binaryToCharMap.put("01001100", 'L');
    binaryToCharMap.put("01001101", 'M');
    binaryToCharMap.put("01001110", 'N');
    binaryToCharMap.put("01001111", 'O');
    binaryToCharMap.put("01010000", 'P');
    binaryToCharMap.put("01010001", 'Q');
    binaryToCharMap.put("01010010", 'R');
    binaryToCharMap.put("01010011", 'S');
    binaryToCharMap.put("01010100", 'T');
    binaryToCharMap.put("01010101", 'U');
    binaryToCharMap.put("01010110", 'V');
    binaryToCharMap.put("01010111", 'W');
    binaryToCharMap.put("01011000", 'X');
    binaryToCharMap.put("01011001", 'Y');
    binaryToCharMap.put("01011010", 'Z');

    // Lettres minuscules a-z
    binaryToCharMap.put("01100001", 'a');
    binaryToCharMap.put("01100010", 'b');
    binaryToCharMap.put("01100011", 'c');
    binaryToCharMap.put("01100100", 'd');
    binaryToCharMap.put("01100101", 'e');
    binaryToCharMap.put("01100110", 'f');
    binaryToCharMap.put("01100111", 'g');
    binaryToCharMap.put("01101000", 'h');
    binaryToCharMap.put("01101001", 'i');
    binaryToCharMap.put("01101010", 'j');
    binaryToCharMap.put("01101011", 'k');
    binaryToCharMap.put("01101100", 'l');
    binaryToCharMap.put("01101101", 'm');
    binaryToCharMap.put("01101110", 'n');
    binaryToCharMap.put("01101111", 'o');
    binaryToCharMap.put("01110000", 'p');
    binaryToCharMap.put("01110001", 'q');
    binaryToCharMap.put("01110010", 'r');
    binaryToCharMap.put("01110011", 's');
    binaryToCharMap.put("01110100", 't');
    binaryToCharMap.put("01110101", 'u');
    binaryToCharMap.put("01110110", 'v');
    binaryToCharMap.put("01110111", 'w');
    binaryToCharMap.put("01111000", 'x');
    binaryToCharMap.put("01111001", 'y');
    binaryToCharMap.put("01111010", 'z');
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
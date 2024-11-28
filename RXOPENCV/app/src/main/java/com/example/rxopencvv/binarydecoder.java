package com.example.rxopencv;

public class binarydecoder {

    /**
     * Decodes a binary sequence into text.
     *
     * @param binarySequence The binary sequence as a String (e.g., "01000001").
     * @return The decoded text.
     */
    public String decodeBinary(String binarySequence) {
        StringBuilder decodedText = new StringBuilder();

        // Ensure the binary sequence length is a multiple of 8
        for (int i = 0; i < binarySequence.length(); i += 8) {
            if (i + 8 <= binarySequence.length()) {
                String byteString = binarySequence.substring(i, i + 8);
                char decodedChar = (char) Integer.parseInt(byteString, 2); // Binary to char
                decodedText.append(decodedChar);
            }
        }

        return decodedText.toString();
    }
}


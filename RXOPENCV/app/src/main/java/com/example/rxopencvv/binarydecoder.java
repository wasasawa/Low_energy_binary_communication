package com.example.rxopencv;

import java.util.HashMap;
import java.util.Map;



public class binarydecoder {

    /**
     * Decodes a binary sequence into text.
     *
     * @param binarySequence The binary sequence as a String (e.g., "01000001").
     * @return The decoded text.
     */
    private static final Map<String, Character> binaryToCharMap = new HashMap<>();
    static {
        binaryToCharMap.put("00000000", '\u0000'); // NULL
        binaryToCharMap.put("00000001", '\u0001'); // SOH
        binaryToCharMap.put("00000010", '\u0002'); // STX
        binaryToCharMap.put("00000011", '\u0003'); // ETX
        binaryToCharMap.put("00000100", '\u0004'); // EOT
        binaryToCharMap.put("00000101", '\u0005'); // ENQ
        binaryToCharMap.put("00000110", '\u0006'); // ACK
        binaryToCharMap.put("00000111", '\u0007'); // BEL
        binaryToCharMap.put("00001000", '\u0008'); // BS
        binaryToCharMap.put("00001001", '\u0009'); // TAB
        /*binaryToCharMap.put("00001010", '\u000A'); // LF*/
        binaryToCharMap.put("00001011", '\u000B'); // VT
        binaryToCharMap.put("00001100", '\u000C'); // FF
        /*binaryToCharMap.put("00001101", '\u000D'); // CR*/
        binaryToCharMap.put("00001110", '\u000E'); // SO
        binaryToCharMap.put("00001111", '\u000F'); // SI
        binaryToCharMap.put("00010000", '\u0010'); // DLE
        binaryToCharMap.put("00010001", '\u0011'); // DC1
        binaryToCharMap.put("00010010", '\u0012'); // DC2
        binaryToCharMap.put("00010011", '\u0013'); // DC3
        binaryToCharMap.put("00010100", '\u0014'); // DC4
        binaryToCharMap.put("00010101", '\u0015'); // NAK
        binaryToCharMap.put("00010110", '\u0016'); // SYN
        binaryToCharMap.put("00010111", '\u0017'); // ETB
        binaryToCharMap.put("00011000", '\u0018'); // CAN
        binaryToCharMap.put("00011001", '\u0019'); // EM
        binaryToCharMap.put("00011010", '\u001A'); // SUB
        binaryToCharMap.put("00011011", '\u001B'); // ESC
        binaryToCharMap.put("00011100", '\u001C'); // FS
        binaryToCharMap.put("00011101", '\u001D'); // GS
        binaryToCharMap.put("00011110", '\u001E'); // RS
        binaryToCharMap.put("00011111", '\u001F'); // US
        binaryToCharMap.put("00100000", ' ');     // SPACE
        binaryToCharMap.put("00100001", '!');     // EXCLAMATION
        binaryToCharMap.put("00100010", '"');     // DOUBLE_QUOTE
        binaryToCharMap.put("00100011", '#');     // HASH
        binaryToCharMap.put("00100100", '$');     // DOLLAR
        binaryToCharMap.put("00100101", '%');     // PERCENT
        binaryToCharMap.put("00100110", '&');     // AMPERSAND
        binaryToCharMap.put("00100111", '\'');    // SINGLE_QUOTE
        binaryToCharMap.put("00101000", '(');     // LEFT_PAREN
        binaryToCharMap.put("00101001", ')');     // RIGHT_PAREN
        binaryToCharMap.put("00101010", '*');     // ASTERISK
        binaryToCharMap.put("00101011", '+');     // PLUS
        binaryToCharMap.put("00101100", ',');     // COMMA
        binaryToCharMap.put("00101101", '-');     // MINUS
        binaryToCharMap.put("00101110", '.');     // PERIOD
        binaryToCharMap.put("00101111", '/');     // SLASH
        binaryToCharMap.put("00110000", '0');     // 0
        binaryToCharMap.put("00110001", '1');     // 1
        binaryToCharMap.put("00110010", '2');     // 2
        binaryToCharMap.put("00110011", '3');     // 3
        binaryToCharMap.put("00110100", '4');     // 4
        binaryToCharMap.put("00110101", '5');     // 5
        binaryToCharMap.put("00110110", '6');     // 6
        binaryToCharMap.put("00110111", '7');     // 7
        binaryToCharMap.put("00111000", '8');     // 8
        binaryToCharMap.put("00111001", '9');     // 9
        binaryToCharMap.put("00111010", ':');     // COLON
        binaryToCharMap.put("00111011", ';');     // SEMICOLON
        binaryToCharMap.put("00111100", '<');     // LESS_THAN
        binaryToCharMap.put("00111101", '=');     // EQUALS
        binaryToCharMap.put("00111110", '>');     // GREATER_THAN
        binaryToCharMap.put("00111111", '?');     // QUESTION_MARK
        binaryToCharMap.put("01000000", '@');     // AT
        binaryToCharMap.put("01000001", 'A');     // A
        binaryToCharMap.put("01000010", 'B');     // B
        binaryToCharMap.put("01000011", 'C');     // C
        binaryToCharMap.put("01000100", 'D');     // D
        binaryToCharMap.put("01000101", 'E');     // E
        binaryToCharMap.put("01000110", 'F');     // F
        binaryToCharMap.put("01000111", 'G');     // G
        binaryToCharMap.put("01001000", 'H');     // H
        binaryToCharMap.put("01001001", 'I');     // I
        binaryToCharMap.put("01001010", 'J');     // J
        binaryToCharMap.put("01001011", 'K');     // K
        binaryToCharMap.put("01001100", 'L');     // L
        binaryToCharMap.put("01001101", 'M');     // M
        binaryToCharMap.put("01001110", 'N');     // N
        binaryToCharMap.put("01001111", 'O');     // O
        binaryToCharMap.put("01010000", 'P');     // P
        binaryToCharMap.put("01010001", 'Q');     // Q
        binaryToCharMap.put("01010010", 'R');     // R
        binaryToCharMap.put("01010011", 'S');     // S
        binaryToCharMap.put("01010100", 'T');     // T
        binaryToCharMap.put("01010101", 'U');     // U
        binaryToCharMap.put("01010110", 'V');     // V
        binaryToCharMap.put("01010111", 'W');     // W
        binaryToCharMap.put("01011000", 'X');     // X
        binaryToCharMap.put("01011001", 'Y');     // Y
        binaryToCharMap.put("01011010", 'Z');     // Z
        binaryToCharMap.put("01011011", '[');     // LEFT_BRACKET
        binaryToCharMap.put("01011100", '\\');    // BACKSLASH
        binaryToCharMap.put("01011101", ']');     // RIGHT_BRACKET
        binaryToCharMap.put("01011110", '^');     // CARET
        binaryToCharMap.put("01011111", '_');     // UNDERSCORE
        binaryToCharMap.put("01100000", '`');     // GRAVE
        binaryToCharMap.put("01100001", 'a');     // a
        binaryToCharMap.put("01100010", 'b');     // b
        binaryToCharMap.put("01100011", 'c');     // c
        binaryToCharMap.put("01100100", 'd');     // d
        binaryToCharMap.put("01100101", 'e');     // e
        binaryToCharMap.put("01100110", 'f');     // f
        binaryToCharMap.put("01100111", 'g');     // g
        binaryToCharMap.put("01101000", 'h');     // h
        binaryToCharMap.put("01101001", 'i');     // i
        binaryToCharMap.put("01101010", 'j');     // j
        binaryToCharMap.put("01101011", 'k');     // k
        binaryToCharMap.put("01101100", 'l');     // l
        binaryToCharMap.put("01101101", 'm');     // m
        binaryToCharMap.put("01101110", 'n');     // n
        binaryToCharMap.put("01101111", 'o');     // o
        binaryToCharMap.put("01110000", 'p');     // p
        binaryToCharMap.put("01110001", 'q');     // q
        binaryToCharMap.put("01110010", 'r');     // r
        binaryToCharMap.put("01110011", 's');     // s
        binaryToCharMap.put("01110100", 't');     // t
        binaryToCharMap.put("01110101", 'u');     // u
        binaryToCharMap.put("01110110", 'v');     // v
        binaryToCharMap.put("01110111", 'w');     // w
        binaryToCharMap.put("01111000", 'x');     // x
        binaryToCharMap.put("01111001", 'y');     // y
        binaryToCharMap.put("01111010", 'z');     // z
        binaryToCharMap.put("01111011", '{');     // LEFT_BRACE
        binaryToCharMap.put("01111100", '|');     // VERTICAL_BAR
        binaryToCharMap.put("01111101", '}');     // RIGHT_BRACE
        binaryToCharMap.put("01111110", '~');     // TILDE
        binaryToCharMap.put("01111111", '\u007F'); // DEL
}
    public static String decodeBinary(String binarySequence) {
        // Check if the input string is 8 bits long
        if (binarySequence.length() != 8) {
            throw new IllegalArgumentException("Input string must be 8 bits long.");
        }

        // Lookup the character in the map using the binary sequence
        Character decodedChar = binaryToCharMap.get(binarySequence);

        // If the binary string is not found in the map, throw an exception
        if (decodedChar == null) {
            throw new IllegalArgumentException("Invalid binary sequence.");
        }

        // Return the decoded character
        return String.valueOf(decodedChar);
    }

}
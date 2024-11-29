import 'package:torch_light/torch_light.dart';

//ORIGINAL CODE NOT TO BE USED
/*

  Map morseChars = {
    'a': '.-',
    'b': '-...',
    'c': '-.-.',
    'd': '-..',
    'e': '.',
    'f': '..-.',
    'g': '--.',
    'h': '....',
    'i': '..',
    'j': '.---',
    'k': '-.-',
    'l': '.-..',
    'm': '--',
    'n': '-.',
    'o': '---',
    'p': '.--.',
    'q': '--.-',
    'r': '.-.',
    's': '...',
    't': '-',
    'u': '..-',
    'v': '...-',
    'w': '.--',
    'x': '-..-',
    'y': '-.--',
    'z': '--..',
    '0': '-----',
    '1': '.----',
    '2': '..---',
    '3': '...--',
    '4': '....-',
    '5': '.....',
    '6': '-....',
    '7': '--...',
    '8': '---..',
    '9': '----.',
    '&': '.-...',
    "'": '.----.',
    '@': '.--.-.',
    r"$": '···−··−',
    ')': '-.--.-',
    '(': '-.--.',
    ':': '---...',
    ',': '--..--',
    ';': '−·−·−·',
    '=': '-...-',
    '!': '-.-.--',
    '.': '.-.-.-',
    '-': '-....-',
    '_': '··−−·−',
    '+': '.-.-.',
    '"': '.-..-.',
    '?': '..--..',
    '/': '-..-.',
  };

    String textToMorse(String text) {
    var morsedText = [];
    // text.
    for (var i = 0; i < text.length; i++) {
      morseChars.forEach((key, value) {
        if (text[i] == key) {
          morsedText.add(value);
        }
        // morsedText.add(value);
        // print(key);
      });
    }
    return morsedText.join(" ");
  }
  void blinkMorse(String morse) async {
    if (await TorchLight.isTorchAvailable()) {
      for (var i = 0; i < morse.length; i++) {
        if (morse[i] == "-") {
          TorchLight.enableTorch();
          await Future.delayed(const Duration(seconds: 3), () {
            TorchLight.disableTorch();
          });
          // morsedText.add(value);
          // prin1t(key);
        } else if (morse[i] == ".") {
          TorchLight.enableTorch();
          await Future.delayed(const Duration(seconds: 1), () {
            TorchLight.disableTorch();
          });
        }
      }
    }
  }
}


String textToMorse(String text) {
  var morsedText = [];
  // text.
  for (var i = 0; i < text.length; i++) {
    bin.forEach((key, value) {
      if (text[i] == key) {
        morsedText.add(value);
      }
      // morsedText.add(value);
      // print(key);
    });
  }
  return morsedText.join(" ");
}
 */

class Functions {
//Look up table for chars
// Using 8 bits and ascii representation
  Map<String, String> bin = {
    '\u0000': '00000000', // NULL
    '\u0001': '00000001', // SOH
    '\u0002': '00000010', // STX
    '\u0003': '00000011', // ETX
    '\u0004': '00000100', // EOT
    '\u0005': '00000101', // ENQ
    '\u0006': '00000110', // ACK
    '\u0007': '00000111', // BEL
    '\u0008': '00001000', // BS
    '\u0009': '00001001', // TAB
    '\u000A': '00001010', // LF
    '\u000B': '00001011', // VT
    '\u000C': '00001100', // FF
    '\u000D': '00001101', // CR
    '\u000E': '00001110', // SO
    '\u000F': '00001111', // SI
    '\u0010': '00010000', // DLE
    '\u0011': '00010001', // DC1
    '\u0012': '00010010', // DC2
    '\u0013': '00010011', // DC3
    '\u0014': '00010100', // DC4
    '\u0015': '00010101', // NAK
    '\u0016': '00010110', // SYN
    '\u0017': '00010111', // ETB
    '\u0018': '00011000', // CAN
    '\u0019': '00011001', // EM
    '\u001A': '00011010', // SUB
    '\u001B': '00011011', // ESC
    '\u001C': '00011100', // FS
    '\u001D': '00011101', // GS
    '\u001E': '00011110', // RS
    '\u001F': '00011111', // US
    ' ': '00100000', // SPACE
    '!': '00100001', // EXCLAMATION
    '"': '00100010', // DOUBLE_QUOTE
    '#': '00100011', // HASH
    '\$': '00100100', // DOLLAR
    '%': '00100101', // PERCENT
    '&': '00100110', // AMPERSAND
    "'": '00100111', // SINGLE_QUOTE
    '(': '00101000', // LEFT_PAREN
    ')': '00101001', // RIGHT_PAREN
    '*': '00101010', // ASTERISK
    '+': '00101011', // PLUS
    ',': '00101100', // COMMA
    '-': '00101101', // MINUS
    '.': '00101110', // PERIOD
    '/': '00101111', // SLASH
    '0': '00110000', // 0
    '1': '00110001', // 1
    '2': '00110010', // 2
    '3': '00110011', // 3
    '4': '00110100', // 4
    '5': '00110101', // 5
    '6': '00110110', // 6
    '7': '00110111', // 7
    '8': '00111000', // 8
    '9': '00111001', // 9
    ':': '00111010', // COLON
    ';': '00111011', // SEMICOLON
    '<': '00111100', // LESS_THAN
    '=': '00111101', // EQUALS
    '>': '00111110', // GREATER_THAN
    '?': '00111111', // QUESTION_MARK
    '@': '01000000', // AT
    'A': '01000001', // A
    'B': '01000010', // B
    'C': '01000011', // C
    'D': '01000100', // D
    'E': '01000101', // E
    'F': '01000110', // F
    'G': '01000111', // G
    'H': '01001000', // H
    'I': '01001001', // I
    'J': '01001010', // J
    'K': '01001011', // K
    'L': '01001100', // L
    'M': '01001101', // M
    'N': '01001110', // N
    'O': '01001111', // O
    'P': '01010000', // P
    'Q': '01010001', // Q
    'R': '01010010', // R
    'S': '01010011', // S
    'T': '01010100', // T
    'U': '01010101', // U
    'V': '01010110', // V
    'W': '01010111', // W
    'X': '01011000', // X
    'Y': '01011001', // Y
    'Z': '01011010', // Z
    '[': '01011011', // LEFT_BRACKET
    '\\': '01011100', // BACKSLASH
    ']': '01011101', // RIGHT_BRACKET
    '^': '01011110', // CARET
    '_': '01011111', // UNDERSCORE
    '`': '01100000', // GRAVE
    'a': '01100001', // a
    'b': '01100010', // b
    'c': '01100011', // c
    'd': '01100100', // d
    'e': '01100101', // e
    'f': '01100110', // f
    'g': '01100111', // g
    'h': '01101000', // h
    'i': '01101001', // i
    'j': '01101010', // j
    'k': '01101011', // k
    'l': '01101100', // l
    'm': '01101101', // m
    'n': '01101110', // n
    'o': '01101111', // o
    'p': '01110000', // p
    'q': '01110001', // q
    'r': '01110010', // r
    's': '01110011', // s
    't': '01110100', // t
    'u': '01110101', // u
    'v': '01110110', // v
    'w': '01110111', // w
    'x': '01111000', // x
    'y': '01111001', // y
    'z': '01111010', // z
    '{': '01111011', // LEFT_BRACE
    '|': '01111100', // VERTICAL_BAR
    '}': '01111101', // RIGHT_BRACE
    '~': '01111110', // TILDE
    '\u007F': '01111111' // DEL
};


  String textToBinary(String text) {
  String codedText='';
  for (int i = 0; i < text.length; i++) {
    String char = text[i];
    codedText += bin[char] ?? ""; // Default to '00000000' if char is not found
  }
  return codedText;
  }

/*
  void transmit(String codedText) async
  {
    //TorchLight.enableTorch();
    var isFlashOn = false;

      if (codedText[0] == "0") {
        TorchLight.disableTorch();
        isFlashOn = false;
        await Future.delayed(const Duration(seconds: 1), () {});

      }
      else if (codedText[0] == "1") {
        TorchLight.enableTorch();
        isFlashOn = true;
        await Future.delayed(const Duration(seconds: 1), () {});
      }

      for (var i = 1; i < codedText.length; i++) {

        if (codedText[i] == "0" ) {
          TorchLight.disableTorch();
          isFlashOn = false;
          await Future.delayed(const Duration(seconds: 1), () {});
        }
        
        else if (codedText[i] == "1") {
          TorchLight.enableTorch();
          isFlashOn = true;
          await Future.delayed(const Duration(seconds: 1), () {});
        }
        
      }
    if (isFlashOn) TorchLight.disableTorch();

  }
*/

void transmit(String codedText) async
  {
    //TorchLight.enableTorch();
    var isTorchOn = false;
    for (var i = 0; i < codedText.length; i++) {
      String char = codedText[i];
        if (char == '0') {
          TorchLight.disableTorch();
          isTorchOn = false;
          await Future.delayed(const Duration(milliseconds: 100), () {});
        }
        else if (char == '1') {
          TorchLight.enableTorch();
          isTorchOn = true;
          await Future.delayed(const Duration(milliseconds: 100), () {});
        }
    if (isTorchOn) {TorchLight.disableTorch();}
  }

  }


}






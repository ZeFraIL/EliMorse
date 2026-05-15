package elia.shapira.elimorse;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for translating between plain text and Morse code.
 * It supports international Morse code standards for letters, numbers, and basic punctuation.
 */
public class MorseTranslator {

    private final Map<String, String> morseMap = new HashMap<>();
    private final Map<String, String> textMap = new HashMap<>();

    public MorseTranslator() {
        initMorseMap();
    }

    /**
     * Translates plain text to Morse code.
     * Skips characters that don't have a Morse representation.
     */
    public String toMorse(String text) {
        if (text == null) return "";
        
        String upperCaseText = text.toUpperCase().trim();
        StringBuilder morseBuilder = new StringBuilder();
        
        for (int i = 0; i < upperCaseText.length(); i++) {
            String character = String.valueOf(upperCaseText.charAt(i));
            String morseChar = morseMap.get(character);
            
            if (morseChar != null) {
                morseBuilder.append(morseChar);
                // Add space between letters, but not after the last character of a word
                if (i < upperCaseText.length() - 1 && !character.equals(" ")) {
                    String nextChar = String.valueOf(upperCaseText.charAt(i + 1));
                    if (!nextChar.equals(" ")) {
                        morseBuilder.append(" ");
                    }
                }
            }
        }
        return morseBuilder.toString().trim();
    }

    /**
     * Translates Morse code to plain text.
     * Invalid Morse sequences are replaced with '?'
     */
    public String toText(String morseCode) {
        if (morseCode == null || morseCode.trim().isEmpty()) return "";
        
        // Ensure uniform word separators for splitting
        String preparedCode = morseCode.trim().replace("/", " / ");
        // Replace multiple spaces with single space to avoid empty tokens
        preparedCode = preparedCode.replaceAll("\\s+", " ");
        String[] words = preparedCode.split(" / ");
        StringBuilder textBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String[] letters = words[i].split(" ");
            for (String letter : letters) {
                if (letter.isEmpty()) continue;
                String textChar = textMap.get(letter);
                if (textChar != null) {
                    textBuilder.append(textChar);
                } else {
                    textBuilder.append("?"); // Indicate invalid Morse sequence
                }
            }
            if (i < words.length - 1) {
                textBuilder.append(" ");
            }
        }
        return textBuilder.toString();
    }

    private void initMorseMap() {
        // International Morse Code Standard
        String[][] morseData = {
                {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."}, {"E", "."}, {"F", "..-."},
                {"G", "--."}, {"H", "...."}, {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."},
                {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."}, {"Q", "--.-"}, {"R", ".-."},
                {"S", "..."}, {"T", "-"}, {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"},
                {"Y", "-.--"}, {"Z", "--.."}, {"0", "-----"}, {"1", ".----"}, {"2", "..---"},
                {"3", "...--"}, {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
                {"8", "---.."}, {"9", "----."}, {" ", "/"}
        };
        for (String[] pair : morseData) {
            morseMap.put(pair[0], pair[1]);
            textMap.put(pair[1], pair[0]);
        }
    }
}

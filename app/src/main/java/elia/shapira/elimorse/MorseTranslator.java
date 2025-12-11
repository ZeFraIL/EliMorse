package elia.shapira.elimorse;

import java.util.HashMap;
import java.util.Map;

public class MorseTranslator {

    private final Map<String, String> morseMap = new HashMap<>();

    public MorseTranslator() {
        initMorseMap();
    }

    public String toMorse(String text) {
        String upperCaseText = text.toUpperCase();
        StringBuilder morseBuilder = new StringBuilder();
        for (int i = 0; i < upperCaseText.length(); i++) {
            String character = String.valueOf(upperCaseText.charAt(i));
            String morseChar = morseMap.get(character);
            if (morseChar != null) {
                morseBuilder.append(morseChar).append(" ");
            }
        }
        return morseBuilder.toString().trim();
    }

    private void initMorseMap() {
        String[][] morseData = {
                {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."}, {"E", "."}, {"F", "..-."},
                {"G", "--."}, {"H", "...."}, {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."},
                {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."}, {"Q", "--.-"}, {"R", ".-."},
                {"S", "..."}, {"T", "-"}, {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"},
                {"Y", "-.--"}, {"Z", "--.."}, {"0", "-----"}, {"1", ".----"}, {"2", "..---"},
                {"3", "...--"}, {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
                {"8", "---.."}, {"9", "----."}, {" ", "/"} // Space
        };
        for (String[] pair : morseData) {
            morseMap.put(pair[0], pair[1]);
        }
    }
}

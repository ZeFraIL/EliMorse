package elia.shapira.elimorse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A repository for generating exercise questions.
 * This class is responsible for creating questions of different types (numbers, words, letters)
 * and translating them into Morse code for the exercises.
 */
public class QuestionRepository {

    private final MorseTranslator translator = new MorseTranslator();
    private final String[] e_words = {"DOG", "CAT", "BIRD", "HAT", "RUN", "JUMP", "BOOK", "PEN", "EAT", "SLEEP", "COMPUTER", "LANGUAGE", "INTERNET", "MESSAGE", "TRANSMIT", "RECEIVE", "ENCRYPT", "DECODE", "NAVIGATE", "TELEGRAM"};
    private final String[] e_letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * Generates a single random question of a specified type.
     *
     * @param gameType The type of game, defined by constants in {@link GameConstants}.
     * @return A {@link Question} object containing the question text and its Morse code answer.
     * @throws IllegalArgumentException if the gameType is invalid.
     */
    public Question generateQuestion(int gameType) {
        String questionText;
        switch (gameType) {
            case GameConstants.GAME_TYPE_NUMBERS:
                questionText = String.valueOf(new Random().nextInt(100));
                break;
            case GameConstants.GAME_TYPE_WORDS:
                questionText = e_words[new Random().nextInt(e_words.length)];
                break;
            case GameConstants.GAME_TYPE_LETTERS:
                questionText = e_letters[new Random().nextInt(e_letters.length)];
                break;
            case GameConstants.GAME_TYPE_LISTENING:
                // For listening, we can generate any type of question, here we reuse words.
                questionText = e_words[new Random().nextInt(e_words.length)];
                break;
            default:
                throw new IllegalArgumentException("Invalid game type: " + gameType);
        }
        String morseAnswer = translator.toMorse(questionText);
        return new Question(questionText, morseAnswer);
    }

    /**
     * Generates a list of random questions.
     *
     * @param gameType The type of game for which to generate questions.
     * @param count The number of questions to generate.
     * @return A list of {@link Question} objects.
     */
    public List<Question> generateQuestions(int gameType, int count) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            questions.add(generateQuestion(gameType));
        }
        return questions;
    }
}

package elia.shapira.elimorse;

/**
 * Represents a single question in a Morse code exercise or game.
 * Each question consists of a text prompt and its corresponding Morse code answer.
 */
public class Question {
    /** The text displayed to the user as the question prompt. */
    private final String questionText;
    /** The correct Morse code sequence that answers the question. */
    private final String morseAnswer;

    /**
     * Constructs a new Question object.
     *
     * @param questionText The text for the question prompt.
     * @param morseAnswer  The correct Morse code answer.
     */
    public Question(String questionText, String morseAnswer) {
        this.questionText = questionText;
        this.morseAnswer = morseAnswer;
    }

    /**
     * Gets the question text.
     * @return The question prompt.
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Gets the correct Morse code answer.
     * @return The Morse code answer sequence.
     */
    public String getMorseAnswer() {
        return morseAnswer;
    }
}

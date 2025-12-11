package elia.shapira.elimorse;

public class Question {
    private final String questionText;
    private final String morseAnswer;

    public Question(String questionText, String morseAnswer) {
        this.questionText = questionText;
        this.morseAnswer = morseAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getMorseAnswer() {
        return morseAnswer;
    }
}

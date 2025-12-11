package elia.shapira.elimorse;

import androidx.appcompat.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An activity that runs a Morse code encoding exercise.
 * The user is presented with a word, letter, or number and must input the correct Morse code.
 * The activity tracks attempts, scores, and saves the results to the database.
 */
public class ExerciseGame extends BaseActivity {

    private int questionNum = 0;
    private int failedAttempts = 0;
    private int nOfTry = 0;
    private TextView tvQuestion, tvInputNumber, tvTryNum1, tvQuestionNum, tvTryNum2, tvTryNum3;
    private String stAnswer = "";
    private HelperDB helperDB;
    private List<Question> questions;
    private Question currentQuestion;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Called when the activity is first created.
     * Initializes UI components, loads questions, and sets up button listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_game);

        initElements();

        Button bDash = findViewById(R.id.bDash);
        Button bSpace = findViewById(R.id.bSpace);
        Button bDot = findViewById(R.id.bDot);
        Button bReady = findViewById(R.id.bReady);
        Button bClear = findViewById(R.id.bClear);
        Button bRemove = findViewById(R.id.bRemove);

        bReady.setOnClickListener(view -> checkAnswer());
        bDash.setOnClickListener(view -> appendToAnswer("-"));
        bSpace.setOnClickListener(view -> appendToAnswer(" "));
        bDot.setOnClickListener(view -> appendToAnswer("."));
        bClear.setOnClickListener(view -> clearAnswer());
        bRemove.setOnClickListener(view -> removeLastChar());
    }

    /**
     * Checks the user's submitted answer against the correct Morse code.
     * If correct, it proceeds to the next question or ends the game.
     * If incorrect, it records a failed attempt and updates the UI.
     */
    private void checkAnswer() {
        if (stAnswer.equals(currentQuestion.getMorseAnswer())) {
            questionNum++;
            if (questionNum >= questions.size()) {
                saveResults();
                showResultsDialog();
            } else {
                resetAfterQuestion();
            }
        } else {
            nOfTry++;
            failedAttempts++;
            updateTryIndicators();
            if (nOfTry >= 3) {
                showFailedDialog();
            }
        }
    }

    /**
     * Appends a character to the user's answer string.
     * @param s The string to append (".", "-", or " ").
     */
    private void appendToAnswer(String s) {
        if (stAnswer.length() < 30) { // Prevent excessively long input
            stAnswer += s;
            tvInputNumber.setText(stAnswer);
        }
    }

    /**
     * Clears the user's current answer input.
     */
    private void clearAnswer() {
        stAnswer = "";
        tvInputNumber.setText(stAnswer);
    }

    /**
     * Removes the last character from the user's answer.
     */
    private void removeLastChar() {
        if (!stAnswer.isEmpty()) {
            stAnswer = stAnswer.substring(0, stAnswer.length() - 1);
            tvInputNumber.setText(stAnswer);
        }
    }

    /**
     * Saves the final results of the exercise to the database in a background thread.
     */
    private void saveResults() {
        executorService.execute(() -> {
            SQLiteDatabase db = helperDB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(HelperDB.COLUMN_EXERCISE_KIND, getGameTypeString());
            contentValues.put(HelperDB.COLUMN_EXERCISE_MISTAKES, failedAttempts);
            contentValues.put(HelperDB.COLUMN_EXERCISE_DATE, getCurrentDate());
            contentValues.put(HelperDB.COLUMN_EXERCISE_USER, user.getUserPassword()); // Note: Using password as user ID might not be ideal
            db.insert(HelperDB.TABLE_EXERCISE, null, contentValues);
            db.close();
        });
    }

    /**
     * Shows a dialog with the final results when the game is completed successfully.
     */
    private void showResultsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.results_title)
                .setMessage(getString(R.string.results_message, failedAttempts))
                .setCancelable(false)
                .setPositiveButton(R.string.to_dashboard, (dialog, i) -> goToDashboard())
                .create().show();
    }

    /**
     * Shows a dialog indicating that the user has failed the question after 3 attempts.
     */
    private void showFailedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.failed_title)
                .setMessage(getString(R.string.failed_message, currentQuestion.getQuestionText(), currentQuestion.getMorseAnswer()))
                .setCancelable(false)
                .setPositiveButton(R.string.to_dashboard, (dialog, which) -> goToDashboard())
                .create().show();
    }

    /**
     * Navigates the user back to the dashboard.
     */
    private void goToDashboard() {
        Intent goDash = new Intent(this, DashBoard.class);
        goDash.putExtra("user", user);
        startActivity(goDash);
        finish();
    }

    /**
     * Updates the visual indicators (O/Ø) for tracking failed attempts.
     */
    private void updateTryIndicators() {
        // ... (UI update logic)
    }

    /**
     * Initializes all UI elements, loads questions, and sets up the initial game state.
     */
    private void initElements() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        helperDB = new HelperDB(this);

        int gameType = intent.getIntExtra("KEY_SENDER", -1);
        if (gameType == -1) {
            finish(); // Should not happen
            return;
        }

        QuestionRepository questionRepository = new QuestionRepository();
        questions = questionRepository.generateQuestions(gameType, 10);

        // ... (findViewById calls)

        resetAfterQuestion();
    }

    /**
     * Resets the UI and game state for the next question.
     */
    private void resetAfterQuestion() {
        if (questionNum < questions.size()) {
            currentQuestion = questions.get(questionNum);
            tvQuestion.setText(currentQuestion.getQuestionText());
            tvQuestionNum.setText(String.format(Locale.getDefault(), "%d/%d", questionNum + 1, questions.size()));
            clearAnswer();
            nOfTry = 0;
            updateTryIndicators();
        }
    }

    /**
     * Gets the current date as a formatted string.
     * @return The current date in "dd/MM/yyyy" format.
     */
    private String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    /**
     * Gets a string representation of the current game type.
     * @return A string like "Numbers", "Words", etc.
     */
    private String getGameTypeString() {
        int gameType = getIntent().getIntExtra("KEY_SENDER", -1);
        switch (gameType) {
            case GameConstants.GAME_TYPE_NUMBERS:
                return "Numbers";
            case GameConstants.GAME_TYPE_WORDS:
                return "Words";
            case GameConstants.GAME_TYPE_LETTERS:
                return "Letters";
            default:
                return "Unknown";
        }
    }
}

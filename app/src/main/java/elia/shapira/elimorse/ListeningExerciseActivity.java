package elia.shapira.elimorse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An activity that runs a Morse code listening exercise.
 * The app plays a Morse code sound, and the user must type the corresponding text.
 * It tracks attempts, scores, and saves the results to the database.
 */
public class ListeningExerciseActivity extends BaseActivity {

    private TextView tvQuestionNum, tvTryNum1, tvTryNum2, tvTryNum3;
    private EditText etAnswer;
    private Button bPlaySound, bSubmit;

    private List<Question> questions;
    private Question currentQuestion;
    private int questionIndex = 0;
    private int nOfTry = 0;
    private int failedAttempts = 0;
    private MorsePlayer morsePlayer;
    private HelperDB helperDB;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Called when the activity is first created.
     * Initializes UI, loads questions, and sets up the game state.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_exercise);

        initElements();
        loadNextQuestion();

        bPlaySound.setOnClickListener(v -> morsePlayer.playAsSound(currentQuestion.getMorseAnswer()));
        bSubmit.setOnClickListener(v -> checkAnswer());
    }

    /**
     * Initializes all UI elements, the MorsePlayer, the database helper, and loads questions.
     */
    private void initElements() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        helperDB = new HelperDB(this);
        morsePlayer = new MorsePlayer(this);

        tvQuestionNum = findViewById(R.id.tvQuestionNum);
        tvTryNum1 = findViewById(R.id.tvTryNum1);
        tvTryNum2 = findViewById(R.id.tvTryNum2);
        tvTryNum3 = findViewById(R.id.tvTryNum3);
        etAnswer = findViewById(R.id.etAnswer);
        bPlaySound = findViewById(R.id.bPlaySound);
        bSubmit = findViewById(R.id.bSubmit);

        int gameType = intent.getIntExtra("KEY_SENDER", GameConstants.GAME_TYPE_LISTENING);
        QuestionRepository questionRepository = new QuestionRepository();
        questions = questionRepository.generateQuestions(gameType, 10);
    }

    /**
     * Loads the next question in the list, or ends the game if all questions are answered.
     * Automatically plays the Morse code sound for the new question.
     */
    private void loadNextQuestion() {
        if (questionIndex < questions.size()) {
            currentQuestion = questions.get(questionIndex);
            tvQuestionNum.setText(String.format(Locale.getDefault(), "%d/%d", questionIndex + 1, questions.size()));
            etAnswer.setText("");
            nOfTry = 0;
            updateTryIndicators();
            // Play the sound automatically after a short delay
            new Handler(Looper.getMainLooper()).postDelayed(() -> morsePlayer.playAsSound(currentQuestion.getMorseAnswer()), 1000);
        } else {
            saveResults();
            showResultsDialog();
        }
    }

    /**
     * Checks the user's answer against the correct question text.
     * Proceeds to the next question on success, or tracks a failed attempt.
     */
    private void checkAnswer() {
        String userAnswer = etAnswer.getText().toString().trim();
        if (userAnswer.equalsIgnoreCase(currentQuestion.getQuestionText())) {
            questionIndex++;
            loadNextQuestion();
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
     * Updates the color and text of the try indicators based on the number of failed attempts.
     */
    private void updateTryIndicators() {
        // ... (UI update logic)
    }

    /**
     * Saves the exercise results to the database in a background thread.
     */
    private void saveResults() {
        executorService.execute(() -> {
            SQLiteDatabase db = helperDB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(HelperDB.COLUMN_EXERCISE_KIND, "Listening");
            contentValues.put(HelperDB.COLUMN_EXERCISE_MISTAKES, failedAttempts);
            contentValues.put(HelperDB.COLUMN_EXERCISE_DATE, getCurrentDate());
            contentValues.put(HelperDB.COLUMN_EXERCISE_USER, user.getUserPassword());
            db.insert(HelperDB.TABLE_EXERCISE, null, contentValues);
            db.close();
        });
    }

    /**
     * Shows a dialog with the final results when the game is completed.
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
     * Shows a dialog indicating failure after 3 incorrect attempts.
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
     * Navigates the user to the dashboard and finishes the current activity.
     */
    private void goToDashboard() {
        Intent goDash = new Intent(this, DashBoard.class);
        goDash.putExtra("user", user);
        startActivity(goDash);
        finish();
    }

    /**
     * Gets the current date formatted as a string.
     * @return The current date in "dd/MM/yyyy" format.
     */
    private String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    /**
     * Called when the activity is no longer visible to the user.
     * Stops any ongoing Morse code playback to free up resources.
     */
    @Override
    protected void onStop() {
        super.onStop();
        morsePlayer.stop();
    }
}

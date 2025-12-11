package elia.shapira.elimorse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Exercise extends BaseActivity {

    private RadioButton rbNumbers, rbWords, rbLetters, rbListening;
    private Button bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuExer);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));

        bNext.setOnClickListener(view -> {
            int gameType = findGameType();

            if (gameType != -1) {
                Intent intent;
                if (gameType == GameConstants.GAME_TYPE_LISTENING) {
                    intent = new Intent(this, ListeningExerciseActivity.class);
                } else {
                    intent = new Intent(this, ExerciseGame.class);
                }
                intent.putExtra("KEY_SENDER", gameType);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.select_game_type, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initElements() {
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
        rbNumbers = findViewById(R.id.rbNumbers);
        rbWords = findViewById(R.id.rbWords);
        rbLetters = findViewById(R.id.rbLetters);
        rbListening = findViewById(R.id.rbListening);
        bNext = findViewById(R.id.bNext);
    }

    public int findGameType() {
        if (rbNumbers.isChecked()) return GameConstants.GAME_TYPE_NUMBERS;
        if (rbWords.isChecked()) return GameConstants.GAME_TYPE_WORDS;
        if (rbLetters.isChecked()) return GameConstants.GAME_TYPE_LETTERS;
        if (rbListening.isChecked()) return GameConstants.GAME_TYPE_LISTENING;
        else return -1;
    }
}
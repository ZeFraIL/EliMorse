package elia.shapira.elimorse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Translate extends AppCompatActivity {

    private static final int DOT_DURATION = 250; // ms
    private static final int DASH_DURATION = 3 * DOT_DURATION;
    private static final int PART_PAUSE_DURATION = DOT_DURATION;
    private static final int LETTER_PAUSE_DURATION = 3 * DOT_DURATION;
    private static final int WORD_PAUSE_DURATION = 7 * DOT_DURATION;

    private Context context;
    private TextView tvTranslateOutcome, tvMenu;
    private EditText etTranslateIncome;
    private int what_switch = 0;
    private User user;
    private CameraManager cameraManager;
    private String cameraId;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Map<String, String> morseMap = new HashMap<>();
    private final Map<String, String> textMap = new HashMap<>();
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 200);

        initElements();
        initMorseMap();

        bSwitch.setOnClickListener(v -> {
            what_switch++;
            updateSwitchButton();
        });

        bTranslate.setOnClickListener(view -> {
            String inputText = etTranslateIncome.getText().toString().trim();
            if (what_switch % 2 == 0) {
                toMorse(inputText);
            } else {
                toText(inputText);
            }
        });

        bDictionary.setOnClickListener(v -> {
            Intent goGuideList = new Intent(context, GuideList.class);
            startActivity(goGuideList);
        });

        bAudio.setOnClickListener(v -> {
            if (isPlaying) return;
            String morseCode = tvTranslateOutcome.getText().toString();
            if (!morseCode.isEmpty()) {
                playSequence(morseCode, 0);
            }
        });

        bFlashlight.setOnClickListener(v -> {
            if (isPlaying) return;
            String morseCode = tvTranslateOutcome.getText().toString();
            if (morseCode.isEmpty()) {
                MyToast.showToast(context, getString(R.string.morse_code_is_empty));
                return;
            }
            flashMorseCode(morseCode, 0);
        });

        tvMenu.setOnClickListener(v -> showPopupMenu());
    }

    private void toText(String morseInput) {
        String[] morseArray = morseInput.split("/");
        StringBuilder textBuilder = new StringBuilder();
        for (String morseChar : morseArray) {
            String textChar = textMap.get(morseChar);
            if (textChar != null) {
                textBuilder.append(textChar);
            }
        }
        tvTranslateOutcome.setText(textBuilder.toString());
    }

    private void toMorse(String textInput) {
        String upperCaseInput = textInput.toUpperCase();
        StringBuilder morseBuilder = new StringBuilder();
        for (int i = 0; i < upperCaseInput.length(); i++) {
            String character = String.valueOf(upperCaseInput.charAt(i));
            String morseChar = morseMap.get(character);
            if (morseChar != null) {
                morseBuilder.append(morseChar);
                if (i < upperCaseInput.length() - 1) {
                    morseBuilder.append(" "); // Letter separator
                }
            }
        }
        tvTranslateOutcome.setText(morseBuilder.toString());
    }

    private void playSequence(String morseCode, int index) {
        if (index >= morseCode.length()) {
            isPlaying = false;
            return;
        }
        isPlaying = true;

        char symbol = morseCode.charAt(index);
        switch (symbol) {
            case '.':
                playSound(R.raw.dot1, DOT_DURATION, () -> playSequence(morseCode, index + 1));
                break;
            case '-':
                playSound(R.raw.dash1, DASH_DURATION, () -> playSequence(morseCode, index + 1));
                break;
            case ' ':
                handler.postDelayed(() -> playSequence(morseCode, index + 1), LETTER_PAUSE_DURATION - PART_PAUSE_DURATION);
                break;
            case '/':
                handler.postDelayed(() -> playSequence(morseCode, index + 1), WORD_PAUSE_DURATION - LETTER_PAUSE_DURATION);
                break;
        }
    }

    private void playSound(int soundId, int duration, Runnable onCompletion) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            handler.postDelayed(onCompletion, PART_PAUSE_DURATION);
        });
        mediaPlayer.start();
    }

    private void flashMorseCode(String morseCode, int index) {
        if (index >= morseCode.length()) {
            isPlaying = false;
            return;
        }
        isPlaying = true;

        char symbol = morseCode.charAt(index);
        switch (symbol) {
            case '.':
                flash(DOT_DURATION, () -> flashMorseCode(morseCode, index + 1));
                break;
            case '-':
                flash(DASH_DURATION, () -> flashMorseCode(morseCode, index + 1));
                break;
            case ' ':
                handler.postDelayed(() -> flashMorseCode(morseCode, index + 1), LETTER_PAUSE_DURATION - PART_PAUSE_DURATION);
                break;
            case '/':
                handler.postDelayed(() -> flashMorseCode(morseCode, index + 1), WORD_PAUSE_DURATION - LETTER_PAUSE_DURATION);
                break;
        }
    }

    private void flash(int duration, Runnable onCompletion) {
        try {
            cameraManager.setTorchMode(cameraId, true);
            handler.postDelayed(() -> {
                try {
                    cameraManager.setTorchMode(cameraId, false);
                    handler.postDelayed(onCompletion, PART_PAUSE_DURATION);
                } catch (CameraAccessException e) {
                    handleCameraError(e);
                }
            }, duration);
        } catch (CameraAccessException e) {
            handleCameraError(e);
        }
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, tvMenu);
        popupMenu.getMenuInflater().inflate(R.menu.total_menu, popupMenu.getMenu());
        MenuItem clearMenuItem = popupMenu.getMenu().add(Menu.NONE, 1, 0, getString(R.string.clear));
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.guide) {
                navigateTo(Guide.class, getString(R.string.guide_part));
            } else if (itemId == R.id.credits) {
                navigateTo(AboutMe.class, getString(R.string.about_me_part));
            } else if (itemId == R.id.reminder) {
                navigateTo(Reminder.class, getString(R.string.reminder_part));
            } else if (itemId == R.id.back) {
                finish();
            } else if (itemId == R.id.exit) {
                finishAffinity();
            } else if (itemId == 1) {
                tvTranslateOutcome.setText("");
                etTranslateIncome.setText("");
            }
            return true;
        });
        popupMenu.show();
    }

    private void navigateTo(Class<?> activityClass, String sectionName) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra("user", user);
        sayWhat(sectionName);
        startActivity(intent);
    }

    private void sayWhat(String sayThis) {
        Intent goService = new Intent(context, TTS_Service.class);
        goService.putExtra("what", getString(R.string.you_go_to_section, sayThis));
        startService(goService);
    }

    private void initElements() {
        context = Translate.this;
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
        etTranslateIncome = findViewById(R.id.etTranslateIncome);
        tvTranslateOutcome = findViewById(R.id.tvTranslateOutcome);
        Button bAudio = findViewById(R.id.bAudio);
        Button bSwitch = findViewById(R.id.bSwitch);
        Button bFlashlight = findViewById(R.id.bFlashlight);
        Button bDictionary = findViewById(R.id.bDictionary);
        bTranslate = findViewById(R.id.bTranslate);
        tvMenu = findViewById(R.id.tvMenu);
        updateSwitchButton();
        try {
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (cameraManager != null) {
                cameraId = cameraManager.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            handleCameraError(e);
        }
    }

    private void handleCameraError(CameraAccessException e) {
        e.printStackTrace();
        Toast.makeText(context, R.string.camera_not_available, Toast.LENGTH_SHORT).show();
    }

    private void updateSwitchButton() {
        if (what_switch % 2 == 0) {
            bSwitch.setText(R.string.text_to_morse);
        } else {
            bSwitch.setText(R.string.morse_to_text);
        }
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
            textMap.put(pair[1], pair[0]);
        }
    }
}

package elia.shapira.elimorse;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Translate extends BaseActivity {

    private static final int DOT_DURATION = 250; // ms
    private static final int DASH_DURATION = 3 * DOT_DURATION;
    private static final int PART_PAUSE_DURATION = DOT_DURATION;
    private static final int LETTER_PAUSE_DURATION = 3 * DOT_DURATION;
    private static final int WORD_PAUSE_DURATION = 7 * DOT_DURATION;

    private Context context;
    private TextView tvTranslateOutcome, tvMenu;
    private EditText etTranslateIncome;
    private Button bSwitch, bTranslate, bDictionary, bAudio, bFlashlight;
    private int what_switch = 0;
    private CameraManager cameraManager;
    private String cameraId;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final MorseTranslator translator = new MorseTranslator();
    private boolean isPlaying = false;

    private SoundPool soundPool;
    private int dotSoundId, dashSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 200);

        initElements();
        initSoundPool();

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
                My_Toast.showToast(context, getString(R.string.morse_code_is_empty));
                return;
            }
            flashMorseCode(morseCode, 0);
        });

        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));
    }

    private void initSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        dotSoundId = soundPool.load(this, R.raw.dot1, 1);
        dashSoundId = soundPool.load(this, R.raw.dash1, 1);
    }

    private void toText(String morseInput) {
        tvTranslateOutcome.setText(translator.toText(morseInput));
    }

    private void toMorse(String textInput) {
        tvTranslateOutcome.setText(translator.toMorse(textInput));
    }

    private void playSequence(String morseCode, int index) {
        if (index >= morseCode.length()) {
            isPlaying = false;
            return;
        }
        isPlaying = true;

        char symbol = morseCode.charAt(index);
        long delay = 0;

        switch (symbol) {
            case '.':
                soundPool.play(dotSoundId, 1, 1, 1, 0, 1);
                delay = DOT_DURATION + PART_PAUSE_DURATION;
                break;
            case '-':
                soundPool.play(dashSoundId, 1, 1, 1, 0, 1);
                delay = DASH_DURATION + PART_PAUSE_DURATION;
                break;
            case ' ':
                delay = LETTER_PAUSE_DURATION - PART_PAUSE_DURATION;
                break;
            case '/':
                delay = WORD_PAUSE_DURATION - LETTER_PAUSE_DURATION;
                break;
            default:
                delay = 0;
        }

        handler.postDelayed(() -> playSequence(morseCode, index + 1), delay);
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
            default:
                flashMorseCode(morseCode, index + 1);
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

    @Override
    protected void showPopupMenu(TextView anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
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

    private void initElements() {
        context = Translate.this;
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
        etTranslateIncome = findViewById(R.id.etTranslateIncome);
        tvTranslateOutcome = findViewById(R.id.tvTranslateOutcome);
        bAudio = findViewById(R.id.bAudio);
        bSwitch = findViewById(R.id.bSwitch);
        bFlashlight = findViewById(R.id.bFlashlight);
        bDictionary = findViewById(R.id.bDictionary);
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

    @Override
    protected void onDestroy() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        super.onDestroy();
    }
}

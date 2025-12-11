package elia.shapira.elimorse;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * A helper class for playing Morse code sequences as either sound or flashlight pulses.
 * It handles the standard timing for dots, dashes, and pauses.
 * This class is designed to be reusable across different activities.
 */
public class MorsePlayer {

    private static final int DOT_DURATION = 250; // ms
    private static final int DASH_DURATION = 3 * DOT_DURATION;
    private static final int PART_PAUSE_DURATION = DOT_DURATION;
    private static final int LETTER_PAUSE_DURATION = 3 * DOT_DURATION;
    private static final int WORD_PAUSE_DURATION = 7 * DOT_DURATION;

    private final Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isPlaying = false;

    private CameraManager cameraManager;
    private String cameraId;

    /**
     * Constructs a new MorsePlayer.
     * @param context The application context, used for accessing resources and system services.
     */
    public MorsePlayer(Context context) {
        this.context = context;
        try {
            this.cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (this.cameraManager != null) {
                this.cameraId = this.cameraManager.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            Log.e("MorsePlayer", "Failed to access camera for flashlight.", e);
            Toast.makeText(context, R.string.camera_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Starts playing the given Morse code sequence using sound.
     * If a sequence is already playing, this call is ignored.
     * @param morseCode The Morse code string to play. Dots (.), dashes (-), spaces ( ), and word separators (/) are supported.
     */
    public void playAsSound(String morseCode) {
        if (isPlaying || morseCode == null || morseCode.isEmpty()) return;
        isPlaying = true;
        playSequence(morseCode, 0, true);
    }

    /**
     * Starts playing the given Morse code sequence using the device's flashlight.
     * If a sequence is already playing, or if the camera is unavailable, this call is ignored.
     * @param morseCode The Morse code string to play.
     */
    public void playAsFlash(String morseCode) {
        if (isPlaying || morseCode == null || morseCode.isEmpty() || cameraId == null) return;
        isPlaying = true;
        playSequence(morseCode, 0, false);
    }

    /**
     * Recursively plays the Morse code sequence character by character.
     * @param morseCode The full Morse code string.
     * @param index The index of the character to process.
     * @param isSound True to play using sound, false to use the flashlight.
     */
    private void playSequence(String morseCode, int index, boolean isSound) {
        if (index >= morseCode.length()) {
            isPlaying = false;
            return;
        }

        char symbol = morseCode.charAt(index);
        Runnable nextAction = () -> playSequence(morseCode, index + 1, isSound);

        switch (symbol) {
            case '.':
                if (isSound) playSound(nextAction);
                else flash(DOT_DURATION, nextAction);
                break;
            case '-':
                if (isSound) playSound(nextAction);
                else flash(DASH_DURATION, nextAction);
                break;
            case ' ':
                handler.postDelayed(nextAction, LETTER_PAUSE_DURATION - PART_PAUSE_DURATION);
                break;
            case '/':
                handler.postDelayed(nextAction, WORD_PAUSE_DURATION - LETTER_PAUSE_DURATION);
                break;
            default: // Skip unknown characters
                nextAction.run();
                break;
        }
    }

    /**
     * Plays a single Morse sound (dot or dash).
     * @param onCompletion A callback to execute after the sound and subsequent pause have completed.
     */
    private void playSound(Runnable onCompletion) {
        int soundId = R.raw.dot1; // Assuming dot1 and dash1 have same duration now handled by timing
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            handler.postDelayed(onCompletion, PART_PAUSE_DURATION);
        });
        mediaPlayer.start();
    }

    /**
     * Flashes the torch for a single Morse signal (dot or dash).
     * @param duration The duration of the flash in milliseconds.
     * @param onCompletion A callback to execute after the flash and subsequent pause have completed.
     */
    private void flash(int duration, Runnable onCompletion) {
        try {
            cameraManager.setTorchMode(cameraId, true);
            handler.postDelayed(() -> {
                try {
                    cameraManager.setTorchMode(cameraId, false);
                    handler.postDelayed(onCompletion, PART_PAUSE_DURATION);
                } catch (CameraAccessException e) {
                    Log.e("MorsePlayer", "Failed to turn off torch.", e);
                }
            }, duration);
        } catch (CameraAccessException e) {
            Log.e("MorsePlayer", "Failed to turn on torch.", e);
        }
    }

    /**
     * Stops any currently playing Morse code sequence immediately.
     */
    public void stop() {
        isPlaying = false;
        handler.removeCallbacksAndMessages(null);
    }
}

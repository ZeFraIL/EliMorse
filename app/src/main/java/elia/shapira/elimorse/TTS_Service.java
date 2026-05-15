package elia.shapira.elimorse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.ArrayList;

/**
 * A background service that provides Text-To-Speech (TTS) capabilities for the application.
 * It initializes the system TTS engine and speaks text strings received via intents.
 * If multiple requests are received before initialization is complete, they are queued and spoken once ready.
 */
public class TTS_Service extends Service implements TextToSpeech.OnInitListener {

    /** The TextToSpeech engine instance. */
    private TextToSpeech tts;
    /** Flag indicating whether the TTS engine has been successfully initialized. */
    private boolean isInitialized = false;
    /** Queue for text requests received before initialization finishes. */
    private ArrayList<String> pendingSpeaks = new ArrayList<>();

    /**
     * Initializes the service and the TTS engine.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(getApplicationContext(), this);
        Log.d("TTS_Service", "TTS Service created");
    }

    /**
     * Handles incoming requests to speak text.
     * Extracts the text from the intent and either speaks it immediately or queues it for later.
     *
     * @param intent  The intent containing the "what" extra string to speak.
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return {@code START_NOT_STICKY} to ensure the service is not automatically restarted if killed.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("what")) {
            String textToSpeak = intent.getStringExtra("what");
            if (isInitialized) {
                speak(textToSpeak);
            } else {
                pendingSpeaks.add(textToSpeak);
            }
        }
        return START_NOT_STICKY;
    }

    /**
     * Callback from the TTS engine indicating initialization status.
     * Sets the language to US English and processes any pending speech requests.
     *
     * @param status {@code TextToSpeech.SUCCESS} or {@code TextToSpeech.ERROR}.
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS_Service", "Language is not supported");
                Toast.makeText(this, "Language is not supported", Toast.LENGTH_SHORT).show();
            } else {
                isInitialized = true;
                Log.d("TTS_Service", "TTS Initialized");
                for (String text : pendingSpeaks) {
                    speak(text);
                }
                pendingSpeaks.clear();
            }
        } else {
            Log.e("TTS_Service", "TTS Initialization failed");
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Speaks the provided text using the TTS engine.
     * Uses {@code QUEUE_FLUSH} to interrupt any current speech and speak the new text immediately.
     *
     * @param text The text string to speak.
     */
    private void speak(String text) {
        if (tts != null && isInitialized) {
            // Use QUEUE_FLUSH to interrupt previous speech and speak the newest message immediately.
            // This makes the UI feel more responsive.
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_MSG_ID");
        }
    }

    /**
     * Shuts down the TTS engine and cleans up resources when the service is destroyed.
     */
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            Log.d("TTS_Service", "TTS Service destroyed and shutdown");
        }
        super.onDestroy();
    }

    /**
     * Binding is not supported for this service.
     * @param intent The intent used for binding.
     * @return Always null.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

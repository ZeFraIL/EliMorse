package elia.shapira.elimorse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.ArrayList;

public class TTS_Service extends Service implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean isInitialized = false;
    private ArrayList<String> pendingSpeaks = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(getApplicationContext(), this);
        Log.d("TTS_Service", "TTS Service created");
    }

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

    private void speak(String text) {
        if (tts != null && isInitialized) {
            // Use QUEUE_FLUSH to interrupt previous speech and speak the newest message immediately.
            // This makes the UI feel more responsive.
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_MSG_ID");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            Log.d("TTS_Service", "TTS Service destroyed and shutdown");
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

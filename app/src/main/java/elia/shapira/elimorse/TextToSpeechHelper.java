package elia.shapira.elimorse;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechHelper {

    private static TextToSpeech textToSpeech;

    /*public static void initializeTextToSpeech(Context context) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = textToSpeech.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(context, "Problem with language", Toast.LENGTH_SHORT).show();
                        }
                        else  {
                            Toast.makeText(context, "Initialization is Ok", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Problem with initialization", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

    public static void speak(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public static void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}

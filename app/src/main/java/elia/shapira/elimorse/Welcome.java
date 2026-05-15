package elia.shapira.elimorse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * The splash screen activity that welcomes the user.
 * It displays the app logo with animations and a welcome message via TTS.
 * Automatically transitions to the login/registration screen after a delay.
 */
public class Welcome extends AppCompatActivity {

    /** Logo ImageView for animation. */
    ImageView ivLogo;
    /** Button to skip the splash screen and start immediately. */
    Button bStart;
    /** Activity context. */
    Context context;
    /** Timer for automatic transition to the next screen. */
    CountDownTimer cdt;

    /**
     * Initializes the welcome screen, applies theme, and starts the countdown timer.
     * @param savedInstanceState Saved state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();
        setContentView(R.layout.activity_welcome);

        initElements();

        // Start 7-second countdown for auto-transition
        cdt = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                startMainScreen();
            }
        };

        cdt.start();

        bStart.setOnClickListener(view -> {
            cdt.cancel();
            startMainScreen();
        });
    }

    /**
     * Transitions to the LogAndReg activity.
     */
    private void startMainScreen() {
        Intent go = new Intent(this, LogAndReg.class);
        startActivity(go);
        finish();
    }

    /**
     * Applies the saved theme preference (Light/Dark/System) before the view is created.
     */
    private void applyTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int mode = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    /**
     * Initializes UI elements, starts logo animations, and triggers the welcome TTS message.
     */
    private void initElements() {
        context = this;
        ivLogo = findViewById(R.id.ivLogo);
        bStart = findViewById(R.id.bStart);

        // Start logo animations
        Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoomin);
        ivLogo.startAnimation(zoomIn);
        ivLogo.animate().rotation(360f).setDuration(5000).start();

        // Start TTS service for the welcome message
        Intent goService = new Intent(context, TTS_Service.class);
        goService.putExtra("what", getString(R.string.welcome_message));
        startService(goService);
    }
}

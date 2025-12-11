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

public class Welcome extends AppCompatActivity {

    ImageView ivLogo;
    Button bStart;
    Context context;
    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();
        setContentView(R.layout.activity_welcome);

        initElements();

        cdt = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent go = new Intent(context, LogAndReg.class);
                startActivity(go);
                finish(); // Finish Welcome activity
            }
        };

        cdt.start();

        bStart.setOnClickListener(view -> {
            cdt.cancel();
            Intent go = new Intent(context, LogAndReg.class);
            startActivity(go);
            finish(); // Finish Welcome activity
        });
    }

    private void applyTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int mode = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    private void initElements() {
        context = this;
        ivLogo = findViewById(R.id.ivLogo);
        bStart = findViewById(R.id.bStart);

        // Start animations
        Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoomin);
        ivLogo.startAnimation(zoomIn);
        ivLogo.animate().rotation(360f).setDuration(5000).start();

        // Start TTS service
        Intent goService = new Intent(context, TTS_Service.class);
        goService.putExtra("what", getString(R.string.welcome_message));
        startService(goService);
    }
}

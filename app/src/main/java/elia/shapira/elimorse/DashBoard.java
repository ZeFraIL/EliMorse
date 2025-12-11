package elia.shapira.elimorse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * The main dashboard of the application, displayed after successful login.
 * It serves as the central hub for navigating to all major features of the app,
 * such as exercises, translation, guides, and more.
 * It also provides access to the application-wide theme switcher.
 */
public class DashBoard extends BaseActivity {

    private SharedPreferences sharedPreferences;

    /**
     * Called when the activity is first created.
     * Initializes the UI, sets up navigation buttons, and retrieves the current user.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuDB);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));

        Button bExercise = findViewById(R.id.bExercise);
        Button bTranslate = findViewById(R.id.bTranslate);
        Button bGuide = findViewById(R.id.bGuide);
        Button bAbout = findViewById(R.id.bAbout);
        Button bHistory = findViewById(R.id.bHistory);
        Button bReminder = findViewById(R.id.bReminder);

        bExercise.setOnClickListener(view -> navigateTo(Exercise.class, getString(R.string.exercise_part)));
        bTranslate.setOnClickListener(view -> navigateTo(Translate.class, getString(R.string.translate_part)));
        bGuide.setOnClickListener(view -> navigateTo(Guide.class, getString(R.string.guide_part)));
        bAbout.setOnClickListener(view -> navigateTo(AboutMe.class, getString(R.string.about_me_part)));
        bHistory.setOnClickListener(view -> navigateTo(History.class, getString(R.string.history_part)));
        bReminder.setOnClickListener(view -> navigateTo(Reminder.class, getString(R.string.reminder_part)));
    }

    /**
     * Overridden to include the theme switcher option in the popup menu.
     * @param anchorView The TextView to which the popup menu should be anchored.
     */
    @Override
    protected void showPopupMenu(TextView anchorView) {
        super.showPopupMenu(anchorView);
        // We need to add the theme option dynamically as it's specific to this menu.
        // A better approach might be to have different menu resources.
        anchorView.getMenu().findItem(R.id.theme).setOnMenuItemClickListener(item -> {
            showThemeDialog();
            return true;
        });
    }

    /**
     * Displays a dialog for the user to choose the app theme (Light, Dark, or System Default).
     * The selected theme is applied immediately and saved to SharedPreferences.
     */
    private void showThemeDialog() {
        String[] themes = {getString(R.string.theme_light), getString(R.string.theme_dark), getString(R.string.theme_system_default)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.choose_theme));
        builder.setItems(themes, (dialog, which) -> {
            int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
            switch (which) {
                case 0:
                    mode = AppCompatDelegate.MODE_NIGHT_NO;
                    break;
                case 1:
                    mode = AppCompatDelegate.MODE_NIGHT_YES;
                    break;
            }
            AppCompatDelegate.setDefaultNightMode(mode);
            sharedPreferences.edit().putInt("theme_mode", mode).apply();
        });
        builder.show();
    }

    /**
     * Initializes shared preferences for the theme and retrieves the user object from the intent.
     */
    private void initElements() {
        sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        Intent takeIt = getIntent();
        if (takeIt.hasExtra("user")) {
            user = (User) takeIt.getSerializableExtra("user");
        }
    }
}

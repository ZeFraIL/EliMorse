package elia.shapira.elimorse;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

/**
 * An abstract base activity that provides common functionality for other activities in the application.
 * This includes a shared popup menu for navigation and a standardized method for TTS announcements.
 * Activities extending this class should call {@code super.onCreate()} and can use the provided methods.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * The current user object, typically retrieved from the intent that started the activity.
     * It can be null if no user is passed.
     */
    protected User user;

    /**
     * Shows a standardized popup menu for navigation.
     * The menu is inflated from {@code R.menu.total_menu}.
     *
     * @param anchorView The TextView to which the popup menu should be anchored.
     */
    protected void showPopupMenu(TextView anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.total_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.guide) {
                navigateTo(Guide.class, getString(R.string.guide_part));
            } else if (itemId == R.id.credits) {
                navigateTo(AboutMe.class, getString(R.string.about_me_part));
            } else if (itemId == R.id.reminder) {
                navigateTo(Reminder.class, getString(R.string.reminder_part));
            } else if (itemId == R.id.theme) {
                // The theme dialog is handled in activities that need it, like DashBoard.
                // Consider moving theme dialog logic here if it becomes more common.
            } else if (itemId == R.id.back) {
                finish();
            } else if (itemId == R.id.exit) {
                finishAffinity();
            }
            return true;
        });
        popupMenu.show();
    }

    /**
     * Navigates to another activity, passing the current user object and announcing the action via TTS.
     *
     * @param activityClass The class of the destination activity.
     * @param sectionName   The name of the section being navigated to, used for the TTS announcement.
     */
    protected void navigateTo(Class<?> activityClass, String sectionName) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("user", user);
        sayWhat(sectionName);
        startActivity(intent);
    }

    /**
     * Uses the {@link TTS_Service} to announce a given text string.
     *
     * @param sayThis The text to be spoken.
     */
    protected void sayWhat(String sayThis) {
        Intent goService = new Intent(this, TTS_Service.class);
        goService.putExtra("what", getString(R.string.you_go_to_section, sayThis));
        startService(goService);
    }
}

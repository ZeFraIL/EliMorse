package elia.shapira.elimorse;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.HapticFeedbackConstants;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * AboutMe Activity displays information about the developer and provides
 * buttons to contact them via SMS, phone call, or email.
 * It dynamically enables/disables contact buttons based on network and telephony availability.
 * This activity inherits common menu functionality from {@link BaseActivity}.
 */
public class AboutMe extends BaseActivity {

    private Button bSMS, bEmail, bCall;
    private ConnectivityReceiver connectivityReceiver;

    /**
     * Called when the activity is first created.
     * Initializes the UI components, sets up click listeners with haptic feedback,
     * and prepares the connectivity receiver.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        initUser();

        TextView tvMenu = findViewById(R.id.tvMenuAboutMe);
        tvMenu.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            showPopupMenu(tvMenu);
        });

        bSMS = findViewById(R.id.bSMS);
        bEmail = findViewById(R.id.bEmail);
        bCall = findViewById(R.id.bCall);

        bSMS.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sendSms();
        });
        bCall.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            makeCall();
        });
        bEmail.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sendEmail();
        });

        connectivityReceiver = new ConnectivityReceiver();
    }

    /**
     * Called when the activity will start interacting with the user.
     * Registers the {@link ConnectivityReceiver} to listen for network changes
     * and updates the button states.
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
        updateButtonStates();
    }

    /**
     * Called when the activity is no longer interacting with the user.
     * Unregisters the {@link ConnectivityReceiver} to save system resources.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectivityReceiver);
    }

    /**
     * Updates the enabled state of the contact buttons based on service availability.
     * The Email button is enabled if a network connection is available.
     * The SMS and Call buttons are enabled if telephony services are available.
     */
    private void updateButtonStates() {
        bEmail.setEnabled(isNetworkAvailable());
        boolean telephonyAvailable = isTelephonyAvailable();
        bSMS.setEnabled(telephonyAvailable);
        bCall.setEnabled(telephonyAvailable);
    }

    /**
     * Checks if an active network connection is available.
     * @return true if a network connection is available, false otherwise.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Checks if the device has telephony capabilities and a ready SIM card.
     * @return true if telephony is available, false otherwise.
     */
    private boolean isTelephonyAvailable() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            return false;
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * Initializes the user object by retrieving it from the intent extras.
     */
    private void initUser() {
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
        }
    }

    /**
     * Initiates an intent to send an SMS to the developer.
     * The phone number and message body are retrieved from string resources.
     * Shows a Toast if no SMS app is found.
     */
    private void sendSms() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + getString(R.string.developer_phone_number)));
        intent.putExtra("sms_body", getString(R.string.sms_message));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initiates an intent to open the dialer with the developer's phone number.
     * The phone number is retrieved from string resources.
     * Shows a Toast if no phone app is found.
     */
    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.developer_phone_number)));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initiates an intent to send an email to the developer.
     * The email address, subject, and body are retrieved from string resources.
     * Shows a Toast if no email app is found.
     */
    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.developer_email), null));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_message));
        try {
            startActivity(Intent.createChooser(intent, "Send email..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A BroadcastReceiver that listens for system-wide connectivity changes.
     * On receiving a broadcast, it triggers an update of the contact button states.
     */
    public class ConnectivityReceiver extends BroadcastReceiver {
        /**
         * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
         * It checks for connectivity changes and calls {@link #updateButtonStates()}.
         *
         * @param context The Context in which the receiver is running.
         * @param intent The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                updateButtonStates();
            }
        }
    }
}

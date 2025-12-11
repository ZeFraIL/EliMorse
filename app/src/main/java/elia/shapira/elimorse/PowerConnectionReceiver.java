package elia.shapira.elimorse;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import androidx.appcompat.app.AlertDialog;

public class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean charging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;
        if (charging) {
            unregisterBatteryReceiver(context);
        }
    }

    private void unregisterBatteryReceiver(Context context) {
        BatteryLevelReceiver batteryReceiver = new BatteryLevelReceiver();
        context.unregisterReceiver(batteryReceiver);
    }

    public static void showLowBatteryAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("charge your phone")
                .setTitle("Low battery")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
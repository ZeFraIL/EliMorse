package elia.shapira.elimorse;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

public class BatteryLevelReceiver extends BroadcastReceiver {

    private static final int MAX_REPEATS = 10;
    private static final int REPEAT_INTERVAL = 60000;

    private int repeatCount = 0;
    private int level;

    @Override
    public void onReceive(Context context, Intent intent) {
        level=intent.getIntExtra("level",-1);
        if (level<20)
            MyToast.showToast(context, context.getString(R.string.battery_level, level));
        if (level < 10) {
            PowerConnectionReceiver.showLowBatteryAlert(context);
            startVoiceNotifications(context);
        }
    }

    private void startVoiceNotifications(Context context) {
        if (repeatCount >= MAX_REPEATS) {
            return;
        }
        new CountDownTimer(REPEAT_INTERVAL, REPEAT_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (level>20)
                    cancel();
                Intent goService=new Intent(context, TTS_Service.class);
                goService.putExtra("what","Low battery level");
                context.startService(goService);
                repeatCount++;
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }
}

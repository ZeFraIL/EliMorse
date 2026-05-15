package elia.shapira.elimorse;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

/**
 * Activity for setting exercise reminders using system alarms and notifications.
 * Users can set a reminder for a specific date and time or after a delay.
 */
public class Reminder extends AppCompatActivity {

    private Context context;
    /** TextView to display the selected date and time. */
    private TextView tvWhere;
    /** Button to open the date/time picker. */
    private Button bWhere;
    /** Button to set an exact alarm. */
    private Button bSetAlarm;
    /** Button to set an alarm after a relative delay. */
    private Button bAddTimeAlarm;
    /** EditText to enter delay in seconds. */
    private EditText etTimeAfter;
    /** Calendars for current time and target notification time. */
    private Calendar calendar, cNoti;
    private int notiYear, notiMonth, notiDay, notiHour, notiMinute,
            cyear, cmonth, cday, cminute, chour;
    /** String representing the selected date/time in human-readable format. */
    private String stWhere = "";
    private String UserPassword;


    /**
     * Initializes the reminder activity, requests notification permissions, and sets up alarm buttons.
     * @param savedInstanceState Saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 100);

        initElements();

        bAddTimeAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st = etTimeAfter.getText().toString();
                if (st.isEmpty()) {
                    Toast.makeText(context, R.string.please_enter_time, Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int t = Integer.parseInt(st);
                    long afterT = System.currentTimeMillis() + 1000 * t;
                    Intent after = new Intent(context, ReceiverAfterTime.class);
                    PendingIntent afterIntent = PendingIntent.getBroadcast(context,
                            2, after, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, afterT, afterIntent);
                    Toast.makeText(context, getString(R.string.alarm_set_after, t), Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(context, R.string.invalid_number_format, Toast.LENGTH_SHORT).show();
                }
            }
        });

        bWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);
                chour = calendar.get(Calendar.HOUR);
                cminute = calendar.get(Calendar.MINUTE);
                choiceDate();
            }
        });

        bSetAlarm=findViewById(R.id.bSetAlarm);
        bSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent after=new Intent(context,ReceiverAfterTime.class);
                PendingIntent afterIntent=PendingIntent.getBroadcast(context,
                        0,after,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alma = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alma.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), afterIntent);
                Toast.makeText(context, "Alarm is ON in "+
                        calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Opens a DatePickerDialog to choose the reminder date.
     * Transitions to {@link #choiceTime()} upon selection.
     */
    private void choiceDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        notiYear = year;
                        notiMonth = month;
                        notiDay = day;
                        calendar.set(Calendar.YEAR,notiYear);
                        calendar.set(Calendar.MONTH,notiMonth);
                        calendar.set(Calendar.DAY_OF_MONTH,notiDay);
                        stWhere = notiDay + "." + (notiMonth+1) + "." + notiYear + " in ";
                        choiceTime();
                    }
                }, cyear, cmonth, cday);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /**
     * Opens a TimePickerDialog to choose the reminder time.
     */
    private void choiceTime() {
        TimePickerDialog tpd = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        notiHour = i;
                        notiMinute = i1;
                        calendar.set(Calendar.HOUR,notiHour);
                        calendar.set(Calendar.MINUTE,notiMinute);
                        stWhere += notiHour + ":" + notiMinute;
                        tvWhere.setText(stWhere);
                    }
                }, chour, cminute, true);
        tpd.show();
    }

    /**
     * Initializes UI elements and state.
     */
    private void initElements() {
        bAddTimeAlarm=findViewById(R.id.bAddTimeAlarm);
        context = this;Intent TakeIt = getIntent();
        UserPassword = TakeIt.getStringExtra("password");
        tvWhere = findViewById(R.id.tvWhere);
        bWhere = findViewById(R.id.bWhere);
        etTimeAfter=findViewById(R.id.etTimeAfter);
    }

}

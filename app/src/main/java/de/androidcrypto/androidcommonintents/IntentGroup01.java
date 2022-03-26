package de.androidcrypto.androidcommonintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class IntentGroup01 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group01);

        btn01 = findViewById(R.id.btnG01B01);
        btn02 = findViewById(R.id.btnG01B02);
        btn03 = findViewById(R.id.btnG01B03);
        btn04 = findViewById(R.id.btnG01B04);
        btn05 = findViewById(R.id.btnG01B05);
        btn06 = findViewById(R.id.btnG01B06);
        btn07 = findViewById(R.id.btnG01B07);
        btn08 = findViewById(R.id.btnG01B08);

        tvG01 = findViewById(R.id.tvG01);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an alarm
                // https://developer.android.com/guide/components/intents-common
                // https://developer.android.com/reference/android/provider/AlarmClock#ACTION_SET_ALARM
                // alarm clock
                // create an alarm
                // <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
                /* <intent-filter>
        <action android:name="android.intent.action.SET_ALARM" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>

                 */
                //Calendar calendar;
                //calendar.s

                //Calendar alarm = new GregorianCalendar(TimeZone.getDefault());
                //alarm.setTimeInMillis(calendar.getTimeInMillis());
                //int hour = alarm.get(Calendar.HOUR_OF_DAY);
                //int minutes = alarm.get(Calendar.MINUTE);
                // |
                Context context = v.getContext();
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, label);
                alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, 19);
                alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, 35);
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Test alarm");
                //alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, Calendar.SATURDAY);

                if (alarmIntent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(alarmIntent);
                }

            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show alarms
                /*
                <activity ...>
    <intent-filter>
        <action android:name="android.intent.action.SHOW_ALARMS" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
                 */
                Context context = v.getContext();
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);

                if (alarmIntent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(alarmIntent);
                }
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set a timer
                // <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
                /*
<intent-filter>
        <action android:name="android.intent.action.SET_TIMER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
                 */

                Context context = v.getContext();
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_TIMER);
                alarmIntent.putExtra(AlarmClock.EXTRA_LENGTH, 10); // 10 seconds
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Test timer");
                //alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, Calendar.SATURDAY);

                if (alarmIntent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(alarmIntent);
                }
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show all timers
                /*
                <intent-filter>
        <action android:name="android.intent.action.SHOW_ALARMS" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
                 */
                Context context = v.getContext();
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_TIMERS);
                //alarmIntent.putExtra(AlarmClock.EXTRA_LENGTH, 10); // 10 seconds
                //alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Test timer");
                //alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, Calendar.SATURDAY);

                if (alarmIntent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(alarmIntent);
                }

            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set calendar event
                // https://developer.android.com/reference/android/content/Intent#ACTION_INSERT
                // https://stackoverflow.com/questions/14694931/insert-event-to-calendar-using-intent
                // some smartphones require Intent.ACTION_EDIT instead of ACTION_INSERT
                /*
<intent-filter>
        <action android:name="android.intent.action.INSERT" />
        <data android:mimeType="vnd.android.cursor.dir/event" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
                 */
                Context context = v.getContext();

                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2022, 2, 29, 7, 30);

                Calendar endTime = Calendar.getInstance();
                endTime.set(2022, 2, 31, 8, 30);

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Yoga")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");

                if (intent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(intent);
                }
            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
package com.example.denisprawira.ta.User.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.provider.AlarmClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denisprawira.ta.User.Helper.AlarmReceiver;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.ReminderDatabase;
import com.example.denisprawira.ta.User.Model.Reminder;
import com.example.denisprawira.ta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mHour, mMinute, mDay;

    private int reminderId;
    private String reminderIdEvent, reminderTitle,reminderTime,reminderMessage, reminderRepeatStatus,reminderRepeatType,reminderRepeatInterval, reminderActive,reminderEventDate,reminderEventTime;

    Reminder reminder;

    ReminderDatabase rb;

    ImageButton backReminder ;
    Button reminderSave;
    RelativeLayout RepeatNo,RepeatType;

    FloatingActionButton floatingActionButton;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private long mRepeatTime;


    TextView setTime,setRepeat,setRepeatType,setRepeatNo;
    Switch repeatSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        rb = new ReminderDatabase(this);
        reminder  = rb.getReminderByEvent(getIntent().getExtras().get("idEvent").toString());
        reminderId             = reminder.getReminderId();
        reminderIdEvent        = reminder.getReminderIdEvent();
        reminderTitle          = reminder.getReminderTitle();
        reminderTime           = reminder.getReminderTime();
        reminderMessage        = reminder.getReminderMessage();
        reminderRepeatStatus   = reminder.getReminderRepeatStatus();
        reminderRepeatType     = reminder.getReminderType();
        reminderRepeatInterval = reminder.getReminderInterval();
        reminderActive         = reminder.getReminderActive();
        reminderEventDate      = reminder.getReminderEventDate();
        reminderEventTime      = reminder.getReminderEventTime();

        setTime                = findViewById(R.id.set_time);
        setRepeat              = findViewById(R.id.set_repeat);
        repeatSwitch           = findViewById(R.id.repeat_switch);
        setRepeatType          = findViewById(R.id.set_repeat_type);
        setRepeatNo            = findViewById(R.id.set_repeat_no);
        backReminder           = findViewById(R.id.backReminder);
        floatingActionButton   = findViewById(R.id.floatingActionButton);
        RepeatNo               = findViewById(R.id.RepeatNo);
        RepeatType             = findViewById(R.id.RepeatType);
        reminderSave           = findViewById(R.id.reminderSave);

        floatingActionButton.setOnClickListener(this);
        reminderSave.setOnClickListener(this);
        backReminder.setOnClickListener(this);
        repeatSwitch.setOnClickListener(this);

        setTime.setText(reminder.getReminderTime());

        // Setup Repeat Switch
        if (reminder.getReminderRepeatStatus().equals("false")) {
            repeatSwitch.setChecked(false);
            setRepeat.setText("Tidak Berulang");
            setRepeatType.setText("Off");
            setRepeatNo.setText("Off");
            RepeatNo.setClickable(false);
            RepeatType.setClickable(false);
        } else if (reminder.getReminderRepeatStatus().equals("true")) {
            repeatSwitch.setChecked(true);
            setRepeat.setText("Berulang");
            setRepeatType.setText(reminderRepeatType);
            setRepeatNo.setText(reminderRepeatInterval);
            RepeatNo.setClickable(true);
            RepeatType.setClickable(true);
        }

        // Setup Reminder Active
        if (reminderActive.equals("true")) {
            floatingActionButton.setImageResource(R.drawable.ic_notifications_on_white_24dp);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReminderActivity.this, R.color.colorAccent)));
        } else if (reminderActive.equals("false")) {
            floatingActionButton.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReminderActivity.this, R.color.colorPrimary)));
        }




    }


    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void setTime(View v){
        final String[] options = new String[7];
        options[0] = "Saat Event Dimulai";
        options[1] = "30 Menit Sebelum Event";
        options[2] = "1 Jam Sebelum Event";
        options[3] = "2 Jam Sebelum Event";
        options[4] = "3 Jam Sebelum Event";
        options[5] = "4 Jam Sebelum Event";
        options[6] = "5 Jam Sebelum Event";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Waktu");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                setTime.setText(options[item]);
                reminderTime = options[item];
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void setRepeatType(View v){
        final String[] items = new String[2];
        items[0] = "Jam";
        items[1] = "Menit";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tipe Perulangan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                reminderRepeatType = items[item];
                setRepeatType.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void setRepeatNo(View v){
        if(reminderRepeatType.equals("Jam")){
            final String[] items = new String[3];
            items[0] = "Setiap 1 Jam";
            items[1] = "Setiap 2 Jam";
            items[2] = "Setiap 3 Jam";

            // Create List Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tipe Perulangan");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    setRepeatNo.setText(items[item]);
                    reminderRepeatInterval = items[item];
                    if (items[item].equals(items[0])) {
                        mRepeatTime = milHour;
                    } else if (items[item].equals(items[1])){
                        mRepeatTime = milHour*2;
                    } else if (items[item].equals(items[2])) {
                        mRepeatTime = milHour*3;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else if(reminderRepeatType.equals("Menit")){
            final String[] items = new String[3];
            items[0] = "Setiap 10 Menit";
            items[1] = "Setiap 20 Menit";
            items[2] = "Setiap 30 Menit";

            // Create List Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tipe Perulangan");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    setRepeatNo.setText(items[item]);
                    reminderRepeatInterval = items[item];
                    if (items[item].equals(items[0])) {
                        mRepeatTime = milMinute*10;
                    } else if (items[item].equals(items[1])){
                        mRepeatTime = milMinute*20;
                    } else if (items[item].equals(items[2])) {
                        mRepeatTime = milMinute*30;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            Toast.makeText(this, "Pilih Tipe Perulangan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateReminder(){
        reminder.setReminderTime(reminderTime);
        reminder.setReminderRepeatStatus(reminderRepeatStatus);
        reminder.setReminderType(reminderRepeatType);
        reminder.setReminderInterval(reminderRepeatInterval);
        reminder.setReminderActive(reminderActive);

        rb.updateReminder(reminder);
        new AlarmReceiver().cancelAlarm(getApplicationContext(), reminderId);
        // Set up calender for creating the notification

        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.MONTH, GlobalHelper.convertToIntegerMonth(reminderEventDate));
        calendar.set(Calendar.YEAR, GlobalHelper.convertToIntegerYear(reminderEventDate));
        calendar.set(Calendar.DAY_OF_MONTH, GlobalHelper.convertToIntegerDay(reminderEventDate));
        calendar.set(Calendar.HOUR_OF_DAY, GlobalHelper.convertToIntegerHour(reminderEventTime));
        calendar.set(Calendar.MINUTE, GlobalHelper.convertToIntegerMinute(reminderEventTime));
        calendar.set(Calendar.SECOND, 0);

        // Create a new notification
        if (reminderActive.equals("true")) {
            if (reminderRepeatStatus.equals("true")) {
                new AlarmReceiver().setRepeatAlarm(getApplicationContext(), calendar, reminderId, mRepeatTime);
            } else if (reminderRepeatStatus.equals("false")) {
                new AlarmReceiver().setAlarm(getApplicationContext(), calendar, reminderId);
            }
        }

        // Create toast to confirm update
        Toast.makeText(getApplicationContext(), "Edited",
                Toast.LENGTH_SHORT).show();

        onBackPressed();
    }


    @Override
    public void onClick(View v) {
        if(v==backReminder){
            onBackPressed();
        }else if(v==floatingActionButton){
            if(reminderActive.equals("true")){
                reminderActive="false";
                Toast.makeText(ReminderActivity.this, reminderActive, Toast.LENGTH_SHORT).show();
                floatingActionButton.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReminderActivity.this, R.color.colorPrimary)));
            }else{
                reminderActive ="true";
                Toast.makeText(ReminderActivity.this, reminderActive, Toast.LENGTH_SHORT).show();
                floatingActionButton.setImageResource(R.drawable.ic_notifications_on_white_24dp);
                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReminderActivity.this, R.color.colorAccent)));
            }
        }else if(v==reminderSave){
            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
            i.putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm");
            i.putExtra(AlarmClock.EXTRA_HOUR, 10);
            i.putExtra(AlarmClock.EXTRA_MINUTES, 30);
            startActivity(i);
            //updateReminder();
        }else if(v==repeatSwitch){
            if (reminderRepeatStatus.equals("false")) {
                reminderRepeatStatus="true";
                repeatSwitch.setChecked(true);
                setRepeat.setText("Berulang");
                setRepeatType.setText(reminderRepeatType);
                setRepeatNo.setText(reminderRepeatInterval);
                RepeatNo.setClickable(true);
                RepeatType.setClickable(true);
            } else if (reminderRepeatStatus.equals("true")) {
                reminderRepeatStatus="false";
                repeatSwitch.setChecked(false);
                setRepeat.setText("Tidak Berulang");
                setRepeatType.setText("Off");
                setRepeatNo.setText("Off");
                RepeatNo.setClickable(false);
                RepeatType.setClickable(false);
            }
        }
    }
}

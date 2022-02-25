/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.denisprawira.ta.User.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.denisprawira.ta.User.Model.Reminder;

import java.util.ArrayList;
import java.util.List;


public class ReminderDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CobaTable";

    // Table name
    private static final String TABLE_REMINDERS = "ReminderTable";

    // Table Columns names
    private static final String KEY_ID              = "id";
    private static final String KEY_ID_EVENT        = "reminder_id_event";
    private static final String KEY_TITLE           = "reminder_title";
    private static final String KEY_REMINDER_TIME   = "reminder_time";
    private static final String KEY_MESSAGE         = "reminder_message";
    private static final String KEY_REPEAT_STATUS   = "reminder_repeat";
    private static final String KEY_REPEAT_TYPE     = "reminder_type";
    private static final String KEY_REPEAT_INTERVAL = "reminder_interval";
    private static final String KEY_ACTIVE          = "reminder_active";
    private static final String KEY_EVENT_DATE      = "reminder_event_date";
    private static final String KEY_EVENT_TIME      = "reminder_event_time";

    public ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_EVENT + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_REMINDER_TIME + " TEXT,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_REPEAT_STATUS + " TEXT,"
                + KEY_REPEAT_TYPE+ " TEXT,"
                + KEY_REPEAT_INTERVAL + " TEXT,"
                + KEY_ACTIVE+ " TEXT,"
                + KEY_EVENT_DATE + " TEXT,"
                + KEY_EVENT_TIME + " TEXT"+")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        // Create tables again
        onCreate(db);
    }


    // Adding new Reminder
    public int addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID_EVENT, reminder.getReminderIdEvent());
        values.put(KEY_TITLE , reminder.getReminderTitle());
        values.put(KEY_REMINDER_TIME , reminder.getReminderTime());
        values.put(KEY_MESSAGE , reminder.getReminderMessage());
        values.put(KEY_REPEAT_STATUS , reminder.getReminderRepeatStatus());
        values.put(KEY_REPEAT_TYPE , reminder.getReminderType());
        values.put(KEY_REPEAT_INTERVAL , reminder.getReminderInterval());
        values.put(KEY_ACTIVE , reminder.getReminderActive());
        values.put(KEY_EVENT_DATE , reminder.getReminderEventDate());
        values.put(KEY_EVENT_TIME , reminder.getReminderEventTime());

        // Inserting Row
        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    // Getting single Reminder
    public Reminder getReminder(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_ID_EVENT,
                                KEY_TITLE,
                                KEY_REMINDER_TIME,
                                KEY_MESSAGE,
                                KEY_REPEAT_STATUS,
                                KEY_REPEAT_TYPE,
                                KEY_REPEAT_INTERVAL,
                                KEY_ACTIVE,
                                KEY_EVENT_DATE,
                                KEY_EVENT_TIME,
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                cursor.getString(9), cursor.getString(10));

        return reminder;
    }


    // new function here for search reminder by idEvent
    public Reminder getReminderByEvent(String idEvent){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_ID_EVENT,
                                KEY_TITLE,
                                KEY_REMINDER_TIME,
                                KEY_MESSAGE,
                                KEY_REPEAT_STATUS,
                                KEY_REPEAT_TYPE,
                                KEY_REPEAT_INTERVAL,
                                KEY_ACTIVE,
                                KEY_EVENT_DATE,
                                KEY_EVENT_TIME,
                        }, KEY_ID_EVENT + "=?",

                new String[] {String.valueOf(idEvent)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                cursor.getString(9), cursor.getString(10));

        return reminder;
    }



    // Getting all Reminders
    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setReminderId(Integer.parseInt(cursor.getString(0)));
                reminder.setReminderIdEvent(cursor.getString(1));
                reminder.setReminderTitle(cursor.getString(2));
                reminder.setReminderTime(cursor.getString(3));
                reminder.setReminderMessage(cursor.getString(4));
                reminder.setReminderRepeatStatus(cursor.getString(5));
                reminder.setReminderType(cursor.getString(6));
                reminder.setReminderInterval(cursor.getString(7));
                reminder.setReminderActive(cursor.getString(8));
                reminder.setReminderEventDate(cursor.getString(9));
                reminder.setReminderTime(cursor.getString(10));

                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Getting all Reminders
    public List<String> getAllItems(){
        List<String> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                String reminder = new String();
                reminder = cursor.getString(3);
                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Getting Reminders Count
    public int getRemindersCount(){
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single Reminder
    public int updateReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_EVENT, reminder.getReminderIdEvent());
        values.put(KEY_TITLE , reminder.getReminderTitle());
        values.put(KEY_REMINDER_TIME , reminder.getReminderTime());
        values.put(KEY_MESSAGE , reminder.getReminderMessage());
        values.put(KEY_REPEAT_STATUS , reminder.getReminderRepeatStatus());
        values.put(KEY_REPEAT_TYPE , reminder.getReminderType());
        values.put(KEY_REPEAT_INTERVAL , reminder.getReminderInterval());
        values.put(KEY_ACTIVE , reminder.getReminderActive());
        values.put(KEY_EVENT_DATE , reminder.getReminderEventDate());
        values.put(KEY_EVENT_TIME , reminder.getReminderEventTime());
        // Updating row
        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getReminderId())});
    }

    // Deleting single Reminder
    public void deleteReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getReminderId())});
        db.close();
    }
}

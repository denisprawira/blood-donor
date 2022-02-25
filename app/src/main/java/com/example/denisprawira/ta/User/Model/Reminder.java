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


package com.example.denisprawira.ta.User.Model;

// Reminder class
public class Reminder {
    private int reminderId;
    private String reminderIdEvent;
    private String reminderTitle;
    private String reminderTime;
    private String reminderMessage;
    private String reminderRepeatStatus;
    private String reminderType;
    private String reminderInterval;
    private String reminderActive;
    private String reminderEventDate;
    private String reminderEventTime;


    public Reminder() {
    }

    public Reminder(int reminderId, String reminderIdEvent, String reminderTitle, String reminderTime, String reminderMessage, String reminderRepeatStatus, String reminderType, String reminderInterval, String reminderActive, String reminderEventDate, String reminderEventTime) {
        this.reminderId = reminderId;
        this.reminderIdEvent = reminderIdEvent;
        this.reminderTitle = reminderTitle;
        this.reminderTime = reminderTime;
        this.reminderMessage = reminderMessage;
        this.reminderRepeatStatus = reminderRepeatStatus;
        this.reminderType = reminderType;
        this.reminderInterval = reminderInterval;
        this.reminderActive = reminderActive;
        this.reminderEventDate = reminderEventDate;
        this.reminderEventTime = reminderEventTime;
    }

    public Reminder(String reminderIdEvent, String reminderTitle, String reminderTime, String reminderMessage, String reminderRepeatStatus, String reminderType, String reminderInterval, String reminderActive, String reminderEventDate, String reminderEventTime) {
        this.reminderIdEvent = reminderIdEvent;
        this.reminderTitle = reminderTitle;
        this.reminderTime = reminderTime;
        this.reminderMessage = reminderMessage;
        this.reminderRepeatStatus = reminderRepeatStatus;
        this.reminderType = reminderType;
        this.reminderInterval = reminderInterval;
        this.reminderActive = reminderActive;
        this.reminderEventDate = reminderEventDate;
        this.reminderEventTime = reminderEventTime;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderIdEvent() {
        return reminderIdEvent;
    }

    public void setReminderIdEvent(String reminderIdEvent) {
        this.reminderIdEvent = reminderIdEvent;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public String getReminderRepeatStatus() {
        return reminderRepeatStatus;
    }

    public void setReminderRepeatStatus(String reminderRepeatStatus) {
        this.reminderRepeatStatus = reminderRepeatStatus;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public String getReminderInterval() {
        return reminderInterval;
    }

    public void setReminderInterval(String reminderInterval) {
        this.reminderInterval = reminderInterval;
    }

    public String getReminderActive() {
        return reminderActive;
    }

    public void setReminderActive(String reminderActive) {
        this.reminderActive = reminderActive;
    }

    public String getReminderEventDate() {
        return reminderEventDate;
    }

    public void setReminderEventDate(String reminderEventDate) {
        this.reminderEventDate = reminderEventDate;
    }

    public String getReminderEventTime() {
        return reminderEventTime;
    }

    public void setReminderEventTime(String reminderEventTime) {
        this.reminderEventTime = reminderEventTime;
    }

}


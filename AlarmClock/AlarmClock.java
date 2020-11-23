package com.Lab1;

public class AlarmClock extends Clock {

    protected int alarmHour;
    protected int alarmMin;
    protected String AlarmAM_PM;
    protected  boolean AlarmON;
    int alarmVolume = 0;

    int tempAlarmHour;
    int tempAlarmMin;
    String tempAlarmAM_PM;
    boolean SnoozePressed;

    public AlarmClock(int hour, int min, int sec, String AM_PM, int alarmHour, int alarmMin, String AlarmAM_PM) {
        super(hour,min, sec, AM_PM);

        this.alarmHour = alarmHour;
        this.alarmMin = alarmMin;
        this.AlarmAM_PM = AlarmAM_PM;

        this.tempAlarmHour = alarmHour;
        this.tempAlarmMin = alarmMin;
        this.tempAlarmAM_PM = AlarmAM_PM;
        this.SnoozePressed = false;
    }
    //setters for alarm to be set
    public void setAlarm(int alarmHour, int alarmMin, String AlarmAM_PM) {
        this.alarmHour = alarmHour;
        this.alarmMin = alarmMin;
        this.AlarmAM_PM = AlarmAM_PM;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    //shows alarm time
    public String showAlarmTime() {
        String hour = Integer.toString(this.alarmHour);
        String min = Integer.toString(this.alarmMin);
        if (this.alarmMin < 10) {
            min = "0"+min;
        }
        return "Alarm: " + hour+":"+min+" "+ this.AlarmAM_PM;
    }

    //checks if the time equals to alarm
    public boolean checkAlarm() {
        //if snooze was pressed then check the temp variables instead of alarm variable since that's the value that was changed
        if(this.SnoozePressed) {
            if((this.hour == this.tempAlarmHour) && (this.min == this.tempAlarmMin) && (this.AM_PM.equals(this.tempAlarmAM_PM))) {
                //System.out.println("Buzz Buzz Buzz");
                return true;
            }
        }
        else {
            if((this.hour == this.alarmHour) && (this.min == this.alarmMin) && (this.AM_PM.equals(this.AlarmAM_PM))) {
                //System.out.println("Buzz Buzz Buzz");
                return true;
            }
        }
        return false;

    }

    //function to snooze and add 9 mins to temp hour and mins thus not changing the actual alarm time
    public void snooze() {

        this.SnoozePressed = true;
        System.out.println("Snooze was pressed");
        this.tempAlarmMin+=9;
        boolean flag = false;

        // if min goes above 60 adds 1 to hour
        if (this.tempAlarmMin >=60) {
            this.tempAlarmHour++;
            this.tempAlarmMin = this.tempAlarmMin-60;
            flag = true;
        }
        //when hour equals 12 change am to pm
        if (this.tempAlarmHour == 12) {
            if (flag) {
                if (this.AlarmAM_PM.equals("AM")) {
                    this.AlarmAM_PM = "PM";
                } else {
                    this.AlarmAM_PM = "AM";
                }
            }
        }
        //when hour goes greater than 12 simply replaces to 1
        if (this.tempAlarmHour > 12) {
            this.tempAlarmHour=1;
        }
    }

    //When called shuts off the alarm and exits the program
    public void AlarmOFF() {
        this.AlarmON = false;
        System.out.println("The alarm was shut off");
    }

    public void AlarmON() {
        this.AlarmON = true;
        System.out.println("The alarm was shut ON");
    }

}

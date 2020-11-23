package com.Lab1;

public class AlarmClockRadio {

    Radio radio;
    AlarmClock alarmClock;

    public AlarmClockRadio (Radio radio, AlarmClock alarmClock) {
        this.radio = radio;
        this.alarmClock = alarmClock;

        System.out.println("Initial Time: " + alarmClock.showTime());
        System.out.println("The radio was turned on and is playing " + radio.getRadioStation());

    }

    public void setRadioStation(String station) {
        this.radio.station = station;
    }
    //getters method for station
    public String getRadioStation() {
        return this.radio.station;
    }

    //setters for current time
    public void setCurrentTime(int hour,int min,int sec,String AM_PM) {
        this.alarmClock.hour = hour;
        this.alarmClock.min = min;
        this.alarmClock.sec = sec;
        this.alarmClock.AM_PM = AM_PM;
    }

    //setters for alarmtime
    public void setAlarmTime(int AlarmHour,int AlarmMin,String AlarmAM_PM) {
        this.alarmClock.alarmHour = AlarmHour;
        this.alarmClock.alarmMin = AlarmMin;
        this.alarmClock.AlarmAM_PM = AlarmAM_PM;
    }

    public String getCurrentTime() {
        return this.alarmClock.showTime();
    }

    public String getAlarmTime() {
        return this.alarmClock.showAlarmTime();
    }

    public void turnAlarmOn() {
        this.alarmClock.AlarmON();
    }

    public void turnAlarmOFF() {
        this.alarmClock.AlarmOFF();
    }

    public void snooze() {
        this.alarmClock.snooze();
    }

    public boolean isAlarmOn() {
        return this.alarmClock.AlarmON;
    }

    public boolean checkAlarm() {

        if(this.alarmClock.checkAlarm()) {
            System.out.println("The radio is playing " + this.radio.station);

        }
        return this.alarmClock.checkAlarm();
    }

    public void tick() {
        this.alarmClock.tick();
    }

    public void turnRadioOn() {
        this.radio.RadioON();
    }

    public void turnRadioOff() {
        this.radio.RadioOFF();
    }

    public boolean isRadioOn() {
        return this.radio.radioOn;
    }





}

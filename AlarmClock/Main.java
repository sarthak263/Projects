package com.Lab1;

public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        Radio radio1 = new Radio("1080 AM");
        AlarmClock alarmClock  = new AlarmClock(8,0,0,"AM",8,5,"AM");
        AlarmClockRadio alarmRadio1 = new AlarmClockRadio(radio1,alarmClock);


        while(flag) {
            System.out.println(alarmRadio1.getCurrentTime());

            for (int i = 0; i < 60; i++) {
                if(alarmRadio1.checkAlarm()) {
                    alarmRadio1.snooze();
                    flag = false;
                }
                alarmRadio1.tick();
            }
        }//end while

        flag = true;
        while(flag) {
            System.out.println(alarmRadio1.getCurrentTime());

            for (int i = 0; i < 60; i++) {
                if(alarmRadio1.checkAlarm()) {
                    alarmRadio1.turnAlarmOFF();
                    flag = false;
                    break;
                }
                alarmRadio1.tick();
            }
        }//end while

    }//end main
}//end class

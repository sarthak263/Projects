package com.Lab1;

public class Clock {
    int hour;
    int min;
    int sec;
    String AM_PM;

    public Clock(int hour, int min, int sec, String AM_PM) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.AM_PM = AM_PM;
    }
    //setter for time to be set
    public void setTime(int hour,int min, int sec, String AM_PM) {
            this.hour = hour;
            this.min = min;
            this.sec = sec;
            this.AM_PM = AM_PM;
    }
    //shows the time
    public String showTime() {
        String hour = Integer.toString(this.hour);
        String min = Integer.toString(this.min);
        if (this.min < 10) {
            min = "0"+min;
        }
        return "Time: " + hour+":"+min+" "+ this.AM_PM;
    }
    public void tick() {
        this.sec++;
        // when seconds becomes 60 then adds 1 to min and changes sec to 0
        if (this.sec >= 60) {
            this.min++;
            this.sec = 0;
        }
        // adds one to hour when min gets greater than 60
        if(this.min >= 60) {
            this.hour++;
            this.min = 0;
        }
        //if this equals to 12:00:00 then changes am to pm or other way around
        if(this.hour == 12) {
            if(this.min == 0) {
                if(this.sec==0) {
                    if (this.AM_PM.equals("AM")) {
                        this.AM_PM = "PM";
                    } else {
                        this.AM_PM = "AM";
                    }
                }
            }
        }

        //when hour goes greater than 12 simply replaces to 1
        if(this.hour > 12) {
            this.hour = 1;
        }
    }


}

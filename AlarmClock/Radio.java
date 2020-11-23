package com.Lab1;

public class Radio {

    protected String station = "1060 AM";
    protected boolean radioOn;
    protected int radioVolume = 0;

    public Radio (String station) {
        this.station = station;
        this.radioOn = true;
    }
    public Radio() {
        this.radioOn = true;
    }

    //setters method for station
    public void setRadioStation(String station) {
        this.station = station;
    }

    //getters method for station
    public String getRadioStation() {
        return this.station;
    }

    //setters method for volume
    public void setRadioVolume(int volume) {
        this.radioVolume = volume;
    }
    public void RadioOFF() {
        this.radioOn = false;
        System.out.println("The Radio was shut off");
    }

    public void RadioON() {
        this.radioOn = true;
        System.out.println("The Radio was shut ON");
    }
}

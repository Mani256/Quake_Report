package com.android.quakereport.quakereport;

/**
 * Created by ganesh on 29-09-2016.
 */
public class Earthquake {
    private String magnitue;
    private String location;
    private String time;
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMagnitue() {
        return magnitue;
    }

    public void setMagnitue(String magnitue) {
        this.magnitue = magnitue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "magnitue='" + magnitue + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void setTime(String time) {
        this.time = time;
    }

}

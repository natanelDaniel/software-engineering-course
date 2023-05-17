package Ex1;

import java.util.Date;

public class Event {
    private Date date;
    private int duration;

    public Event(Date date, int duration) {
        this.date = date;
        this.duration = duration;
    }

    public Date getDateObject() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getHour(){
        return date.getHours();
    }

    public String toString(){
        int hour = date.getHours();
        int minutes = date.getMinutes();
        String str = "";
        if (hour < 10){
            str += "0";
        }
        str += hour + ":";
        if (minutes < 10){
            str += "0";
        }
        str += minutes + ", Duration: " + duration + " minutes,\n\t\t\t\t";
        return str;
    }

}

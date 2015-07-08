package dashboardActivities;

import java.util.Date;

public class Event {

    private String event_id;
    private String event_name;
    private String event_decs;
    private String event_date;
    private boolean user_attendence;


    public boolean isUser_attendence() {
        return user_attendence;
    }


    public void setUser_attendence(boolean user_attendence) {
        this.user_attendence = user_attendence;
    }


    public Event(String event_id, String event_name, String event_decs,String event_date){

        this.event_id = event_id;
        this.event_name = event_name;
        this.event_decs = event_decs;
        this.event_date = event_date;
    }


    public String getEvent_id() {
        return event_id;
    }


    public String getEvent_name() {
        return event_name;
    }


    public String getEvent_decs() {
        return event_decs;
    }


    public String getEvent_date() {
        return event_date;
    }

}

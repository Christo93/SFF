package dashboardActivities;

import java.util.ArrayList;

import android.content.Context;



public class EventStore {

    private ArrayList<Event> mEvents;
    private static EventStore sEventStore;
    private Context mAppContext;

    private EventStore(Context appContext) {
        mAppContext = appContext;
        mEvents = new ArrayList<Event>();
    }

    public void addEvent(Event c) {
        mEvents.add(c);
    }

    public static EventStore get(Context c) {
        if (sEventStore == null) {
            sEventStore = new EventStore(c.getApplicationContext());
        }
        return sEventStore;
    }

    public ArrayList<Event> getEvents() {
        return mEvents;
    }

    public Event getEvent(String id) {
        for (Event e : mEvents) {
            if (e.getEvent_id().equals(id))
                return e;
        }
        return null;
    }

}

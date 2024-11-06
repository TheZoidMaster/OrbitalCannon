package zoid.orbital_cannon.util;

import java.util.ArrayList;

public class EventScheduler {

    private static ArrayList<Event> events = new ArrayList<>();

    private static class Event {
        public Runnable runnable;
        public long timestamp;
        public String name;

        public Event(Runnable runnable, long timestamp, String name) {
            this.runnable = runnable;
            this.timestamp = timestamp;
            this.name = name;
        }
    }

    public static void schedule(Runnable runnable, long milliseconds, String name) {
        long currentTimestamp = System.currentTimeMillis();
        Event event = new Event(runnable, currentTimestamp + milliseconds, name);
        events.add(event);
    }

    public static void unschedule(String name) {
        ArrayList<Event> eventsToRemove = new ArrayList<>();
        for (Event event : events) {
            if (event.name.equals(name)) {
                eventsToRemove.add(event);
            }
        }
        events.removeAll(eventsToRemove);
    }

    public static void update() {
        long currentTimestamp = System.currentTimeMillis();
        ArrayList<Event> eventsToRemove = new ArrayList<>();
        for (Event event : events) {
            if (event.timestamp <= currentTimestamp) {
                event.runnable.run();
                eventsToRemove.add(event);
            }
        }
        events.removeAll(eventsToRemove);
    }
}

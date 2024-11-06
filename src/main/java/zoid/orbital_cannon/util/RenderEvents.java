package zoid.orbital_cannon.util;

import java.util.ArrayList;

public class RenderEvents {
    public static ArrayList<RenderEvent> events = new ArrayList<>();
    public static long lastUpdate = 0;

    public static void addEvent(RenderEvent event) {
        events.add(event);
    }

    public static void removeEvent(String id) {
        ArrayList<RenderEvent> eventsToRemove = new ArrayList<>();
        for (RenderEvent event : events) {
            if (event.id.equals(id)) {
                eventsToRemove.add(event);
            }
        }
        events.removeAll(eventsToRemove);
    }

    public static void update() {
        long currentTimestamp = System.currentTimeMillis();
        double delta = (currentTimestamp - lastUpdate) / 1000.0;
        for (RenderEvent event : events) {
            event.runnable.run(delta);
        }
        lastUpdate = System.currentTimeMillis();
    }

    public class RenderEvent {
        public String id;
        public DeltaRunnable runnable;

        public RenderEvent(String id, DeltaRunnable runnable) {
            this.id = id;
            this.runnable = runnable;
        }
    }

    public interface DeltaRunnable {
        void run(double delta);
    }
}

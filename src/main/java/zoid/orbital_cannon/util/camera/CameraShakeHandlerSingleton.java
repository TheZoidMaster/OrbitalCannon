package zoid.orbital_cannon.util.camera;

import java.util.ArrayList;

public class CameraShakeHandlerSingleton {
    public float avgX;
    public float avgY;
    private ArrayList<CameraShakeEvent> events = new ArrayList<>();

    private static CameraShakeHandlerSingleton instance = null;

    public static CameraShakeHandlerSingleton getInstance() {
        return instance;
    }

    public static void initialize() {
        if (instance != null) {
            throw new IllegalStateException("Only one instance of CameraShakeHandlerSingleton is allowed!");
        }

        instance = new CameraShakeHandlerSingleton();
        instance.avgX = 0;
        instance.avgY = 0;
    }

    public void addEvent(CameraShakeEvent event) {
        events.add(event);
    }

    public void removeEvent(String id) {
        ArrayList<CameraShakeEvent> eventsToRemove = new ArrayList<>();
        for (CameraShakeEvent event : events) {
            if (event.id.equals(id)) {
                eventsToRemove.add(event);
            }
        }
        events.removeAll(eventsToRemove);
    }

    public void update(double delta) {
        ArrayList<CameraShakeEvent> eventsToRemove = new ArrayList<>();
        avgX = 0;
        avgY = 0;
        for (CameraShakeEvent event : events) {
            event.setX(event.getX() + (float) (event.getXFalloff() * delta));
            event.setY(event.getY() + (float) (event.getYFalloff() * delta));
            if (event.getIntensity().x <= 0 && event.getIntensity().y <= 0) {
                eventsToRemove.add(event);
            }
            avgX += event.getX();
            avgY += event.getY();
        }
        events.removeAll(eventsToRemove);
        avgX /= events.size();
        avgY /= events.size();

        if (Float.isNaN(avgX) || Float.isInfinite(avgX)) {
            avgX = 0;
        }
        if (Float.isNaN(avgY) || Float.isInfinite(avgY)) {
            avgY = 0;
        }
    }
}

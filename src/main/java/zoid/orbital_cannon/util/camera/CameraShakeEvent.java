package zoid.orbital_cannon.util.camera;

import net.minecraft.util.math.Vec2f;

public class CameraShakeEvent {
    private float x;
    private float xFalloff;
    private float y;
    private float yFalloff;
    public String id;

    public CameraShakeEvent(float x, float xFalloff, float y, float yFalloff, String id) {
        this.x = x;
        this.xFalloff = xFalloff;
        this.y = y;
        this.yFalloff = yFalloff;
        this.id = id;
    }

    public CameraShakeEvent(float intensity, float intensityFalloff, String id) {
        this(intensity, intensityFalloff, intensity, intensityFalloff, id);
    }

    public float getX() {
        return x;
    }

    public float getXFalloff() {
        return xFalloff;
    }

    public float getY() {
        return y;
    }

    public float getYFalloff() {
        return yFalloff;
    }

    public Vec2f getIntensity() {
        return new Vec2f(x, y);
    }

    public Vec2f getIntensityFalloff() {
        return new Vec2f(xFalloff, yFalloff);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setXFalloff(float xFalloff) {
        this.xFalloff = xFalloff;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setYFalloff(float yFalloff) {
        this.yFalloff = yFalloff;
    }

    public void setIntensity(Vec2f intensity) {
        this.x = intensity.x;
        this.y = intensity.y;
    }

    public void setIntensityFalloff(Vec2f intensityFalloff) {
        this.xFalloff = intensityFalloff.x;
        this.yFalloff = intensityFalloff.y;
    }
}

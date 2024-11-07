package zoid.orbital_cannon.mixin;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Camera;
import zoid.orbital_cannon.util.RenderEvents;
import zoid.orbital_cannon.util.camera.CameraShakeHandlerSingleton;

@Debug(export = true)
@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract void moveBy(float x, float y, float z);

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V", shift = At.Shift.AFTER))
    public void update(CallbackInfo ci) {
        RenderEvents.update();

        float x = CameraShakeHandlerSingleton.getInstance().avgX * ((float) Math.random() * 2 - 1);
        float y = CameraShakeHandlerSingleton.getInstance().avgY * ((float) Math.random() * 2 - 1);

        if (!Float.isNaN(x)) {
            moveBy(0, 0, x);
        }
        if (!Float.isNaN(y)) {
            moveBy(0, y, 0);
        }

    }
}
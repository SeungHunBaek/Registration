package com.round1usa.registration.kids.camera;

import android.hardware.camera2.CameraDevice;
import android.support.annotation.NonNull;

public class NullStateCallback extends CameraDevice.StateCallback {

    @Override
    public void onOpened(@NonNull CameraDevice camera) {
        // Camera will be open. Then close.
        camera.close();
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {

    }

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {

    }
}

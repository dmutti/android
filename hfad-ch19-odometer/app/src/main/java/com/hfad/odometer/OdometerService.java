package com.hfad.odometer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.security.SecureRandom;

public class OdometerService extends Service {

    private final OdometerBinder binder = new OdometerBinder();
    private final SecureRandom random = new SecureRandom();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class OdometerBinder extends Binder {
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    public double getDistance() {
        return random.nextDouble();
    }
}

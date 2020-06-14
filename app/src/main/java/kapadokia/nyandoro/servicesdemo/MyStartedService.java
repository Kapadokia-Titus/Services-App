package kapadokia.nyandoro.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyStartedService extends Service {

    //constant
    private static final String TAG= MyStartedService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        // Performing Task [should be short duration tasks not to block the UI ]

        int sleepTime =intent.getIntExtra("sleepTime", 1); 

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return START_STICKY;


        /**
         * return START_STICKY - it will restart the service but the intent delivered to this service
         * will become null
         *
         * return START_REDELIVER_INTENT - the service will be started and the intent delivered to this method
         * will not be null;
         * return START_NOT_STICKY - the service when destroyed will not be restarted and the intent will
         * remain null
         */



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}

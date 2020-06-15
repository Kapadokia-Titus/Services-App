package kapadokia.nyandoro.servicesdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    private static final String TAG  = MyIntentService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public MyIntentService() {
        super("my worker thread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: Thread name" +Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: Thread name"+ Thread.currentThread().getName());
        int sleepTime = intent.getIntExtra("sleepTime", 1);
        int ctr= 1;

        // dummy long operation
        while (ctr<= sleepTime){
            Log.i(TAG, "onHandleIntent: Counter is now" + ctr);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ctr++;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: Thread name"+ Thread.currentThread().getName());
    }
}

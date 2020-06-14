package kapadokia.nyandoro.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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

        new MyAsyncTask().execute(sleepTime);

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

    class MyAsyncTask extends AsyncTask<Integer, String,Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: on Thread "+Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Integer... voids) {
            Log.i(TAG, "doInBackground:on Thread  "+Thread.currentThread().getName());

            int sleepTime = voids[0];
            int ctr= 1;

            // dummy long operation
            while (ctr<= sleepTime){
                publishProgress("Counter is now" + ctr);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ctr++;
            }

            return null;


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Toast.makeText(MyStartedService.this, values[0], Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Counter value"+ values[0]+"onProgressUpdate:on Thread  "+Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stopSelf(); // automatically stops the service within the class itself
            Log.i(TAG, "onPostExecute:on Thread "+Thread.currentThread().getName());

        }
    }
}

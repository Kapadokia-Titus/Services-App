package kapadokia.nyandoro.servicesdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView  service_tv, intent_tv;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        service_tv = findViewById(R.id.started_service_tv);
        intent_tv = findViewById(R.id.intent_service_tv);
    }

    public void startedService(View view) {

        Intent intent = new Intent(MainActivity.this, MyStartedService.class);

        // pass a command to the intent;
        intent.putExtra("sleepTime", 10);
        startService(intent);
    }

    public void stopStartedService(View view) {

        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        stopService(intent);
    }


    public void startIntentService(View view) {

    /**
     * we want to create a background intent service.  this will run in the worker thread therefore no conflict
     * with the ui thread process.
     *
     * However there is no way to get feedback directly from the service, thus we use the ResultReceiver class.
     *
     * first,  we create an instance of the class and pass it as a parcelable object to the service class through the intent.
     * in the putExtra we give it a unique String that will be accesses by the getIntent method in the MyIntentService.class
     */


        ResultReceiver resultReceiver = new ResultReceiver(null);

        Intent intent = new Intent(getApplicationContext(), IntentService.class);
        intent.putExtra("sleepTime", 1);
        intent.putExtra("result", resultReceiver);
        startService(intent);

    }

    private  class  MyResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            /**
             * this class is responsible for getting the bundled processed information.
             * The result is first bundled before being passed to the resultReceiver
             * the resultReceiver then sends it by calling  resultReceiver.send() method, in the send method,
             * we enter two parameters, the resultCode and the bundle.
             *
             * the Handler then enables us to communicate with the UI thread. it enables us to post the
             * data back to the UI thread.
             */

            if (resultCode ==  18 && resultData !=null){

                final String result = resultData.getString("resultIntentService");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        intent_tv.setText(result);
                    }
                });
            }
        }
    }
}

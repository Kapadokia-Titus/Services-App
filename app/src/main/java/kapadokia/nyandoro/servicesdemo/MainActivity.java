package kapadokia.nyandoro.servicesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}

package bit.hawkhje1.musicschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class StartScreenActivity extends AppCompatActivity {

    private static int SPLASH_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // create handler
        Handler handler = new Handler();

        // create handler for splashscreen to sleep
        SplashScreenSleepHandler splashScreenSleepHandler = new SplashScreenSleepHandler();

        // set delay for splash screen
        handler.postDelayed(splashScreenSleepHandler, SPLASH_DELAY);

    }

    class SplashScreenSleepHandler implements Runnable
    {
        @Override
        public void run() {

            // create an instance for start screen activity
            Intent enrolmentActivity = new Intent(StartScreenActivity.this, MainActivity.class);

            // start activity
            startActivity(enrolmentActivity);

        }
    }

}

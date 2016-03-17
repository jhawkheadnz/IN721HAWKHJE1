package bit.hawkhje1.datapassingtoactivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String APP_INFO = "AppInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // show when onCreate is being called
        Log.d(APP_INFO, "MainActivity (onCreate) Loaded");

        // setup button for loading settings actvitiy
        btnLoadSettingsActivitySetup();

        // get username textview
        TextView txtUsername = (TextView) findViewById(R.id.txtUsername);

        // set username information
        txtUsername.setText(loadUsernameText());

    }

    // set the text of the username TextView based on whether
    // there is a username present or not.
    // If the username is set, print username, however if it is not set
    // then set the placeholder text showing the username is not set
    private String loadUsernameText() {

        String username = "";

        // ============== Getting Information From SettingsActivity ================

        // get the intent that launched this activity
        Intent startingIntent = getIntent();
        Log.d(APP_INFO, startingIntent.toString());

        // check if the intent has the extra with the username id
        if(startingIntent.hasExtra(SettingsActivity.USERNAME_ID)) {
            // display username on screen
            username = startingIntent.getStringExtra(SettingsActivity.USERNAME_ID);
        }else{
            // display username empty message
            username = getResources().getString(R.string.txtUsernameEmpty);
        }

        return username;
    }

    // setup buttons and button click handlers
    private void btnLoadSettingsActivitySetup() {
        // create instance of button click handler for settings activity
        BtnLoadSettingsHandler btnLoadSettingsHandler = new BtnLoadSettingsHandler();

        // get button from activity
        Button btnLoadSettings = (Button)findViewById(R.id.btnGoToSettings);

        // set on click listener for btnLoadSettings
        btnLoadSettings.setOnClickListener(btnLoadSettingsHandler);
    }

    // button click handler for loading settings activity
    class BtnLoadSettingsHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            // create intent to load settings activity
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);

            // load settings activity
            startActivity(settingsIntent);

        }
    }
}

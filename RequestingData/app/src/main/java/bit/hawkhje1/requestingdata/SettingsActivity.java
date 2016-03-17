package bit.hawkhje1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // create an instance of an intent class
        Intent currentIntent = new Intent();

        // get the settings content text color
        int settingsContentTextColor = getContentTextColor();

        // set the color content using putExtra
        currentIntent.putExtra(MainActivity.COLOR_REQUEST_ID, settingsContentTextColor);

        // set result to OK and pass in the intent
        setResult(Activity.RESULT_OK, currentIntent);

        // kill this activity
        finish();
    }

    // get the color of the content text
    private int getContentTextColor()
    {
        // get the settings content textView object
        TextView txtSettingsContent = (TextView)findViewById(R.id.txtSettingsContent);

        // return the color value of the textView object
        return txtSettingsContent.getCurrentTextColor();
    }
}

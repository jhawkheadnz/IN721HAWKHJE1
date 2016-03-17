package bit.hawkhje1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // color request code
    public static int COLOR_REQUEST_CODE = 0;

    // color request id
    public static String COLOR_REQUEST_ID = "RequestedColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btnChangeTextColor Setup
        Button btnChangeTextColor = (Button)findViewById(R.id.btnChangeTextColor);
        BtnChangeTextColorHandler btnChangeTextColorHandler = new BtnChangeTextColorHandler();
        btnChangeTextColor.setOnClickListener(btnChangeTextColorHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COLOR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            // get the text object we want to change the color of
            TextView txtMainContent = (TextView)findViewById(R.id.txtMainContent);

            // get the current color of the textView (for default)
            int currentColor = txtMainContent.getCurrentTextColor();

            // get color from result
            int resultColor = data.getIntExtra(COLOR_REQUEST_ID, currentColor);

            // set the color from result
            txtMainContent.setTextColor(resultColor);
        }

    }

    // Change Text Color Button Click Handler
    class BtnChangeTextColorHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            // create intent for loading settings activity
            Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);

            // start activity requesting result
            startActivityForResult(settingsActivityIntent, COLOR_REQUEST_CODE);

        }
    }

}

package bit.hawkhje1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int COLOR_REQUEST_CODE = 0;
    public static String COLOR_REQUEST_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

    class BtnChangeTextColor implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            // create intent for loading settings activity
            Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);

            startActivityForResult(settingsActivityIntent, COLOR_REQUEST_CODE);

        }
    }

}

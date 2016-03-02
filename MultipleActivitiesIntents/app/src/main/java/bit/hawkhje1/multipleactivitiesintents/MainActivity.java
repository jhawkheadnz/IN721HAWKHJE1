package bit.hawkhje1.multipleactivitiesintents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get textview from layout to output current activity name
        TextView txtView = (TextView)findViewById(R.id.txtActivityName);
        String currentActivityText = getResources().getString(R.string.txt_activity);
        txtView.setText(String.format(currentActivityText, Globals.ACTIVITY1));

        // set button click for button to open new activity
        Button btnChangeActivity = (Button)findViewById(R.id.btnActivityChange);
        OpenActivityHandler openActivityHandler = new OpenActivityHandler();
        btnChangeActivity.setOnClickListener(openActivityHandler);
    }

    public class OpenActivityHandler implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            Intent newActivity = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(newActivity);

        }
    }

}

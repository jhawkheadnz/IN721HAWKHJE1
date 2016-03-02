package bit.hawkhje1.multipleactivitiesintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get textview from layout to output current activity name
        TextView txtView = (TextView)findViewById(R.id.txtActivityName);
        String currentActivityText = getResources().getString(R.string.txt_activity);
        txtView.setText(String.format(currentActivityText, Globals.ACTIVITY2));

        // set button click for button to open new activity
        Button btnChangeActivity = (Button)findViewById(R.id.btnActivityChange);
        OpenWebsiteHandler openWebsiteHandler = new OpenWebsiteHandler();
        btnChangeActivity.setOnClickListener(openWebsiteHandler);

    }

    private class OpenWebsiteHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent newActivity = new Intent(Main2Activity.this, Main3Activity.class);
            startActivity(newActivity);
        }
    }
}

package bit.hawkhje1.multipleactivitiesintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtView = (TextView)findViewById(R.id.txtActivityName);
        String currentActivityText = getResources().getString(R.string.txt_activity);
        txtView.setText(String.format(currentActivityText, Globals.ACTIVITY3));

        // set button click for button to open new activity
        Button btnChangeActivity = (Button)findViewById(R.id.btnActivityChange);
        OpenActivityHandler openActivityHandler = new OpenActivityHandler();
        btnChangeActivity.setOnClickListener(openActivityHandler);
    }

    private class OpenActivityHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Uri goSeeMickey = Uri.parse("http://www.disney.com");
            Intent newActivity = new Intent(Intent.ACTION_VIEW, goSeeMickey);
            startActivity(newActivity);
        }
    }
}

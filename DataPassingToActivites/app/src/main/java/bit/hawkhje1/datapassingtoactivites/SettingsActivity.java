package bit.hawkhje1.datapassingtoactivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private static final String APP_INFO = "AppInfo";
    private static int MINIUMUM_USERNAME_LENGTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d(APP_INFO, getIntent().toString());

        BtnReturnToMainHandler btnReturnToMainHandler = new BtnReturnToMainHandler();
        Button btnReturnToMain = (Button)findViewById(R.id.btnReturnToMain);
        btnReturnToMain.setOnClickListener(btnReturnToMainHandler);
    }

    public static String USERNAME_ID = "username";

    class BtnReturnToMainHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            Log.d(APP_INFO, getIntent().toString());

            // get the EditText from the current activity
            EditText txtUsername = (EditText)findViewById(R.id.txtInputUsername);

            if(txtUsername.length() < MINIUMUM_USERNAME_LENGTH) {

                // notify the user that the username is too short
                Toast.makeText(
                        SettingsActivity.this,
                        "The username you entered is too short.\nIt must be at least 5 characters long.\nYou've used " + txtUsername.length() + " characters",
                        Toast.LENGTH_LONG).show();

                Log.d(APP_INFO, "User characters too short (" + txtUsername.length() + ")");

            } else {
                // create intent to load main activity
                Intent mainActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);

                // use put extra for username
                mainActivityIntent.putExtra(USERNAME_ID, txtUsername.getText().toString());

                // start mainActivity
                startActivity(mainActivityIntent);

                Log.d(APP_INFO, "Loading MainActivity from SettingsActivity");

                // kill this activity
                finish();
            }

        }
    }

}

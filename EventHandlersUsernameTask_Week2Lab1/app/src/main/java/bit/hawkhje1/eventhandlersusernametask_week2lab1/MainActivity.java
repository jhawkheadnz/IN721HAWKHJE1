package bit.hawkhje1.eventhandlersusernametask_week2lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int MIN_USERNAME_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = (EditText)findViewById(R.id.tbxUsername);

        CheckUsernameLengthHandler checkUsernameLengthHandler = new CheckUsernameLengthHandler();
        username.setOnKeyListener(checkUsernameLengthHandler);

    }

    class CheckUsernameLengthHandler implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                EditText tbxUsername = (EditText)v;

                String usernameText = tbxUsername.getText().toString();

                if(usernameText.length() < MIN_USERNAME_LENGTH)
                {
                    Toast.makeText(MainActivity.this, String.format("Username must be %s characters, %s", MIN_USERNAME_LENGTH, usernameText), Toast.LENGTH_SHORT).show();
                    return false;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Thank You " + usernameText, Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            return false;
        }
    }

}

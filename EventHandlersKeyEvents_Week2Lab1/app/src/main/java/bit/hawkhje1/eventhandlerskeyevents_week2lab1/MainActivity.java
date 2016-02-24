package bit.hawkhje1.eventhandlerskeyevents_week2lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText tbxEditText = (EditText)findViewById(R.id.tbxEditText);

        KeyInputHandler keyInputHandler = new KeyInputHandler();

        tbxEditText.setOnKeyListener(keyInputHandler);
    }

    class KeyInputHandler implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(event.getAction() == KeyEvent.ACTION_DOWN) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_AT) {
                    Toast.makeText(MainActivity.this, "Don't type @", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }

            return false;
        }
    }
}

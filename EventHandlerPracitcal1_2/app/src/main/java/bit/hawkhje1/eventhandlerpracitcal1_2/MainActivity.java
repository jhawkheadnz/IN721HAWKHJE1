package bit.hawkhje1.eventhandlerpracitcal1_2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEventTester = (Button)findViewById(R.id.btnEventTester);

        NormalButtonClickHandler normalButtonClickHandler = new NormalButtonClickHandler();
        LongButtonClickHandler longButtonClickHandler = new LongButtonClickHandler();

        btnEventTester.setOnClickListener(normalButtonClickHandler);
        btnEventTester.setOnLongClickListener(longButtonClickHandler);

    }

    class NormalButtonClickHandler implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(MainActivity.this, "Normal Click", Toast.LENGTH_SHORT).show();
        }
    }

    class LongButtonClickHandler implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(MainActivity.this, "Long Click", Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    class EditTextKeyHandler implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(event.getAction() == KeyEvent.KEYCODE_AT)
            {
                Toast.makeText(MainActivity.this, "Don't type '@'", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        }
    }

}

package bit.hawkhje1.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPracticeWords = (Button)findViewById(R.id.btnPracticeWords);
        BtnPracticeWordsHandler btnPracticeWordsHandler = new BtnPracticeWordsHandler();
        btnPracticeWords.setOnClickListener(btnPracticeWordsHandler);

    }

    class BtnPracticeWordsHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent practiceIntent = new Intent(MainActivity.this, PracticeActivity.class);
            startActivity(practiceIntent);
        }
    }

}

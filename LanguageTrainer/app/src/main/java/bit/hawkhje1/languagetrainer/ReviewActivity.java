package bit.hawkhje1.languagetrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.ParcelableTest;

public class ReviewActivity extends AppCompatActivity {

    List<AnswerWord> userAnswers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();

        userAnswers = intent.getParcelableArrayListExtra(PracticeActivity.RESULT_LIST_ID);

        ArrayAdapter<AnswerWord> answerWordArrayAdapter = new ArrayAdapter<AnswerWord>(ReviewActivity.this,android.R.layout.simple_list_item_1, userAnswers);

        ListView resutlsList = (ListView)findViewById(R.id.lvResults);
        resutlsList.setAdapter(answerWordArrayAdapter);

        /*if(userAnswers != null) {
            Toast.makeText(ReviewActivity.this, "Test is not null", Toast.LENGTH_SHORT).show();

            try{
                Log.d("JH_TEST", userAnswers.get(1).toString());
            }catch(Exception ex) {
                ex.printStackTrace();
                Log.d("JH_TEST", ex.getMessage());
            }

        } else
            Toast.makeText(ReviewActivity.this, "Test is still null", Toast.LENGTH_SHORT).show();*/

        // home button
        Button btnHome = (Button)findViewById(R.id.btnHome);
        BtnHomeHandler btnHomeHandler = new BtnHomeHandler();
        btnHome.setOnClickListener(btnHomeHandler);

    }

    private class BtnHomeHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(ReviewActivity.this, MainActivity.class);
            startActivity(homeIntent);
        }
    }
}

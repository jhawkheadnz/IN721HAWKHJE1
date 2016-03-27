package bit.hawkhje1.languagetrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Managers.ReviewManager;

public class ReviewActivity extends AppCompatActivity {

    private List<AnswerWord> userAnswers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // get intent
        Intent intent = getIntent();

        // get the list view
        ListView resultsList = (ListView)findViewById(R.id.lvResults);
        TextView txtResultScore = (TextView)findViewById(R.id.txtResultScore);
        Button btnHome = (Button)findViewById(R.id.btnHome);

        // get the answers from intent
        userAnswers = intent.getParcelableArrayListExtra(PracticeActivity.RESULT_LIST_ID);

        ReviewManager.LoadReviewManager(ReviewActivity.this, userAnswers, btnHome, txtResultScore, resultsList);

    }


}

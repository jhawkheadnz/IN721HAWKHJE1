package bit.hawkhje1.languagetrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.ResultsAdapter;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;

public class ReviewActivity extends AppCompatActivity {

    private List<AnswerWord> userAnswers = null;
    private ListView resultsList;
    private TextView txtResultScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // get intent
        Intent intent = getIntent();

        // get the list view
        resultsList = (ListView)findViewById(R.id.lvResults);
        txtResultScore = (TextView)findViewById(R.id.txtResultScore);

        Button btnHome = (Button)findViewById(R.id.btnHome);
        BtnHomeHandler btnHomeHandler = new BtnHomeHandler();
        btnHome.setOnClickListener(btnHomeHandler);

        // get the answers from intent
        userAnswers = intent.getParcelableArrayListExtra(PracticeActivity.QUIZ_RESULTS);

        LoadScore();
        GenerateResultsList();

    }

    /**
     * Send the user back to the home screen
     */
    private class BtnHomeHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(ReviewActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    private void LoadScore() {

        // get the amount of questions asked
        int questionsCount = userAnswers.size();
        int questionsCorrect = 0;
        float percentage;

        // get the amount of correct answers
        for(AnswerWord answerWord : userAnswers){

            Word word = answerWord.getWord();
            Article article = answerWord.getArticleAnswer();

            if(word.containsArticle(article)) {
                questionsCorrect++;
            }

        }

        //get the percentage
        percentage =(((float)questionsCorrect / (float)questionsCount) * 100);
        txtResultScore.setText(String.format("%s/%s - %.2f%%", Integer.toString(questionsCorrect), Integer.toString(questionsCount), percentage));
    }
    private void GenerateResultsList(){

        // create an adapter for List view to show results to user
        ResultsAdapter resultsAdapter = new ResultsAdapter(ReviewActivity.this, android.R.layout.simple_list_item_1, userAnswers);

        // set the list view's adapter
        this.resultsList.setAdapter(resultsAdapter);

    }
}

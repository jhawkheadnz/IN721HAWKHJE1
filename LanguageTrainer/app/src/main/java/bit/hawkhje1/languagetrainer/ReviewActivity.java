package bit.hawkhje1.languagetrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;

public class ReviewActivity extends AppCompatActivity {

    private List<AnswerWord> userAnswers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // get intent
        Intent intent = getIntent();

        // get the list view
        ListView resutlsList = (ListView)findViewById(R.id.lvResults);
        TextView txtResultScore = (TextView)findViewById(R.id.txtResultScore);

        // get the answers from intent
        userAnswers = intent.getParcelableArrayListExtra(PracticeActivity.RESULT_LIST_ID);

        int questionsCount = userAnswers.size();

        int questionsCorrect = 0;
        float percentage;

        for(AnswerWord answerWord : userAnswers){

            Word word = answerWord.getWord();
            Article article = answerWord.getArticleAnswer();

            if(word.containsArticle(article)) {
                questionsCorrect++;
            }

        }

        percentage =(((float)questionsCorrect / (float)questionsCount) * 100);

        txtResultScore.setText(String.format("%s/%s - %.2f%%", Integer.toString(questionsCorrect), Integer.toString(questionsCount), percentage));

        // create an array adapter for List view to show results to user
        ArrayAdapter<AnswerWord> answerWordArrayAdapter = new ArrayAdapter<>(ReviewActivity.this,android.R.layout.simple_list_item_1, userAnswers);

        // set the list view's adapter
        resutlsList.setAdapter(answerWordArrayAdapter);

        // home button
        Button btnHome = (Button)findViewById(R.id.btnHome);
        BtnHomeHandler btnHomeHandler = new BtnHomeHandler();
        btnHome.setOnClickListener(btnHomeHandler);

    }

    /**
     * Send the user back to the home screen
     */
    private class BtnHomeHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(ReviewActivity.this, MainActivity.class);
            startActivity(homeIntent);
        }
    }
}

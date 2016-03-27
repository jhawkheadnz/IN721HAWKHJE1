package bit.hawkhje1.languagetrainer.Managers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.ResultsAdapter;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.MainActivity;

public class ReviewManager {

    /* Controls */
    private Button btnHome;
    private TextView txtResultScore;
    private ListView resultsList;

    /* Classes */
    List<AnswerWord> userAnswers;

    /* Context */
    Context context;

    private ReviewManager(Context context, List<AnswerWord> userAnswers, Button btnHome, TextView txtResultScore, ListView resultsList) {

        this.context = context;
        this.userAnswers = userAnswers;

        this.btnHome = btnHome;
        this.txtResultScore = txtResultScore;
        this.resultsList = resultsList;

        BtnHomeHandler btnHomeHandler = new BtnHomeHandler();
        this.btnHome.setOnClickListener(btnHomeHandler);

        LoadScore();
        GenerateResultsList();

    }

    public static ReviewManager LoadReviewManager(Context context, List<AnswerWord> userAnswers, Button btnHome, TextView txtResultScore, ListView resultsList) {

        return new ReviewManager(context, userAnswers, btnHome, txtResultScore, resultsList);

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
        ResultsAdapter resultsAdapter = new ResultsAdapter(context, android.R.layout.simple_list_item_1, userAnswers);

        // set the list view's adapter
        this.resultsList.setAdapter(resultsAdapter);

    }

    /**
     * Send the user back to the home screen
     */
    private class BtnHomeHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(context, MainActivity.class);
            context.startActivity(homeIntent);
        }
    }

}

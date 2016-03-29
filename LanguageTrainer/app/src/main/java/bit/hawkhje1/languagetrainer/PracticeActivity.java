package bit.hawkhje1.languagetrainer;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Fragments.QuitQuizDialogFragment;
import bit.hawkhje1.languagetrainer.Managers.Quiz;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.Managers.WordManager;

public class PracticeActivity extends AppCompatActivity {

    public static final String QUIZ_RESULTS = "results";
    private static final String QUIT_QUIZ_ID = "confirm_exit";

    private Quiz quiz;
    private QuitQuizDialogFragment quitQuizDialogFragment;

    private Button btnDie;
    private Button btnDas;
    private Button btnDer;
    private Button btnNext;
    private Button btnConfirm;

    private ImageView imgWordImage;
    private TextView txtResult;

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        resources = getResources();
        InitManagers();
        InitControls();

        quiz.shuffle();

        // display first question
        Word currentQuestion = quiz.getNextQuestion();
        UpdateQuestion(currentQuestion);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        // stop back button default behavior and ask user if they really want to quit
        quitQuizDialogFragment = new QuitQuizDialogFragment();
        quitQuizDialogFragment.show(fm, QUIT_QUIZ_ID);
    }

    //<editor-fold desc="Handler Classes">
    private class BtnDieOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v){
            Article selectedArticle = quiz.setArticle(Article.Die);
            SetArticleSelection(selectedArticle);
            btnConfirm.setVisibility(View.VISIBLE);
        }
    }
    private class BtnDerOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v){
            Article selectedArticle = quiz.setArticle(Article.Der);
            SetArticleSelection(selectedArticle);
            btnConfirm.setVisibility(View.VISIBLE);
        }
    }
    private class BtnDasOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            Article selectedArticle = quiz.setArticle(Article.Das);
            SetArticleSelection(selectedArticle);
            btnConfirm.setVisibility(View.VISIBLE);
        }
    }
    private class BtnConfirmOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){

            // hide the question buttons
            SetQuestionVisibility(View.INVISIBLE);

            // reset to default deselection color
            DeselectArticleButtons();

            // get the result from the quiz for the current question
            boolean result = quiz.getResult();

            // get the text based on the result
            String answerText = GetResultText(result);

            // get the color based on the result
            int resultTextColor = GetResultTextColor(result);

            // set the answer text, and the result color
            txtResult.setText(answerText);
            txtResult.setTextColor(resultTextColor);
            txtResult.setVisibility(View.VISIBLE);

            // add the result to the quiz
            AnswerWord resultData = quiz.getResultData();
            quiz.addResult(resultData);

            // display the next button
            btnNext.setVisibility(View.VISIBLE);

            // hide the confirm button
            btnConfirm.setVisibility(View.INVISIBLE);

            // check if the current question is the final question
            if(quiz.isFinalQuestion()){

                // get the final question text
                String btnNextCompletedText = resources.getString(R.string.btnNextCompletedText);

                // set the button text
                btnNext.setText(btnNextCompletedText);

            }

        }
    }
    private class BtnNextOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){

            if(!quiz.isFinalQuestion()) {
                txtResult.setVisibility(View.INVISIBLE);

                SetQuestionVisibility(View.VISIBLE);
                Word currentQuestion = quiz.getNextQuestion();
                UpdateQuestion(currentQuestion);

                btnNext.setVisibility(View.INVISIBLE);
            }else{
                Intent reviewIntent = new Intent(PracticeActivity.this, ReviewActivity.class);
                reviewIntent.putParcelableArrayListExtra(QUIZ_RESULTS, (ArrayList<? extends Parcelable>) quiz.getResults());
                startActivity(reviewIntent);
                finish();
            }

        }
    }
    //</editor-fold>

    //<editor-fold desc="GUI Control Methods">
    public void ReturnToMainScreen(){

        // dismiss quit dialog
        quitQuizDialogFragment.dismiss();

        // create intent for mainActivity
        Intent mainActivityIntent = new Intent(PracticeActivity.this, MainActivity.class);

        // start main activity
        startActivity(mainActivityIntent);

        // kill this activity
        finish();
    }
    private void UpdateQuestion(Word question){

        // get the image id
        int imageID = question.getImage();

        // draw word image
        imgWordImage.setImageResource(imageID);
    }
    private void SetArticleSelection(Article selectedArticle){

        // get the selection color from resources
        int selectionColor = resources.getColor(R.color.btnSelectedColor);

        // clear the button selection
        DeselectArticleButtons();

        // select which button to highlight based on the selected article
        switch (selectedArticle){
            case Das:
                btnDas.setBackgroundColor(selectionColor);
                break;
            case Der:
                btnDer.setBackgroundColor(selectionColor);
                break;
            case Die:
                btnDie.setBackgroundColor(selectionColor);
                break;
        }

    }

    // set the visibility of the question buttons
    private void SetQuestionVisibility(int visibility){

        btnDas.setVisibility(visibility);
        btnDer.setVisibility(visibility);
        btnDie.setVisibility(visibility);
    }

    // deselect/change background color of buttons to default deselection button color
    private void DeselectArticleButtons(){

        // get the deselection button color
        int deselectButtonColor = resources.getColor(R.color.btnDeselectedColor);

        // set the deslection button color
        btnDie.setBackgroundColor(deselectButtonColor);
        btnDas.setBackgroundColor(deselectButtonColor);
        btnDer.setBackgroundColor(deselectButtonColor);

    }
    private void InitManagers(){
        /* Word Manager */
        WordManager wordManager = new WordManager(R.xml.german_words);
        quiz = new Quiz(wordManager.getWords());
    }
    private void InitControls(){

        /* Controls */
        btnDie = (Button)findViewById(R.id.btnDie);
        btnDas = (Button)findViewById(R.id.btnDas);
        btnDer = (Button)findViewById(R.id.btnDer);
        btnNext = (Button)findViewById(R.id.btnNextQuestion);
        btnConfirm = (Button)findViewById(R.id.btnConfirm);
        imgWordImage = (ImageView)findViewById(R.id.imgWordImage);
        txtResult = (TextView)findViewById(R.id.txtResult);

        /* Handlers */
        BtnDieOnClickHandler btnDieOnClickHandler = new BtnDieOnClickHandler();
        BtnDasOnClickHandler btnDasOnClickHandler = new BtnDasOnClickHandler();
        BtnDerOnClickHandler btnDerOnClickHandler = new BtnDerOnClickHandler();
        BtnConfirmOnClickHandler btnConfirmOnClickHandler = new BtnConfirmOnClickHandler();
        BtnNextOnClickHandler btnNextOnClickHandler = new BtnNextOnClickHandler();

        /* set handlers */
        btnDie.setOnClickListener(btnDieOnClickHandler);
        btnDer.setOnClickListener(btnDerOnClickHandler);
        btnDas.setOnClickListener(btnDasOnClickHandler);
        btnConfirm.setOnClickListener(btnConfirmOnClickHandler);
        btnNext.setOnClickListener(btnNextOnClickHandler);

    }
    private String GetResultText(boolean result){
        int result_response = (result) ? R.string.txtAnswerCorrect : R.string.txtAnswerIncorrect;
        return resources.getString(result_response);
    }
    private int GetResultTextColor(boolean result){
        int result_response_color = (result) ? R.color.colorCorrect : R.color.colorIncorrect;
        return resources.getColor(result_response_color);
    }
    //</editor-fold>
}
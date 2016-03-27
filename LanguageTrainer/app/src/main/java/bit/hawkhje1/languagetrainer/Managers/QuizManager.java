package bit.hawkhje1.languagetrainer.Managers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.Quiz;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.PracticeActivity;
import bit.hawkhje1.languagetrainer.R;
import bit.hawkhje1.languagetrainer.ReviewActivity;

/**
 * Quiz Manager
 * Controls UI, and Quiz Stuff
 */
public class QuizManager {

    private Quiz quiz;
    private Word currentQuestion;
    private Article selectedArticle;

    private ImageView imgWordImage;
    private TextView txtResult;

    private Button btnConfirm;
    private Button btnNext;
    private Button btnDie;
    private Button btnDer;
    private Button btnDas;

    private Resources resources;
    private Context context;

    /**
     * Quiz Manager, manages the quiz, and UI
     * @param context Context being used
     * @param words List of words to use
     * @param imgWordImage ImageView for Gender Word Image
     * @param btnConfirm Confirm Button
     * @param btnNext Next Button
     * @param btnDas Das Button
     * @param btnDer Der Button
     * @param btnDie Die Button
     * @param txtResult Result TextView
     */
    private QuizManager(Context context, List<Word> words, ImageView imgWordImage, Button btnConfirm, Button btnNext, Button btnDas, Button btnDer, Button btnDie, TextView txtResult) {

        this.context = context;
        this.resources = this.context.getResources();

        // setup GUI
        this.imgWordImage = imgWordImage;
        this.btnNext = btnNext;
        this.btnConfirm = btnConfirm;
        this.btnDas = btnDas;
        this.btnDer = btnDer;
        this.btnDie = btnDie;
        this.txtResult = txtResult;

        // setup handlers
        BtnDieOnClickHandler btnDieOnClickHandler = new BtnDieOnClickHandler();
        BtnDerOnClickHandler btnDerOnClickHandler = new BtnDerOnClickHandler();
        BtnDasOnClickHandler btnDasOnClickHandler = new BtnDasOnClickHandler();
        BtnConfirmOnClickHandler btnConfirmOnClickHandler = new BtnConfirmOnClickHandler();
        BtnNextOnClickHandler btnNextOnClickHandler = new BtnNextOnClickHandler();

        // set handlers
        this.btnDie.setOnClickListener(btnDieOnClickHandler);
        this.btnDer.setOnClickListener(btnDerOnClickHandler);
        this.btnDas.setOnClickListener(btnDasOnClickHandler);
        this.btnConfirm.setOnClickListener(btnConfirmOnClickHandler);
        this.btnNext.setOnClickListener(btnNextOnClickHandler);

        this.quiz = new Quiz(words);

        // shuffle the german_words in the quiz
        this.quiz.generateShuffledQuiz();

        // get first question
        currentQuestion = quiz.getNextQuestion();

        // display first question
        DisplayQuestion();
    }

    public static QuizManager LoadQuizManager(Context context, List<Word> words, ImageView imgWordImage, Button btnConfirm, Button btnNext, Button btnDas, Button btnDer, Button btnDie, TextView txtResult) {
        return new QuizManager(context, words, imgWordImage, btnConfirm, btnNext, btnDas, btnDer, btnDie, txtResult);
    }

    /**
     * Die button handler (sets die as the selected article) and highlights the Die button
     */
    private class BtnDieOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            selectedArticle = Article.Die;

            SelectedButton(btnDie);

            if(btnConfirm.getVisibility() == View.INVISIBLE)
                btnConfirm.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Der button handler (sets der as the selected article) and highlights the Der button
     */
    private class BtnDerOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            selectedArticle = Article.Der;

            SelectedButton(btnDer);

            if(btnConfirm.getVisibility() == View.INVISIBLE)
                btnConfirm.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Das button handler (sets das as the selected article) and highlights the Das button
     */
    private class BtnDasOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            selectedArticle = Article.Das;

            SelectedButton(btnDas);

            if(btnConfirm.getVisibility() == View.INVISIBLE)
                btnConfirm.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Confirm button handler which checks the users answer, adds the result to a list
     * for review later.
     */
    private class BtnConfirmOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v){

            // hide der das, and die buttons
            SetQuestionButtonsVisibility(View.INVISIBLE);

            // check if the answer is correct
            boolean checkAnswer = quiz.checkArticle(selectedArticle, currentQuestion);

            // display result text
            DisplayResult(checkAnswer);

            // record the result
            quiz.addResult(selectedArticle, currentQuestion);

            // display next button
            btnNext.setVisibility(View.VISIBLE);

            // hide confirm button
            btnConfirm.setVisibility(View.INVISIBLE);

            // change button text of next button if the current question is the last question
            if(quiz.checkFinalQuestion())
                btnNext.setText(resources.getText(R.string.btnNextCompletedText));
        }
    }

    /**
     * Next button click handler, to proceed to next question or review activity
     */
    private class BtnNextOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v){

            // hide the result text
            txtResult.setVisibility(View.INVISIBLE);

            // check if the questions are completed
            if(!quiz.checkFinalQuestion()){

                // load the next question
                currentQuestion = quiz.getNextQuestion();

                // display question
                DisplayQuestion();

                // hide the next button
                btnNext.setVisibility(View.INVISIBLE);

                // clear button colors
                ClearButtonColors();

                SetQuestionButtonsVisibility(View.VISIBLE);

            }else{

                // load results activity
                Intent reviewIntent = new Intent(context, ReviewActivity.class);

                // get the users answers from the quiz
                List<AnswerWord> answers = quiz.getResults();

                // pass the answers to review activity
                reviewIntent.putParcelableArrayListExtra(PracticeActivity.RESULT_LIST_ID, (ArrayList) answers);

                // start review activity
                context.startActivity(reviewIntent);

            }

        }
    }

    /**
     * Show or hide the quiz die, der, and das buttons
     * @param visibility Visibility of the die, der, and das buttons
     */
    private void SetQuestionButtonsVisibility(int visibility){

        // display the die, das, and der buttons
        btnDie.setVisibility(visibility);
        btnDer.setVisibility(visibility);
        btnDas.setVisibility(visibility);

    }

    /**
     * Display the question to the user
     */
    private void DisplayQuestion(){
        // display the next question to screen
        int questionImage = currentQuestion.getImage();
        imgWordImage.setImageResource(questionImage);
    }

    /**
     * Deselect all the buttons for the quiz
     */
    private void ClearButtonColors() {
        DeselectButton(btnDas);
        DeselectButton(btnDie);
        DeselectButton(btnDer);
    }

    /**
     * Deselect a specific button from the quiz
     * @param btn Button to deselect/highlight
     */
    private void DeselectButton(Button btn) {
        int btnSelectedColor = resources.getColor(R.color.btnDeselectedColor);
        btn.setBackgroundColor(btnSelectedColor);
    }

    /**
     * Set a button from the quiz as selected
     * @param btn Button to select/highlight
     */
    private void SelectedButton(Button btn) {

        // clear all the selected buttons
        ClearButtonColors();

        // get the selection color from resources
        int btnSelectedColor = resources.getColor(R.color.btnSelectedColor);

        // set the selection color for the selected button
        btn.setBackgroundColor(btnSelectedColor);
    }

    /**
     * Display Result
     * @param result true sets correct text and color, false sets incorrect text and color
     */
    private void DisplayResult(boolean result){

        String resultText;
        int resultColor;

        // make result text visible
        txtResult.setVisibility(View.VISIBLE);

        // get the result text, and color
        if(result){
            resultText = resources.getString(R.string.txtAnswerCorrect);
            resultColor = resources.getColor(R.color.btnCorrectColor);
        }else{
            resultText = resources.getString(R.string.txtAnswerIncorrect);
            resultColor = resources.getColor(R.color.btnIncorrectColor);
        }

        // display result to screen
        txtResult.setText(resultText);
        txtResult.setTextColor(resultColor);
    }
}

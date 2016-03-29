package bit.hawkhje1.languagetrainer.Managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import bit.hawkhje1.languagetrainer.Classes.AnswerWord;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;

public class Quiz {

    public static final String QUIZ_LOG = "QuizLog";

    private List<Word> words;
    private List<AnswerWord> answers;
    private Stack<Word> questions;

    private Word currentQuestion = null;
    private Article selectedArticle = null;

    /**
     * Create instance of Quiz and load german_words for quiz
     * @param words
     */
    public Quiz(List<Word> words){
        this.words = words;
        this.questions = new Stack<>();
        this.answers = new ArrayList<>();
    }

    /**
     * Load german_words into quiz
     * @param words
     */
    public void loadWords(List<Word> words){
        this.words = words;
    }

    /**
     * Generate a shuffled set of questions
     */
    public void shuffle(){

        // incase shuffle is called again...
        this.questions.clear();

        // create a copy of the german_words
        List<Word> shuffledWords = words;

        // shuffle german_words
        Collections.shuffle(shuffledWords);

        // add german_words to stack
        this.questions.addAll(shuffledWords);

    }

    /**
     * Get the current question from the quiz
     * @return current question
     */
    public Word getCurrentQuestion(){
        // get the current question from
        return currentQuestion;
    }

    public Article setArticle(Article selectedArticle){
        this.selectedArticle = selectedArticle;
        return this.selectedArticle;
    }

    public Article getArticle(){return selectedArticle; }

    public AnswerWord getResultData(){return new AnswerWord(currentQuestion, selectedArticle);}

    public boolean getResult(){
        return currentQuestion.containsArticle(selectedArticle);
    }

    /**
     * Get the next question in the quiz
     * @return next question in the quiz
     */
    public Word getNextQuestion(){

        // get the next question and store it in current question
        currentQuestion = questions.pop();

        // return the next question
        return currentQuestion;
    }

    /**
     * Check if the current question is the last question
     * @return true if question is final question, false if there is still more questions
     */
    public boolean isFinalQuestion(){

        // check if the stack is finished spitting out questions
        return questions.isEmpty();

    }

    public AnswerWord addResult(Word word, Article article){
        AnswerWord result = new AnswerWord(word, article);
        this.answers.add(result);
        return result;
    }

    public AnswerWord addResult(AnswerWord answerWord){
        this.answers.add(answerWord);
        return answerWord;
    }

    public List<AnswerWord> getResults(){ return this.answers; }
//
//    /**
//     * Check if the answer is correct
//     * @param article Answered Article
//     * @param word Current Question Word
//     * @return true if article and word match
//     */
//    public boolean checkArticle(Article article, Word word){
//        return word.containsArticle(article);
//    }

}

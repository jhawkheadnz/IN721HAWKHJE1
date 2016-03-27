package bit.hawkhje1.languagetrainer.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import bit.hawkhje1.languagetrainer.Enums.Article;

public class Quiz {

    private List<Word> words;
    private Stack<Word> questions = new Stack<>();
    private List<AnswerWord> userAnswers;

    private Word currentQuestion = null;

    public Quiz(){}

    /**
     * Create instance of Quiz and load german_words for quiz
     * @param words
     */
    public Quiz(List<Word> words){
        userAnswers = new ArrayList<>();
        this.words = words;
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
    public void generateShuffledQuiz(){

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
    public boolean checkFinalQuestion(){

        // check if the stack is finished spitting out questions
        return questions.isEmpty();

    }

    /**
     * Check if the answer is correct
     * @param article Answered Article
     * @param word Current Question Word
     * @return true if article and word match
     */
    public boolean checkArticle(Article article, Word word){
        return word.containsArticle(article);
    }

    /**
     * Add result to user answers
     * @param article
     * @param word
     */
    public void addResult(Article article, Word word){
        AnswerWord answer = new AnswerWord(word, article);
        this.userAnswers.add(answer);
    }

    public List<AnswerWord> getResults(){ return this.userAnswers; }

}

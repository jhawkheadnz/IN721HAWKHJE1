package bit.hawkhje1.languagetrainer.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import bit.hawkhje1.languagetrainer.Enums.Article;

public class AnswerWord implements Parcelable {

    public static final Creator<AnswerWord> CREATOR = new WordCreator();

    public static class WordCreator implements Creator<AnswerWord> {
        @Override
        public AnswerWord createFromParcel(Parcel in) {
            return new AnswerWord(in);
        }

        @Override
        public AnswerWord[] newArray(int size) {
            return new AnswerWord[size];
        }
    }

    // Word (Original Word)
    private Word word;

    // Article (User Answer)
    private Article article;

    public AnswerWord(Parcel in) {
        word = in.readParcelable(Word.class.getClassLoader());
        article = in.readParcelable(Article.class.getClassLoader());
    }

    /**
     * Creates an instance of AnswerWord, with Article Answer added
     * @param word Original Word
     * @param article Answered Artice
     */
    public AnswerWord(Word word, Article article) {
        this.word = word;
        this.article = article;
    }

    /**
     * Gets the Selected Article Answer
     * @return Selected Article
     */
    public Article getArticleAnswer() {
        return article;
    }

    /**
     * Sets Selected Article Answer
     * @param article Selected Article
     */
    public void setArticleAnswer(Article article) {
        this.article = article;
    }

    /**
     * Get word
     * @return question word
     */
    public Word getWord(){ return word; }

    /**
     * Get result
     * @return true if the result matches the word, false if the result doesn't match the word
     */
    public boolean getResult(){ return word.containsArticle(this.article); }

    /**
     * Set the question word
     * @param word Word
     */
    public void SetWord(Word word){ this.word = word; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.word, 0);
        dest.writeParcelable(this.article, 0);
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append(word.toString());
        sb.append("Answer: ");
        sb.append(article);
        sb.append(" (");

        // get the result
        boolean result = word.containsArticle(article);

        sb.append(result);
        sb.append(")");
        sb.append("\r\n");
        return sb.toString();

    }

}

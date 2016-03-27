package bit.hawkhje1.languagetrainer.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Enumeration;

import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.Enums.Gender;

/**
 * Word Class
 * Holds German Word Information, and English Translation
 */
public class Word implements Parcelable {

    private Gender gender;
    private Article article;
    private String noun;
    private String english_translation;
    private int image;

    public Word() {

    }

    /**
     * Word object holding German german_words with english translation
     *
     * @param gender              Word's Gender (Masculine, Feminine, Neutral)
     * @param article             Word's Article (Der, Die, Das)
     * @param noun                Word's Noun
     * @param english_translation English Translation
     * @param image               Word's Image (Image Resource ID)
     */
    public Word(Gender gender, Article article, String noun, String english_translation, int image) {
        this.gender = gender;
        this.article = article;
        this.noun = noun;
        this.english_translation = english_translation;
        this.image = image;
    }

    protected Word(Parcel in) {
        image = in.readInt();
        noun = in.readString();
        english_translation = in.readString();
        article = in.readParcelable(Article.class.getClassLoader());
        gender = in.readParcelable(Gender.class.getClassLoader());
    }

    public static final Creator<Word> CREATOR = new WordCreator();

    public static class WordCreator implements Creator<Word> {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    }

    /**
     * Get Word's Gender
     * @return Gender of Word
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set Word's Gender
     * @param gender Word's Gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Get Word's Article
     * @return Word's Article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Set Word's Article
     * @param article Word's Article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Get Word's Noun
     * @return Word's Noun
     */
    public String getNoun() {
        return noun;
    }

    /**
     * Set Word's Noun
     * @param noun Word's Noun
     */
    public void setNoun(String noun) {
        this.noun = noun;
    }

    /**
     * Get English Translation of Current Word
     * @return English Word
     */
    public String getEnglish_translation() {
        return english_translation;
    }

    /**
     * Set the English Translation of Current Word
     * @param english_translation English Word
     */
    public void setEnglish_translation(String english_translation) {
        this.english_translation = english_translation;
    }

    /**
     * Get the Image ID of the Image of the current word
     * @return Image ID
     */
    public int getImage() {
        return image;
    }

    /**
     * Set the image ID of the Image of the current word
     * @param image Image ID
     */
    public void setImage(int image) {
        this.image = image;
    }

    /**
     * Checks if the current word contains specific article
     * @param article Article to check
     * @return true if there is a match, false if there is not match
     */
    public boolean containsArticle(Article article){
        return this.article.equals(article);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Gender: ");
        sb.append(this.gender);
        sb.append(", ");

        sb.append("Article: ");
        sb.append(this.article);
        sb.append(", ");

        sb.append("Noun: ");
        sb.append(this.noun);
        sb.append(", ");

        sb.append("English: ");
        sb.append(this.english_translation);
        sb.append(", ");

        sb.append("ImageID: ");
        sb.append(this.image);
        sb.append("\r\n------------------\r\n");

        return sb.toString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(image);
        dest.writeString(noun);
        dest.writeString(english_translation);
        dest.writeParcelable(article, 0);
        dest.writeParcelable(gender, 0);

    }
}


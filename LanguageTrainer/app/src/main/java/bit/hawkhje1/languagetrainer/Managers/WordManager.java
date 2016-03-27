package bit.hawkhje1.languagetrainer.Managers;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bit.hawkhje1.languagetrainer.App;
import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.Enums.Gender;

public class WordManager {

    private List<Word> words;

    /**
     * Word Manager
     * @param words_xml ID of XML Resource
     */
    public WordManager(int words_xml){

        words = loadWordsFromXml(words_xml);

    }

    /**
     * Loads german_words from an xml resource file
     * @param language_id
     * @return
     */
    private List<Word> loadWordsFromXml(int language_id) {

        Context context = App.getContext();
        Resources res = context.getResources();
        XmlResourceParser xrp = res.getXml(language_id);

        List<Word> words = new ArrayList<>();

        try {

            String name = "";

            int eventType = xrp.getEventType();

            // create word
            Word word = new Word();

            // loop through xml document
            while (eventType != XmlResourceParser.END_DOCUMENT) {

                if (eventType == XmlResourceParser.START_TAG) {

                    name = xrp.getName();

                    // create new word object
                    if(name.equals("word"))
                        word = new Word();

                }

                if (eventType == XmlResourceParser.TEXT) {

                    // get gender from xml
                    if(name.equals("gender"))
                        word.setGender(Gender.valueOf(xrp.getText()));

                    // get article from xml
                    if(name.equals("article"))
                        word.setArticle(Article.valueOf(xrp.getText()));

                    // get noun from xml
                    if(name.equals("noun"))
                        word.setNoun(xrp.getText());

                    // get english word from xml
                    if(name.equals("english"))
                        word.setEnglish_translation(xrp.getText());

                    // get image from xml
                    if(name.equals("image")){
                        String imgName = xrp.getText();
                        int imgID = res.getIdentifier(imgName, "drawable", App.getContext().getPackageName());
                        word.setImage(imgID);
                    }

                }

                if(eventType == XmlResourceParser.END_TAG) {
                    if(xrp.getName().equals("word"))
                        words.add(word);
                }

                eventType = xrp.next();

            }

            return words;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets language german_words
     * @return Words from language
     */
    public List<Word> getWords() {
        return words;
    }

}

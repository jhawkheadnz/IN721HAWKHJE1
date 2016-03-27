package bit.hawkhje1.languagetrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.languagetrainer.Classes.Word;
import bit.hawkhje1.languagetrainer.Managers.QuizManager;
import bit.hawkhje1.languagetrainer.Managers.WordManager;

public class PracticeActivity extends AppCompatActivity {

    public static final String RESULT_LIST_ID = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        /* Controls */
        Button btnDie = (Button)findViewById(R.id.btnDie);
        Button btnDas = (Button)findViewById(R.id.btnDas);
        Button btnDer = (Button)findViewById(R.id.btnDer);
        Button btnNext = (Button)findViewById(R.id.btnNextQuestion);
        Button btnConfirm = (Button)findViewById(R.id.btnConfirm);
        ImageView imgWordImage = (ImageView)findViewById(R.id.imgWordImage);
        TextView txtResult = (TextView)findViewById(R.id.txtResult);

        /* Word Manager */
        WordManager wordManager = new WordManager(R.xml.german_words);

        // get the german words from the word manager
        List<Word> germanWords = wordManager.getWords();

        /* Quiz Manager */
        QuizManager.LoadQuizManager(PracticeActivity.this, germanWords,imgWordImage,btnConfirm, btnNext, btnDas, btnDer, btnDie, txtResult);

    }

}
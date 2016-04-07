package bit.hawkhje1.externaldatastorage1;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static int DEFAULT_COLOR = Color.BLACK;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;
    private Resources res;

    private TextView txtGreeting = (TextView) findViewById(R.id.txtHelloWorld);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get resources
        res = getResources();

        // get share preferences object from current activity
        prefs = getSharedPreferences("prefsDemo", MODE_PRIVATE);

        // get preferences editor from the shared preferences object
        prefEditor = prefs.edit();

        // get view objects
        Spinner spColors = (Spinner)findViewById(R.id.spColors);
        Button btnChangeLanguage = (Button)findViewById(R.id.btnChangeLanguage);
        Button btnChangeColor = (Button)findViewById(R.id.btnChangeColor);

        // get the language preferences field from the preferences object
        // setting the default value to null
        String languagePreferences = prefs.getString("language", null);

        // check if a language preference has been defined
        if (languagePreferences != null) {

            // get the greeting text for the specified language
            String greetingInChosenLanguage = getGreeting(Language.valueOf(languagePreferences));

            // set the greeting text for the specified language
            txtGreeting.setText(greetingInChosenLanguage);

        }

        // get the color value from prefs if it's set
        int greetingColor = prefs.getInt("color", DEFAULT_COLOR);

        // set the greeting color
        txtGreeting.setTextColor(greetingColor);

        // create on click handlers
        BtnChangeLanguageHandler btnChangeLanguageHandler = new BtnChangeLanguageHandler();
        BtnChangeGreetingTextColorHandler btnChangeGreetingTextColorHandler = new BtnChangeGreetingTextColorHandler();

        // set on click listeners
        btnChangeLanguage.setOnClickListener(btnChangeLanguageHandler);
        btnChangeColor.setOnClickListener(btnChangeGreetingTextColorHandler);

        // get a list of custom colors
        List<CustomColor> customColorsList = getColorsList();

        // create an array adapter that lists custom colors
        ArrayAdapter<CustomColor> customColorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customColorsList);

        // set adapter
        spColors.setAdapter(customColorArrayAdapter);

    }

    // generate colors list
    public List<CustomColor> getColorsList(){

        List<CustomColor> customColorsList = new ArrayList<>();

        customColorsList.add(new CustomColor("Blue", res.getColor(R.color.txtColorBlue)));
        customColorsList.add(new CustomColor("Orange", res.getColor(R.color.txtColorOrange)));
        customColorsList.add(new CustomColor("Green", res.getColor(R.color.txtColorGreen)));

        return customColorsList;
    }

    private class CustomColor {

        private String colorName;
        private int color;

        public CustomColor(String colorName, int color){
            this.colorName = colorName;
            this.color = color;
        }

        public String getColorName(){ return this.colorName; }
        public int getColor(){ return this.color; }

        public String toString(){
            return colorName;
        }
    }

    private String getGreeting(Language language){

        String greeting = "";

        switch(language){
            case English:
                greeting = res.getString(R.string.english_hello);
                break;
            case French:
                greeting = res.getString(R.string.french_hello);
                break;
            case German:
                greeting = res.getString(R.string.german_hello);
                break;
            case Spanish:
                greeting = res.getString(R.string.spanish_hello);
                break;
        }

        return greeting;
    }

    private enum Language {
        English,
        French,
        German,
        Spanish
    }

    private class BtnChangeLanguageHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            RadioGroup rLangaugeGroup = (RadioGroup)findViewById(R.id.rgLanguages);
            int selectedLanguageId = rLangaugeGroup.getCheckedRadioButtonId();
            RadioButton rbSelectedLanguage = (RadioButton)findViewById(selectedLanguageId);
            String selectedLanguage = rbSelectedLanguage.getText().toString();

            prefEditor.putString("language", selectedLanguage);
            prefEditor.commit();

            String updatedGreetingText = getGreeting(Language.valueOf(selectedLanguage));
            txtGreeting.setText(updatedGreetingText);
        }
    }

    private class BtnChangeGreetingTextColorHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Spinner spColors = (Spinner)findViewById(R.id.spColors);
            CustomColor selectedCustomColor = (CustomColor)spColors.getSelectedItem();
            int selectedColor = selectedCustomColor.getColor();

            prefEditor.putInt("color", selectedColor);
            prefEditor.commit();

            txtGreeting.setTextColor(selectedColor);
        }
    }



}

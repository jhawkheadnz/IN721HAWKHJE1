package bit.hawkhje1.musicschoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get action button
        Button btnShowInstrument = (Button)findViewById(R.id.btnShowInstrument);

        SelectInstrumentButtonHandler selectInstrumentButtonHandler = new SelectInstrumentButtonHandler();
        btnShowInstrument.setOnClickListener(selectInstrumentButtonHandler);

        // get string array from strings resource
        String[] startMonths = getResources().getStringArray(R.array.monthsStart);

        // create adaptor for array of months
        ArrayAdapter<String> startMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, startMonths);

        // get spinner control from view
        Spinner spinnerMonthStartSelection = (Spinner)findViewById(R.id.spinnerMonthStart);

        // attach adapter to to spinner
        spinnerMonthStartSelection.setAdapter(startMonthAdapter);
    }

    class SelectInstrumentButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            // get radio group
            RadioGroup instrumentsRadioGroup = (RadioGroup)findViewById(R.id.rbtnGroupInstruments);

            // get selected radio button and text from
            int selectedInstrument = instrumentsRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedInstrumentBtn = (RadioButton)findViewById(selectedInstrument);
            String selectedInstrumentText = selectedInstrumentBtn.getText().toString();

            // get selection text from spinner
            Spinner spinnerStartMonth = (Spinner)findViewById(R.id.spinnerMonthStart);
            String selectedStartMonth = (String)spinnerStartMonth.getSelectedItem();

            // get response text TextView object
            TextView txtResponse = (TextView)findViewById(R.id.txtResponse);

            // make text visible
            txtResponse.setVisibility(View.VISIBLE);

            // get formatted response text data from string resources
            String responseText = getResources().getString(R.string.responseText);

            // display formatted output to screen
            txtResponse.setText(String.format(responseText, selectedInstrumentText, selectedStartMonth));

        }
    }

}
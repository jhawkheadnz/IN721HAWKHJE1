package bit.hawkhje1.musicschoolapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SchoolAppActivity";

    EnrollmentDialogFragment enrollmentDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity's onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        //enrollmentDialogFragment = new EnrollmentDialogFragment();

        // create instance of select instrument button handler
        SelectInstrumentButtonHandler selectInstrumentButtonHandler = new SelectInstrumentButtonHandler();

        // get action button
        Button btnShowInstrument = (Button)findViewById(R.id.btnShowInstrument);
        btnShowInstrument.setOnClickListener(selectInstrumentButtonHandler);

        // get string array from strings resource
        String[] startMonths = getResources().getStringArray(R.array.monthsStart);

        // create adaptor for array of months
        ArrayAdapter<String> startMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, startMonths);

        // get spinner control from view
        Spinner spinnerMonthStartSelection = (Spinner)findViewById(R.id.spinnerMonthStart);

        // attach adapter to to spinner
        spinnerMonthStartSelection.setAdapter(startMonthAdapter);

        // close the activity that opened this activity
        // (so when user presses back button they don't see the splash screen)
        //finish();
    }

    class SelectInstrumentButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // get the fragment manager
            FragmentManager fm = getFragmentManager();

            // get the selected instrument
            String selectedInstrument = getSelectedInstrument();
            String selectedMonth = getSelectedMonth();

            // create an instance of EnrollmentDialogFragment using the newInstance pattern
            // and passing in the instrument that was selected
            enrollmentDialogFragment = EnrollmentDialogFragment.newInstance(selectedInstrument, selectedMonth);

            // show the dialog
            enrollmentDialogFragment.show(fm, "confirm");

        }
    }

    private String getSelectedInstrument()
    {
        String instrument = "";

        // get radio group
        RadioGroup instrumentsRadioGroup = (RadioGroup)findViewById(R.id.rbtnGroupInstruments);

        // get selected radio button and text from
        int selectedInstrument = instrumentsRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedInstrumentBtn = (RadioButton)findViewById(selectedInstrument);
        instrument = selectedInstrumentBtn.getText().toString();

        return instrument;
    }

    private String getSelectedMonth()
    {
        String month = "";

        // get selection text from spinner
        Spinner spinnerStartMonth = (Spinner)findViewById(R.id.spinnerMonthStart);

        month = (String)spinnerStartMonth.getSelectedItem();

        return month;
    }

    public void processEnrollment(boolean doEnrollment)
    {
        enrollmentDialogFragment.dismiss();

        String selectedInstrument = getSelectedInstrument();
        String selectedMonth = getSelectedMonth();

        // get response text TextView object
        TextView txtResponse = (TextView) findViewById(R.id.txtResponse);

        // make text visible
        txtResponse.setVisibility(View.VISIBLE);

        if(doEnrollment) {
            // get formatted response text data from string resources
            String responseText = getResources().getString(R.string.responseText);

            // display formatted output to screen
            txtResponse.setText(String.format(responseText, selectedInstrument, selectedMonth));
        }else{
            txtResponse.setText("Oh Well...");
        }
    }

}
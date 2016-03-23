package bit.hawkhje1.musicschoolapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EnrollmentDialogFragment extends DialogFragment {

    public static final String LOG_INFO = "AppInfo";
    public static final String INSTRUMENT_ID = "instrument";
    public static final String MONTH_ID = "month";

    public static EnrollmentDialogFragment newInstance(String instrument, String month)
    {
        EnrollmentDialogFragment enrollmentDialogFragment = new EnrollmentDialogFragment();

        Bundle enrollmentArgs = new Bundle();
        enrollmentArgs.putString(INSTRUMENT_ID, instrument);
        enrollmentArgs.putString(MONTH_ID, month);

        enrollmentDialogFragment.setArguments(enrollmentArgs);

        return enrollmentDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // get reference to dialog window
        Dialog enrollmentDialog = getDialog();

        String instrument = getArguments().getString(INSTRUMENT_ID);
        String month = getArguments().getString(MONTH_ID);

        // set title for dialog window
        String title = getResources().getString(R.string.enroll_question_title);
        enrollmentDialog.setTitle(title);

        // get layout
        View dialogView = inflater.inflate(R.layout.dialog_fragment, container);

        // get the textview from the dialog layout
        TextView enrollmentTextBox = (TextView)dialogView.findViewById(R.id.txtEnrollQuestion);

        // get the question text from the string resource file
        String formattedQuestion = getResources().getString(R.string.enroll_question);

        // create question using formatted string
        String question = String.format(formattedQuestion, instrument, month);

        // output the question to screen
        enrollmentTextBox.setText(question);

        // ========================== Buttons ===============================

        Button btnNo = (Button)dialogView.findViewById(R.id.btnNo);
        BtnNoOnClickHandler btnNoOnClickHandler = new BtnNoOnClickHandler();
        btnNo.setOnClickListener(btnNoOnClickHandler);

        Button btnYes = (Button)dialogView.findViewById(R.id.btnYes);
        BtnYesOnClickHandler btnYesOnClickHandler = new BtnYesOnClickHandler();
        btnYes.setOnClickListener(btnYesOnClickHandler);


        return dialogView;

    }

    class BtnYesOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.processEnrollment(true);
        }
    }

    class BtnNoOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.processEnrollment(false);
        }
    }

}

package bit.hawkhje1.languagetrainer.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bit.hawkhje1.languagetrainer.PracticeActivity;
import bit.hawkhje1.languagetrainer.R;

public class QuitQuizDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        Dialog quitDialog = getDialog();

        View dialogView = inflater.inflate(R.layout.quitquiz_dialog_fragment, container);

        Button btnYes = (Button)dialogView.findViewById(R.id.btnDialogYes);
        BtnYesClickHandler btnYesClickHandler = new BtnYesClickHandler();
        btnYes.setOnClickListener(btnYesClickHandler);

        Button btnNo = (Button)dialogView.findViewById(R.id.btnDialogNo);
        BtnNoClickHandler btnNoClickHandler = new BtnNoClickHandler();
        btnNo.setOnClickListener(btnNoClickHandler);

        return dialogView;

    }

    private class BtnYesClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            // yes logic
            PracticeActivity practiceActivity = (PracticeActivity)getActivity();
            practiceActivity.ReturnToMainScreen();

        }
    }

    private class BtnNoClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){

            // no logic
            dismiss();

        }
    }

}

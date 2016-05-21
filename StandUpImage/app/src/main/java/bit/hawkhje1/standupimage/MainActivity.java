package bit.hawkhje1.standupimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import com.daimajia.androidanimations.library.attention.StandUpAnimator;

public class MainActivity extends AppCompatActivity {

    private ImageView standUpImage;

    private Button btnStandUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        standUpImage = (ImageView)findViewById(R.id.standUpImage);
        btnStandUp = (Button)findViewById(R.id.btnStandUp);

        BtnStandUpHandler btnStandUpHandler = new BtnStandUpHandler();
        btnStandUp.setOnClickListener(btnStandUpHandler);
    }

    private class BtnStandUpHandler implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            StandUpAnimator standUpAnimator = new StandUpAnimator();
            standUpAnimator.prepare(standUpImage);
            standUpAnimator.start();
        }
    }


}

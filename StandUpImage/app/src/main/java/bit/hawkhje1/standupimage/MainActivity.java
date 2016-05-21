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

        // get view items
        standUpImage = (ImageView)findViewById(R.id.standUpImage);
        btnStandUp = (Button)findViewById(R.id.btnStandUp);

        // setup button handler for stand up button
        BtnStandUpHandler btnStandUpHandler = new BtnStandUpHandler();
        btnStandUp.setOnClickListener(btnStandUpHandler);
    }

    // create button handler for standing up
    private class BtnStandUpHandler implements View.OnClickListener {
        
        @Override
        public void onClick(View v) {

            // create stand up animator
            StandUpAnimator standUpAnimator = new StandUpAnimator();

            // prepare the view item that is going to stand up
            standUpAnimator.prepare(standUpImage);

            // begin stand up animation
            standUpAnimator.start();
        }
    }


}

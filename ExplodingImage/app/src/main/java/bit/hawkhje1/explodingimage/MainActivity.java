package bit.hawkhje1.explodingimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.easyandroidanimations.library.ExplodeAnimation;

public class MainActivity extends AppCompatActivity {

    private static final long ANIMATION_DURATION = 500;

    private Button btnExplodeImage;
    private ImageView imgToExplode;
    private ExplodeAnimation explodeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get explode image button from view
        btnExplodeImage = (Button)findViewById(R.id.btnExplodeImage);

        // get image to explode
        imgToExplode = (ImageView)findViewById(R.id.imgToExplode);

        // create instance of explode image button handler
        ExplodeImageBtnHandler explodeImageBtnHandler = new ExplodeImageBtnHandler();

        // attach explode button click handler to button
        btnExplodeImage.setOnClickListener(explodeImageBtnHandler);


    }

    // create explode image button handler
    class ExplodeImageBtnHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // create instance of bounce animation passing in the view to explode
            explodeAnimation = new ExplodeAnimation(imgToExplode);

            //set explode matrix
            explodeAnimation.setExplodeMatrix(ExplodeAnimation.MATRIX_2X2);

            // create intropolater
            DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();

            // set interpolater
            explodeAnimation.setInterpolator(decelerateInterpolator);

            // set duration
            explodeAnimation.setDuration(ANIMATION_DURATION);

            // begin the animation
            explodeAnimation.animate();

        }
    }



}

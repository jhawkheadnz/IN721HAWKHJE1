package bit.hawkhje1.ripplebackground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.skyfishjy.library.RippleBackground;

public class MainActivity extends AppCompatActivity {

    private RippleBackground rippleBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get ripple background from View
        rippleBackground = (RippleBackground)findViewById(R.id.content);

        // get image from view
        ImageView image = (ImageView)findViewById(R.id.centerImage);

        // create ripple background handler
        RippleBtnOnClickHandler rippleBtnOnClickHandler = new RippleBtnOnClickHandler();

        // attach ripple button handler to image
        image.setOnClickListener(rippleBtnOnClickHandler);
    }

    // handle click for ripple background
    private class RippleBtnOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // get the ripple background state
            boolean animationRunning = rippleBackground.isRippleAnimationRunning();

            // if the animation isn't running, start the animation, else stop
            if(!animationRunning){
                rippleBackground.startRippleAnimation();
            }else{
                rippleBackground.stopRippleAnimation();
            }
        }
    }

}

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

        rippleBackground = (RippleBackground)findViewById(R.id.content);

        ImageView image = (ImageView)findViewById(R.id.centerImage);

        RippleBtnOnClickHandler rippleBtnOnClickHandler = new RippleBtnOnClickHandler();

        image.setOnClickListener(rippleBtnOnClickHandler);
    }

    private class RippleBtnOnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            boolean animationRunning = rippleBackground.isRippleAnimationRunning();

            if(!animationRunning){
                rippleBackground.startRippleAnimation();
            }else{
                rippleBackground.stopRippleAnimation();
            }
        }
    }

}

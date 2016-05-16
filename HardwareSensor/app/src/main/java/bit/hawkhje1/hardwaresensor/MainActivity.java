package bit.hawkhje1.hardwaresensor;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SizeF;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    // get window size data for boundry
    private Point windowSize;

    // create a float gravity array for sensor data
    private float[] gravity = new float[]{ 0, 0, 0 };

    // create sensor info string
    private String sensorInfo;

    // create custom canvas view
    private CustomCanvas customCanvas;

    // create field for sensor manager
    private SensorManager sensorManager;

    // create sensor field for accelerometer
    private Sensor accelerometer;

    // create tilt handler
    private TiltHandler tiltHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create custom canvas and pass in context
        customCanvas = new CustomCanvas(this);

        // instead of setting layout as content view
        // set custom canvas SurfaceView as Content View
        // to draw canvas to the screen
        setContentView(customCanvas);

        // initialize
        init();

    }

    // setup sensor stuff
    private void init(){

        // get the sensor manager instance from system service
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        // get the accelerometer service using the sensor manager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // instantiate tilt handler
        tiltHandler = new TiltHandler();

        // set empty string for sensor info
        sensorInfo = "";

        // get window size
        Display display = getWindowManager().getDefaultDisplay();
        windowSize = new Point();
        display.getSize(windowSize);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister sensor event listener
        sensorManager.unregisterListener(tiltHandler);

        // pause drawing
        customCanvas.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register sensor event listener
        sensorManager.registerListener(tiltHandler, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // resume drawing
        customCanvas.resume();
    }

    // tilt event listener (using Accelerometer)
    private class TiltHandler implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                // get the values from accelerometer
                // (from android documentation)
                float alpha = 0.8f;

                // get the gravity values from each x, y, z axis
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[0] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[0] + (1 - alpha) * event.values[2];

                // set output to draw to canvas
                sensorInfo = String.format("x:%.3f, y:%.3f, z:%.3f", gravity[0], gravity[1], gravity[2]);

                // display information to canvas
                customCanvas.drawText(sensorInfo);

                float boxXPos = customCanvas.getBoxX();
                float boxYPos = customCanvas.getBoxY();
                float boxWidth = customCanvas.getBoxWidth();
                float boxHeight = customCanvas.getBoxHeight();
                float windowWidth = windowSize.x;
                float windowHeight = customCanvas.getHeight();

                float x = gravity[0] * -1;
                float y = gravity[1];
                float z = gravity[2];

                float velocity = 5f;

                // when the x-axis gravity is less than 0.5f move box left
                if(x < -0.5f){
                    if(boxXPos >= 0)
                        boxXPos -= velocity;
                }

                // if the box is going too fast and skips 0,
                // set it to 0
                if(boxXPos < 0)
                    boxXPos = 0;

                // when the x-axis gravity is greater than 0.5f move box right
                if(x > 0.5f){
                    if((boxXPos + boxWidth) <= windowWidth)
                        boxXPos += velocity;
                }

                // if the box goes beyond the point it's suppose
                // to hit the right side of the screen, reset the
                // box position
                if((boxXPos + boxWidth) > windowWidth)
                    boxXPos = windowWidth - boxWidth;

                if(y < -0.5f)
                    if(boxYPos >= 0)
                        boxYPos -= velocity;

                if(boxYPos < 0)
                    boxYPos = 0;

                if(y > 0.5f)
                    if((boxYPos + boxHeight) <= windowHeight)
                        boxYPos += velocity; //Math.abs(y) * velocity;

                if((boxYPos + boxHeight) > windowHeight)
                    boxYPos = windowHeight - boxHeight;

                // update the box position
                customCanvas.setBoxX(boxXPos);
                customCanvas.setBoxY(boxYPos);

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {



        }
    }

}
package bit.hawkhje1.locationteleporterrand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static double MIN_LONGITUDE = -180;
    private static double MAX_LONGITUDE = 180;
    private static double MIN_LATITUDE = -90;
    private static double MAX_LATITUDE = 90;

    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvLocation;
    private ImageView imgLocation;
    private Button btnTeleport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup textviews
        tvLatitude = (TextView)findViewById(R.id.tvLatitudeValue);
        tvLongitude = (TextView)findViewById(R.id.tvLongitudeValue);
        tvLocation = (TextView)findViewById(R.id.tvLocation);

        // setup image view
        imgLocation = (ImageView)findViewById(R.id.imgLocation);

        // setup button
        btnTeleport = (Button)findViewById(R.id.btnGenerateLocation);

        // attach button click handler
        BtnClickTeleportHandler btnClickTeleportHandler = new BtnClickTeleportHandler();
        btnTeleport.setOnClickListener(btnClickTeleportHandler);

    }

    // teleportation button
    public class BtnClickTeleportHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Coordinates teleportationLocation = getLocation();

            double longitude = teleportationLocation.getLongitude();
            double latitude = teleportationLocation.getLatitude();

            String longitudeText = Double.toString(longitude);
            String latitudeText = Double.toString(latitude);

            tvLongitude.setText(longitudeText);
            tvLatitude.setText(latitudeText);
        }

    }

    // get the location coordinates
    public Coordinates getLocation(){

        Random rand = new Random();

        double longitude = rand.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE) + MIN_LONGITUDE;
        double latitude = rand.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE) + MIN_LATITUDE;

        return new Coordinates(longitude, latitude);
    }

}

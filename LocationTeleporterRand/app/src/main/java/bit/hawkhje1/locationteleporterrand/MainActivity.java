package bit.hawkhje1.locationteleporterrand;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.locationteleporterrand.Classes.FlickrAsyncTask;
import bit.hawkhje1.locationteleporterrand.Classes.FlickrInfo;
import bit.hawkhje1.locationteleporterrand.Classes.GeoPluginInfo;
import bit.hawkhje1.locationteleporterrand.Interfaces.AsyncCallback;
import bit.hawkhje1.locationteleporterrand.Interfaces.TeleportationListener;
import bit.hawkhje1.locationteleporterrand.Managers.TeleportationManager;

public class MainActivity extends AppCompatActivity {

    // for logcat
    private static final String MAIN_ACTIVITY_INFO = "MAIN_ACTIVITY_INFO";

    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvLocation;
    private ImageView imgLocation;
    private TextView tvImageNotFound;

    // create teleportation manager
    private TeleportationManager teleportationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup textviews
        tvLatitude = (TextView)findViewById(R.id.tvLatitudeValue);
        tvLongitude = (TextView)findViewById(R.id.tvLongitudeValue);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvImageNotFound = (TextView)findViewById(R.id.tvImageNotFound);

        // setup image view
        imgLocation = (ImageView)findViewById(R.id.imgLocation);

        // setup button
        Button btnTeleport = (Button) findViewById(R.id.btnGenerateLocation);

        // attach button click handler
        BtnClickTeleportHandler btnClickTeleportHandler = new BtnClickTeleportHandler();
        btnTeleport.setOnClickListener(btnClickTeleportHandler);

        // create teleportation manager
        teleportationManager = new TeleportationManager(MainActivity.this);

        OnTeleportHandler onTeleportHandler = new OnTeleportHandler();
        teleportationManager.setTeleportationListener(onTeleportHandler);
    }

    // when teleportation has occurred
    public class OnTeleportHandler implements TeleportationListener {

        @Override
        public void onTeleport(Object... data) {

            // if data has come from the teleportation manager
            if(data[0] != null) {

                // convert data into a geoplugin info object
                GeoPluginInfo info = (GeoPluginInfo) data[0];

                // get the longitude and latitude locations from the info object
                String latitude = String.format("%s", info.getLatitude());
                String longitude = String.format("%s", info.getLongitude());

                // display the longitude and latitude values
                tvLatitude.setText(latitude);
                tvLongitude.setText(longitude);

                // display the place and country code
                tvLocation.setText(String.format("%s, %s", info.getPlace(), info.getCountryCode()));

                // check flickr and see if there are any images available for the location
                FlickrAsyncTask flickrAsyncTask = new FlickrAsyncTask(MainActivity.this);

                // callback object
                OnFlickrUpdate onFlickrUpdate = new OnFlickrUpdate();

                // set callback listener
                flickrAsyncTask.setCallbackListener(onFlickrUpdate);

                // execute query passing in latitude and longitude values
                flickrAsyncTask.execute(latitude, longitude);
            }

        }

    }

    // when flickrAsyncTask has grabbed content from Flickr
    public class OnFlickrUpdate implements AsyncCallback<List<FlickrInfo>>{

        @Override
        public void run(List<FlickrInfo> result) {

            // if a result has been found for flickr
            if(result.size() > 0) {

                // display the image
                imgLocation.setImageBitmap(result.get(0).getImage());

            }else{

                // if an image has not been found for the location, display image_not_found image
                Drawable image = getResources().getDrawable(R.drawable.img_not_found);
                imgLocation.setImageDrawable(image);

                // set the "Image Not Found" textview to visible
                tvImageNotFound.setVisibility(View.VISIBLE);
            }

        }
    }

    // teleportation button click handler
    public class BtnClickTeleportHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // if the image not found textview is visibile hide it
            if(tvImageNotFound.getVisibility() == View.VISIBLE)
                tvImageNotFound.setVisibility(View.INVISIBLE);

            // teleport the user to a random location
            teleportationManager.Teleport();
        }
    }

}
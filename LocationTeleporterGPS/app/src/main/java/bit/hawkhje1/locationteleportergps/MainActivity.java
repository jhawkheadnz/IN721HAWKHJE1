package bit.hawkhje1.locationteleportergps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.locationteleportergps.Classes.FlickrAsyncTask;
import bit.hawkhje1.locationteleportergps.Classes.FlickrInfo;
import bit.hawkhje1.locationteleportergps.Classes.GeoPluginInfo;
import bit.hawkhje1.locationteleportergps.Classes.Globals;
import bit.hawkhje1.locationteleportergps.Interfaces.AsyncCallback;
import bit.hawkhje1.locationteleportergps.Interfaces.TeleportationListener;
import bit.hawkhje1.locationteleportergps.Managers.TeleportationManager;

import static bit.hawkhje1.locationteleportergps.Classes.Globals.*;

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

        // setup location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // set default criteria
        Criteria criteria = new Criteria();

        // get provider
        String providerName = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationHandler locationHandler = new LocationHandler();

        locationManager.requestLocationUpdates(providerName, Globals.LocationManager.MIN_UPDATE_TIME,
                Globals.LocationManager.MIN_DISTANCE, locationHandler);

        // setup textviews
        tvLatitude = (TextView)findViewById(R.id.tvLatitudeValue);
        tvLongitude = (TextView)findViewById(R.id.tvLongitudeValue);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvImageNotFound = (TextView)findViewById(R.id.tvImageNotFound);

        // setup image view
        imgLocation = (ImageView)findViewById(R.id.imgLocation);

        // create teleportation manager
        teleportationManager = new TeleportationManager(MainActivity.this);

        OnTeleportHandler onTeleportHandler = new OnTeleportHandler();
        teleportationManager.setTeleportationListener(onTeleportHandler);
    }

    public class LocationHandler implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            Log.d(MAIN_ACTIVITY_INFO, "Updating Location >>>>> " + location.toString());

            String longitude = Double.toString(location.getLongitude());
            String latitude = Double.toString(location.getLatitude());

            // if the image not found textview is visibile hide it
            if(tvImageNotFound.getVisibility() == View.VISIBLE)
                tvImageNotFound.setVisibility(View.INVISIBLE);

            tvLongitude.setText(longitude);
            tvLatitude.setText(latitude);

            teleportationManager.Teleport(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(MAIN_ACTIVITY_INFO, "onStatusChanged(): " + provider + " " + status + " " + extras.toString());
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(MAIN_ACTIVITY_INFO, "onProviderEnabled(): " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(MAIN_ACTIVITY_INFO, "onProviderDisabled(): " + provider);
        }
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
                //tvLatitude.setText(latitude);
                //tvLongitude.setText(longitude);

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
                Drawable image = getResources().getDrawable(R.drawable.image_unavailable);
                imgLocation.setImageDrawable(image);

                // set the "Image Not Found" textview to visible
                tvImageNotFound.setVisibility(View.VISIBLE);
            }

        }
    }

}
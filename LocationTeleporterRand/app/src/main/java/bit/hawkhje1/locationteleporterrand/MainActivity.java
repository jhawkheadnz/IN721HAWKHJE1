package bit.hawkhje1.locationteleporterrand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

            GeoPluginAsyncTask geoPlugin = new GeoPluginAsyncTask();
            geoPlugin.execute();

            try {
                // get the geoplugin information
                GeoPluginInfo geoPluginInfo = geoPlugin.get();

                // get the coordinates from geoplugin
                Coordinates teleportationLocation = geoPluginInfo.getOriginalCoordinates();

                // get individual coordinates from geoplugin
                double longitude = teleportationLocation.getLongitude();
                double latitude = teleportationLocation.getLatitude();

                // parse the longitude and latitude values
                String longitudeText = Double.toString(longitude);
                String latitudeText = Double.toString(latitude);

                // output geoplugin data to screen
                tvLongitude.setText(longitudeText);
                tvLatitude.setText(latitudeText);

                // output location to screen
                tvLocation.setText(String.format("Closest Location: %s, %s", geoPluginInfo.getPlace(), geoPluginInfo.getCountryCode()));

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    // get the location coordinates
    public Coordinates getLocation(){

        // generate a random longitude location
        double longitude = Globals.RandomDouble(Globals.MIN_LONGITUDE, Globals.MAX_LONGITUDE);

        // generate a random latitude location
        double latitude = Globals.RandomDouble(Globals.MIN_LATITUDE, Globals.MAX_LATITUDE);

        // return coordinates
        return new Coordinates(longitude, latitude);

    }

    // async task for GeoPlugin
    public class GeoPluginAsyncTask extends AsyncTask<String, Integer, GeoPluginInfo>
    {
        private static final String GEOPLUGIN_ASYNCTASK = "GEOPLUGIN_ASYNC";

        @Override
        protected GeoPluginInfo doInBackground(String... params) {

            GeoPluginInfo geoPluginInfo = null;

            try {

                String geoPluginContent;
                Coordinates randCoordinates;

                int geoPluginIterations = 0;

                do {

                    // generate random coordinates
                    randCoordinates = getLocation();

                    // output geoplugin async task to log
                    Log.d(GEOPLUGIN_ASYNCTASK, "Checking Coordinates " + randCoordinates.toString());

                    // format the URL
                    String formattedURL = String.format(Globals.GEOPLUGIN_URL, randCoordinates.getLatitude(), randCoordinates.getLongitude());

                    // create URL
                    URL geoPluginURL = new URL(formattedURL);

                    // create an HTTP URL Connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) geoPluginURL.openConnection();

                    // create an input stream
                    InputStream inputStream = httpURLConnection.getInputStream();

                    // create input stream reader
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    // create buffered reader
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    // create a string builder to get content from geoplugin
                    StringBuilder content = new StringBuilder();

                    // create empty string
                    String currentLine;

                    // loop through each line that's returned
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        content.append(currentLine);
                    }

                    geoPluginContent = content.toString();
                    Log.d(GEOPLUGIN_ASYNCTASK, "Content Received From GeoPlugin: " + geoPluginContent);

                    geoPluginIterations++;

                    if(geoPluginIterations == Globals.MAX_GEOPLUGIN_ITERATIONS) {
                        throw new GeoPluginMaxIterationsException("Application Failed to Find a Location within the Specific Iterations");
                    }

                }while(geoPluginContent.equals("[[]]"));

                // Create JSONObject from GeoPlugin API
                JSONObject geoPluginJSON = new JSONObject(geoPluginContent);
                Log.d(GEOPLUGIN_ASYNCTASK, "JSON Object Output" + geoPluginJSON.toString());

                // =========================== Get Content from JSON ===============================
                String place = geoPluginJSON.getString("geoplugin_place");
                String countryCode = geoPluginJSON.getString("geoplugin_countryCode");
                String region = geoPluginJSON.getString("geoplugin_region");
                String regionAbbreviated = geoPluginJSON.getString("geoplugin_regionAbbreviated");

                double longitude = geoPluginJSON.getDouble("geoplugin_longitude");
                double latitude = geoPluginJSON.getDouble("geoplugin_latitude");
                Coordinates coordinates = new Coordinates(longitude, latitude);

                double distanceMiles = geoPluginJSON.getDouble("geoplugin_distanceMiles");
                double distanceKilometers = geoPluginJSON.getDouble("geoplugin_distanceKilometers");
                // =================================================================================

                // Store retrieved content into geoplugin info class
                geoPluginInfo = new GeoPluginInfo(
                        randCoordinates, coordinates, place,
                        countryCode, region, regionAbbreviated,
                        distanceMiles, distanceKilometers);

            } catch (java.io.IOException | JSONException | GeoPluginMaxIterationsException e) {
                e.printStackTrace();
            }

            return geoPluginInfo;
        }

        @Override
        protected void onPostExecute(GeoPluginInfo geoPluginInfo) {

            super.onPostExecute(geoPluginInfo);
        }
    }

}

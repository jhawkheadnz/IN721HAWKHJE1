package bit.hawkhje1.locationteleporterrand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

            GeoPluginAsyncTask geoPlugin = new GeoPluginAsyncTask();
            geoPlugin.execute();

            try {
                Coordinates randCoordinates = geoPlugin.get();
                tvLocation.setText(randCoordinates.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
                tvLocation.setText(e.getMessage());
            } catch (ExecutionException e) {
                e.printStackTrace();
                tvLocation.setText(e.getMessage());
            }
        }

    }

    // get the location coordinates
    public Coordinates getLocation(){

        Random rand = new Random();

        double longitude = rand.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE) + MIN_LONGITUDE;
        double latitude = rand.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE) + MIN_LATITUDE;

        return new Coordinates(longitude, latitude);
    }

    // async task for GeoPlugin
    public class GeoPluginAsyncTask extends AsyncTask<String, Integer, Coordinates>
    {
        private static final String GEOPLUGIN_ASYNCTASK = "GEOPLUGIN_ASYNC";

        @Override
        protected Coordinates doInBackground(String... params) {

            String geoPluginContents;

            // generate random coordinates
            Coordinates randCoordinates = getLocation();

            try {

                // output geoplugin async task to log
                Log.d(GEOPLUGIN_ASYNCTASK, "Checking Coordinates " + randCoordinates.toString());

                // format the URL
                String formattedURL = String.format(Globals.GEOPLUGIN_URL, randCoordinates.getLatitude(), randCoordinates.getLongitude());

                // create URL
                URL geoPluginURL = new URL(formattedURL);

                // create an HTTP URL Connection
                HttpURLConnection httpURLConnection = (HttpURLConnection)geoPluginURL.openConnection();

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
                while((currentLine = bufferedReader.readLine()) != null){
                    content.append(currentLine);
                }



            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return randCoordinates;
        }

        @Override
        protected void onPostExecute(Coordinates coordinates) {

            super.onPostExecute(coordinates);
        }
    }

}

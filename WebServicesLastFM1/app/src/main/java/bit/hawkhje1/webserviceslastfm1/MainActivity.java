package bit.hawkhje1.webserviceslastfm1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set api key
        String apiKey = "58384a2141a4b9737eacb9d0989b8a8c";

        // set content type
        String apiFormat = "json";

        // create AsyncTask
        LastFMAsyncTask lastFMAsyncTask = new LastFMAsyncTask();

        // execute task, and pass in required data
        lastFMAsyncTask.execute(apiKey, apiFormat, "chart.getTopArtists&limit=20");

    }

    private class LastFMAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String JSONOutput = null;

            // get the parameters passed
            String apiKey = params[0];
            String apiFormat = params[1];
            String apiMethod = params[2];

            // create the string format for the current api
            String apiURLFormat = "http://ws.audioscrobbler.com/2.0/?api_key=%s&format=%s&method=%s";

            // create the URL
            String apiURL = String.format(apiURLFormat, apiKey, apiFormat, apiMethod);

            // display URL to log
            Log.d("URL", apiURL);

            try {

                // get URL
                URL url = new URL(apiURL);

                // create httpURLconnection from URL
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // get the response code
                int responseCode = httpURLConnection.getResponseCode();

                StringBuilder strJSONContent = new StringBuilder();

                // check if the response code is OK (200)
                if (responseCode == 200) {

                    // Get Input Stream and BufferedReader data
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);

                    // loop through each line and add it to the StringBuilder
                    String currentLine;
                    while ((currentLine = reader.readLine()) != null) {
                        strJSONContent.append(currentLine);
                    }

                }

                // store JSON data into JSONOutput
                JSONOutput = strJSONContent.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return JSONOutput;

        }

        @Override
        protected void onPostExecute(String JSONInput) {

            // create array list to store artists
            ArrayList<String> topArtistsList = new ArrayList<>();

            try {
                // create JSON Object using main JSON data
                JSONObject lastFMJSONInput = new JSONObject(JSONInput);

                // get the artists object from main JSONObject
                JSONObject lastFMArtistsObject = lastFMJSONInput.getJSONObject("artists");

                // get artist array from artists JSONObject
                JSONArray topArtists = lastFMArtistsObject.getJSONArray("artist");

                // loop through each artist
                for(int i = 0; i < topArtists.length(); i++){

                    // get the current artist object
                    JSONObject currentArtist = (JSONObject)topArtists.get(i);

                    // get artists details
                    String name = currentArtist.getString("name");
                    int listeners = currentArtist.getInt("listeners");

                    // add selected items to list
                    topArtistsList.add(String.format("%s,%s", name, listeners));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // get listview from view
            ListView lvArtists = (ListView)findViewById(R.id.lvArtists);

            // create top artists array adapter
            TopArtistsArrayAdapter topArtistsArrayAdapter =
                    new TopArtistsArrayAdapter(MainActivity.this, R.layout.top_artists_list_item, topArtistsList);

            // set array adapter
            lvArtists.setAdapter(topArtistsArrayAdapter);
        }
    }

    // custom array adapter for top artists
    private class TopArtistsArrayAdapter extends ArrayAdapter<String> {

        private int layout;

        public TopArtistsArrayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);

            // get the resource defined when the constructed
            this.layout = resource;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // get layout inflater
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            // inflate and get view
            View newView = inflater.inflate(this.layout, null);

            // get objects from selected layout
            TextView txtArtistName = (TextView)newView.findViewById(R.id.tvArtistName);
            TextView txtArtistListeners = (TextView)newView.findViewById(R.id.tvArtistListener);

            // get the current artists in the list
            String currentArtist = getItem(position);

            // split the selected values
            String[] artistInfo = currentArtist.split(",");

            // get the values from the sliced string
            String name = artistInfo[0];
            String listeners = artistInfo[1];

            // set the values
            txtArtistName.setText(name);
            txtArtistListeners.setText(listeners);

            // return the new view
            return newView;

        }
    }

}

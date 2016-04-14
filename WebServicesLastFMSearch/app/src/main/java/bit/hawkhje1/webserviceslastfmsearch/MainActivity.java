package bit.hawkhje1.webserviceslastfmsearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

        // get button from view and set on click handler
        Button btnSearch = (Button)findViewById(R.id.btnSearchSimilar);
        OnBtnSearchClickHandler btnSearchClickHandler = new OnBtnSearchClickHandler();
        btnSearch.setOnClickListener(btnSearchClickHandler);
    }

    private class OnBtnSearchClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // create async task
            LastFMSearchAsyncTask lastFMSearchAsyncTask = new LastFMSearchAsyncTask();

            // set api key
            String apiKey = "58384a2141a4b9737eacb9d0989b8a8c";

            // set content type
            String apiFormat = "json";

            EditText txtSimilarArtist = (EditText)findViewById(R.id.editTextArtist);
            String artist = txtSimilarArtist.getText().toString();

            String apiMethod = String.format("artist.getSimilar&artist=%s&limit=10", artist);

            // create AsyncTask
            lastFMSearchAsyncTask.execute(apiKey, apiFormat, apiMethod);

        }
    }

    private class LastFMSearchAsyncTask extends AsyncTask<String, Void, String> {

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

            // get listview from view
            ListView lvResults = (ListView)findViewById(R.id.lvSimilarResults);

            // create list to store names
            List<String> strResults = new ArrayList<>();

            try {
                // get the main json output
                JSONObject searchJSON = new JSONObject(JSONInput);

                // get the similar artists
                JSONObject similarArtists = searchJSON.getJSONObject("similarartists");

                // get the listed artists
                JSONArray artists = similarArtists.getJSONArray("artist");

                // loop through each artist and add their name to the list
                for(int i = 0; i < artists.length(); i++){
                    JSONObject currentArtists = (JSONObject)artists.get(i);
                    String name = currentArtists.getString("name");
                    strResults.add(name);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // create adapter
            ArrayAdapter<String> resultsArrayAdapter =
                    new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, strResults);

            // set adapter
            lvResults.setAdapter(resultsArrayAdapter);

        }
    }

}

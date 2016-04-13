package bit.hawkhje1.webserviceslastfm1;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String API_URL = "http://ws.audioscrobbler.com/2.0/?method=%s&api_key=%s&method=%s";
    private final String API_KEY = "58384a2141a4b9737eacb9d0989b8a8c";
    private final String API_FORMAT = "json";

    private final int REQUEST_OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LastFMAsyncTask lastFMAsyncTask = new LastFMAsyncTask();
        lastFMAsyncTask.execute(API_URL, API_KEY);

    }

    private class LastFMAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String JSONOutput = null;

            String url = params[0];

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.appendQueryParameter("format", API_FORMAT);
            uriBuilder.appendQueryParameter("api_key", API_KEY);

            


            try {

                URL url = new URL(apiURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == REQUEST_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);

                    StringBuilder strJSONContent = new StringBuilder();

                    String currentLine = null;
                    while ((currentLine = reader.readLine()) != null) {
                        strJSONContent.append(currentLine);
                    }

                    JSONOutput = strJSONContent.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return JSONOutput;

        }

        @Override
        protected void onPostExecute(String s) {

            TextView tvJSONOutput = (TextView) findViewById(R.id.tvJSONOutput);
            tvJSONOutput.setText(s);

        }
    }

}

package bit.hawkhje1.webservicesbitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowTopArtist = (Button)findViewById(R.id.btnShowTopArtists);
        BtnShowTopArtistHandler btnShowTopArtistHandler = new BtnShowTopArtistHandler();
        btnShowTopArtist.setOnClickListener(btnShowTopArtistHandler);
    }

    // btn on click to show top artist
    class BtnShowTopArtistHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            // Create and Execute AsyncTask
            LoadTopArtistAsyncTask loadTopArtistAsyncTask = new LoadTopArtistAsyncTask();
            loadTopArtistAsyncTask.execute();
        }
    }

    class LoadTopArtistAsyncTask extends AsyncTask<String, Void, Bitmap>{

        private final int IMAGE_LARGE = 4; // get largest image

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap topArtistBitmap = null;

            try {

                // url to get the JSON result for the first artist in the top artists list
                URL url = new URL("http://ws.audioscrobbler.com/2.0/?api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json&method=chart.getTopArtists&limit=1");

                // get the http url connection
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                // get input stream from http url connection
                InputStream inputStream = httpURLConnection.getInputStream();

                // create stream reader passing in input stream
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                // create buffered reader to read content from url
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // create a builder to get the content from the url
                StringBuilder content = new StringBuilder();

                // populate content string builder
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                // get the json content
                String jsonOutput = content.toString();

                // get the main json data
                JSONObject jsonResult = new JSONObject(jsonOutput);

                // get the main artists object
                JSONObject artistMain = jsonResult.getJSONObject("artists");

                // get the artist array
                JSONArray artists = artistMain.getJSONArray("artist");

                // get the artists at the top of the list
                JSONObject topArist = (JSONObject)artists.get(0);

                // get the image array
                JSONArray topAristImages = (JSONArray)topArist.get("image");

                // set the size of the image to retrieve
                int imageSize = IMAGE_LARGE;

                // select the image to use
                JSONObject topArtistImageLarge = (JSONObject)topAristImages.get(imageSize);

                // get the image url
                String topArtistImageURL = topArtistImageLarge.getString("#text");

                // create image url
                URL imageURL = new URL(topArtistImageURL);

                // create image url connection
                HttpURLConnection imageURLConnection = (HttpURLConnection)imageURL.openConnection();

                // get image input stream
                InputStream imageInputStream = imageURLConnection.getInputStream();

                // create bitmap using bitmap factory to decode input stream
                topArtistBitmap = BitmapFactory.decodeStream(imageInputStream);

                // close input streams
                inputStream.close();
                imageInputStream.close();

            } catch (IOException ex){
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // return bitmap
            return topArtistBitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            // get image view from view
            ImageView imgTopArtist = (ImageView)findViewById(R.id.imgTopArtist);

            // display selected image
            imgTopArtist.setImageBitmap(bitmap);

        }
    }
}

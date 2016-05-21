package bit.hawkhje1.locationteleporterrand.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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

import bit.hawkhje1.locationteleporterrand.Interfaces.AsyncCallback;

/**
 * Async Task for Loading Flickr Content
 */
public class FlickrAsyncTask extends AsyncTask<String, Void, List<FlickrInfo>> {

    // for logcat
    private static final String FLICKR_ASYNCTASK_INFO = "FLICKR_ASYNCTASK_INFO";

    // Context to pass to progress dialog
    private Context context;

    // Progress dialog for display when loading content
    private ProgressDialog progressDialog;

    // Callback to pull data from AsyncTask externally
    private AsyncCallback<List<FlickrInfo>> callback;

    // Constructor passing in context
    public FlickrAsyncTask(Context context) {

        // setup vars
        this.context = context;
        this.progressDialog = new ProgressDialog(this.context);
        this.callback = null;

    }

    // Attach a callback listener
    public void setCallbackListener(AsyncCallback<List<FlickrInfo>> callback) {
        this.callback = callback;
    }

    // get image from a URL
    private Bitmap getImageFromURL(String _url) {

        Bitmap image = null;

        try {
            // create URL object
            URL url = new URL(_url);

            // open http url connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // get input stream from http url connection
            InputStream inputStream = httpURLConnection.getInputStream();

            // decode image from bitmap factory
            image = BitmapFactory.decodeStream(inputStream);

            // close input stream
            inputStream.close();

            // close http url connection
            httpURLConnection.disconnect();

        } catch (IOException e) {

            // display stack trace if error occurs
            e.printStackTrace();
        }

        // return image or null
        return image;
    }

    @Override
    protected List<FlickrInfo> doInBackground(String... params) {

        // get longitude value from parameters
        String latitude = params[0];

        // get latitude value from parameters
        String longitude = params[1];

        // get default radius from Globals
        String radius = Double.toString(Globals.Flickr.DEFAULT_FLICKR_RADIUS);

        // get default results per page value from Globals
        String perPage = Integer.toString(Globals.Flickr.RESULTS_PER_PAGE);

        // get default start page
        String page = Integer.toString(Globals.Flickr.DEFAULT_PAGE);

        // create flickr url
        String flickrURL = String.format(Globals.Flickr.FLICKR_URL, Globals.Flickr.FLICKR_APIKEY, latitude, longitude, radius, perPage, page);

        // display flickr url to log
        Log.d(FLICKR_ASYNCTASK_INFO, "Loading FlickrInfo From: " + flickrURL);

        // create a list to store all the flickr info we want to grab
        List<FlickrInfo> flickrInfoList = new ArrayList<>();
        String content = "";

        try {

            // create flickr url object
            URL url = new URL(flickrURL);

            // open connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // get input stream from connection
            InputStream inputStream = httpURLConnection.getInputStream();

            // create an input stream reader to read info
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // create a buffered reader to create readable info
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // create flickrContent string builder to piece together content received from Flickr
            StringBuilder flickrContent = new StringBuilder();

            // loop through each line and add to flickrContent
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                flickrContent.append(line);
            }

            // assign received data to content string
            content = flickrContent.toString();

            // display content received from Flickr
            Log.d(FLICKR_ASYNCTASK_INFO, "Content received from Flickr: " + content);

            // close input stream
            inputStream.close();

            // disconnect connection
            httpURLConnection.disconnect();

        } catch (IOException e) {

            // print out info if exception is thrown
            e.printStackTrace();
        }

        try {

            // convert content into a JSON Object
            JSONObject jsonObject = new JSONObject(content);

            // get the photos JSON Object
            JSONObject photos = jsonObject.getJSONObject("photos");

            // get the photo JSON Array
            JSONArray photoCollection = photos.getJSONArray("photo");

            // loop through JSON Array and create FlickrInfo instances
            // compile image URL and get image from Flickr
            for (int i = 0; i < photoCollection.length(); i++) {

                // create a JSONObject of the current photo item
                JSONObject currentPhoto = (JSONObject) photoCollection.get(i);

                // load content from JSONObject
                String id = currentPhoto.getString("id");
                String owner = currentPhoto.getString("owner");
                String secret = currentPhoto.getString("secret");
                String server = currentPhoto.getString("server");
                int farm = currentPhoto.getInt("farm");
                String title = currentPhoto.getString("title");
                boolean isPublic = (currentPhoto.getInt("ispublic") == 1);
                boolean isFriend = (currentPhoto.getInt("isfriend") == 1);
                boolean isFamily = (currentPhoto.getInt("isfamily") == 1);

                // create intance of flickrInfo for current JSONObject
                FlickrInfo flickrInfo = new FlickrInfo(id, owner, secret, server, farm, title, isPublic, isFriend, isFamily);

                // get the flickr image URL
                String imageURL = flickrInfo.constructImageURL();

                // output image url to log
                Log.d(FLICKR_ASYNCTASK_INFO, "Image URL: " + imageURL);

                // get the image from the imageURL
                Bitmap image = getImageFromURL(imageURL);

                // add the image to the flickrInfo object
                flickrInfo.setImage(image);

                // add flickrInfo to flickrInfo list
                flickrInfoList.add(flickrInfo);


            }

        } catch (JSONException e) {

            // output stack trace if exception is raised
            e.printStackTrace();
        }

        // return the flickr list
        return flickrInfoList;
    }

    @Override
    protected void onPostExecute(List<FlickrInfo> flickrInfoList) {
        super.onPostExecute(flickrInfoList);

        // if the callback object is set, execute run method
        if (callback != null)
            callback.run(flickrInfoList);

        // if the progress dialog is showing dismiss the dialog
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // just before execute display progress dialog
        progressDialog.setTitle(Globals.Flickr.FLICKR_PROGRESS_DIALOG_TITLE);
        progressDialog.show();
    }
}

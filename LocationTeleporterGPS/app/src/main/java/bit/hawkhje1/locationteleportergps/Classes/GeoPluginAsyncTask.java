package bit.hawkhje1.locationteleportergps.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import bit.hawkhje1.locationteleportergps.Interfaces.AsyncCallback;

/**
 * Async Task for Grabbing GeoPlugin Content
 */
public class GeoPluginAsyncTask extends AsyncTask<Double, Void, GeoPluginInfo> {

    // for log cat
    private static final String GEOPLUGIN_ASYNCTASK_INFO = "GEOPLUGIN_ASYNC_INFO";

    // reference context for progress dialog
    private Context context;

    // create progress dialog
    private ProgressDialog progressDialog;

    // set callback interface
    private AsyncCallback<GeoPluginInfo> callback;

    // construct geoplugin and pass in context
    public GeoPluginAsyncTask(Context context){

        // set callback to null by default
        this.callback = null;

        // assign context
        this.context = context;

        // create progress dialog
        this.progressDialog = new ProgressDialog(this.context);

        // set title for progress dialog
        this.progressDialog.setTitle(Globals.GeoPlugin.PROGRESS_DIALOG_TITLE);
    }

    /**
     * Sets the callback listener for GeoPluginAsyncTask
     * @param callback Callback listener
     */
    public void setAsyncCallbackListener(AsyncCallback<GeoPluginInfo> callback){

        // attach callback listener
        this.callback = callback;
    }

    @Override
    protected GeoPluginInfo doInBackground(Double... params) {

        // create an empty string
        String geoPluginContent = "";

        // randomly generate latitude value
        double latitude = params[0];

        // randomly generate longitude value
        double longitude = params[1];

        // construct url with randomly generated coordinates
        String geoPluginURL = String.format(Globals.GeoPlugin.GEOPLUGIN_URL, latitude, longitude);

        // output geoplugin url to log
        Log.d(GEOPLUGIN_ASYNCTASK_INFO, "GeoPlugin URL: " + geoPluginURL);

        // check and see if geoplugin content exists for random coordinates
        try {

            // create URL
            URL url = new URL(geoPluginURL);

            // open connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // get input stream from connection
            InputStream inputStream = httpURLConnection.getInputStream();

            // create input stream reader for reading input stream
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // create buffered read to read character input stream
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // create string builder to build string data from buffered reader
            StringBuilder siteContent = new StringBuilder();

            // loop through each line and add to string builder
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                siteContent.append(line);
            }

            // get output from the string builder
            geoPluginContent = siteContent.toString();

        } catch (Exception ex) {

            // if exception is raised print out stack trace
            ex.printStackTrace();

        }

        // when a valid location is found return a geoplugininfo instance from JSON
        return GeoPluginInfo.fromJSON(geoPluginContent, latitude, longitude);

    }

    @Override
    protected void onPostExecute(GeoPluginInfo geoPluginInfo) {
        super.onPostExecute(geoPluginInfo);

        // if the callback listener has been set then execute run
        if(callback != null)
            this.callback.run(geoPluginInfo);

        // if the progress dialog is showing, dismiss it
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // just before execution occurs show progress dialog
        progressDialog.show();
    }
}

package bit.hawkhje1.locationteleportergps.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bit.hawkhje1.locationteleportergps.Interfaces.AsyncCallback;

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private static final String IMAGE_ASYNCTASK = "IMAGE_ASYNC";

    private Context context;
    private AsyncCallback<Bitmap> callback;
    private ProgressDialog progressDialog;
    private String progressTitle;

    public GetImageAsyncTask(Context context){
        this.callback = null;
        this.context = context;

        this.progressDialog = new ProgressDialog(this.context);
        this.progressTitle = Globals.ImageAsyncTask.DEFAULT_PROGRESS_TITLE;
        this.progressDialog.setTitle(this.progressTitle);
    }

    public void setCallbackListener(AsyncCallback<Bitmap> callback) {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String imageURL = params[0];

        Log.d(IMAGE_ASYNCTASK, "Param Length: " + params.length);

        if(params.length == Globals.ImageAsyncTask.SET_TITLE_PARAMETER) {
            progressTitle = params[1];
            Log.d(IMAGE_ASYNCTASK, "Custom Download Title: " + progressTitle);
        }

        progressDialog.setTitle(progressTitle);

        Log.d(IMAGE_ASYNCTASK, "Loading Image: " + imageURL);

        Bitmap image = null;

        try {

            URL url = new URL(imageURL);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            image = BitmapFactory.decodeStream(inputStream);

            inputStream.close();

            httpURLConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(callback != null)
            callback.run(bitmap);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}

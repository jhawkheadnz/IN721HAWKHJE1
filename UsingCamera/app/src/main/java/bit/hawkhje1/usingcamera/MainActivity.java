package bit.hawkhje1.usingcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private File photoFile;

    private Button btnTakePhoto;
    private ImageView imgPicture1;
    private ImageView imgPicture2;
    private ImageView imgPicture3;
    private ImageView imgPicture4;

    private int IMAGE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup view controls
        Button btnTakePicture = (Button)findViewById(R.id.btnTakePicture);

        // setup controls
        imgPicture1 = (ImageView)findViewById(R.id.imageView);
        imgPicture2 = (ImageView)findViewById(R.id.imageView2);
        imgPicture3 = (ImageView)findViewById(R.id.imageView3);
        imgPicture4 = (ImageView)findViewById(R.id.imageView4);

        // setup handlers
        OnBtnClick_TakePicture onBtnClick_takePicture = new OnBtnClick_TakePicture();
        btnTakePicture.setOnClickListener(onBtnClick_takePicture);

    }

    private class OnBtnClick_TakePicture implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // launch camera intent
            startCameraIntent();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_RESULT_CODE){

            if(resultCode == RESULT_OK){

                // get the image file's path
                String realFilePath = photoFile.getPath();

                // get bitmap
                Bitmap userPhotoBitmap = BitmapFactory.decodeFile(realFilePath);

                imgPicture1.setImageBitmap(userPhotoBitmap);
                imgPicture2.setImageBitmap(userPhotoBitmap);
                imgPicture3.setImageBitmap(userPhotoBitmap);
                imgPicture4.setImageBitmap(userPhotoBitmap);
            }

        }else{

            // Display error message...
            Toast.makeText(MainActivity.this, "No Photo Saved. Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void startCameraIntent(){

        // create time stamped file
        this.photoFile = createTimeStampedFile();

        // create uri from file
        Uri photoUri = Uri.fromFile(photoFile);

        // create an intent to launch the camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // setup intent information
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        // lanuch camera intent
        startActivityForResult(cameraIntent, IMAGE_RESULT_CODE);

    }

    private File createTimeStampedFile(){

        // get the external storage public directory for pictures
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // create  a directory for the images
        File imageStorageDirectory = new File(imageRootPath, "cameraDemo1");

        // check if the directory exists, if it doesn't exits then create it
        if(!imageStorageDirectory.exists()){
            imageStorageDirectory.mkdirs();
        }

        // get the current time stamp
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yymmdd_HHmmss");
        Date currentTime = new Date();

        // convert timestamp into a formatted string
        String timeStamp = timestampFormat.format(currentTime);

        // create image filename
        String photoFilename = String.format("img_%s.jpg", timeStamp);

        // return new file with image
        return new File(imageStorageDirectory.getPath() + File.separator + photoFilename);

    }

}

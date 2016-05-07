package bit.hawkhje1.locationteleporterrand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TextView tvLatitude;
    private TextView tvLocation;
    private TextView tvLongitude;
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

    // button onclick handler
    public class BtnClickTeleportHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            
        }
    }
}

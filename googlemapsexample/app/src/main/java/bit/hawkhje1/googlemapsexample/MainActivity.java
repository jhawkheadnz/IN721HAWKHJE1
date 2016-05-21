package bit.hawkhje1.googlemapsexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    LatLng DunedinLatLng;
    double DUNEDIN_LAT = -45;
    double DUNEDIN_LNG = 170;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DunedinLatLng = new LatLng(DUNEDIN_LAT, DUNEDIN_LNG);

        // get support map fragment
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // bind the callback for when the map is ready
        mapFragment.getMapAsync(new MapCallbackClass());
    }

    public class MapCallbackClass implements OnMapReadyCallback {

        public void onMapReady(GoogleMap googleMap) {

            // get google map
            mMap = googleMap;

            // set marker for Dunedin
            mMap.addMarker(new MarkerOptions().position(DunedinLatLng).title("Dunedin!!"));

            // center the camera on the location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(DunedinLatLng));
        }

    }
}

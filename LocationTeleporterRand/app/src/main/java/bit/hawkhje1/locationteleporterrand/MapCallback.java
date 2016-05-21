package bit.hawkhje1.locationteleporterrand;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Work on 5/21/2016.
 */
public class MapCallback implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng latLng;
    private String markerText;

    public MapCallback(GoogleMap googleMap, LatLng latLng, String markerText){
        this.latLng = latLng;
        this.map = googleMap;
        this.markerText = markerText;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;

        this.map.moveCamera(CameraUpdateFactory.newLatLng(this.latLng));

        this.map.addMarker(new MarkerOptions().position(latLng).title(markerText));

    }
}

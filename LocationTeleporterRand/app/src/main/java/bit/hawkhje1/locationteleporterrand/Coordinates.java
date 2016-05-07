package bit.hawkhje1.locationteleporterrand;

/**
 * Created by Work on 5/7/2016.
 */
public class Coordinates {

    private double longitude;
    private double latitude;

    public Coordinates(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

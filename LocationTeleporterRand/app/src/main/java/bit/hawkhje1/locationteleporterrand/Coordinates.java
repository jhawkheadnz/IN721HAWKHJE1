package bit.hawkhje1.locationteleporterrand;

/**
 * Gets and sets Geo Coordinates
 */
public class Coordinates {

    // private coordinates
    private double longitude;
    private double latitude;

    // set coordinates
    public Coordinates(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // get longitude value
    public double getLongitude() {
        return longitude;
    }

    // set longitude value
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // get latitude value
    public double getLatitude() {
        return latitude;
    }

    // set latitude value
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

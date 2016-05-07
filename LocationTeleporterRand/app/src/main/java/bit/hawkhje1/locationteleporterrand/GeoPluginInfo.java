package bit.hawkhje1.locationteleporterrand;

/**
 * Created by Work on 5/7/2016.
 */
public class GeoPluginInfo {

    private Coordinates originalCoordinates;
    private Coordinates locationCoordinates;
    private String place;
    private String countryCode;
    private String region;
    private String regionAbbreviated;
    private double distanceMiles;
    private double distanceKilometers;

    public GeoPluginInfo() {}

    public GeoPluginInfo(
            Coordinates originalCoordinates,
            Coordinates locationCoordinates,
            String place, String countryCode,
            String region, String regionAbbreviated,
            double distanceMiles, double distanceKilometers) {

        this.originalCoordinates = originalCoordinates;
        this.locationCoordinates = locationCoordinates;
        this.place = place;
        this.countryCode = countryCode;
        this.region = region;
        this.regionAbbreviated = regionAbbreviated;
        this.distanceMiles = distanceMiles;
        this.distanceKilometers = distanceKilometers;
    }

    public Coordinates getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(Coordinates locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionAbbreviated() {
        return regionAbbreviated;
    }

    public void setRegionAbbreviated(String regionAbbreviated) {
        this.regionAbbreviated = regionAbbreviated;
    }

    public double getDistanceMiles() {
        return distanceMiles;
    }

    public void setDistanceMiles(double distanceMiles) {
        this.distanceMiles = distanceMiles;
    }

    public double getDistanceKilometers() {
        return distanceKilometers;
    }

    public void setDistanceKilometers(double distanceKilometers) {
        this.distanceKilometers = distanceKilometers;
    }

    @Override
    public String toString() {
        return "GeoPluginInfo{" +
                "locationCoordinates=" + locationCoordinates +
                ", place='" + place + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", region='" + region + '\'' +
                ", regionAbbreviated='" + regionAbbreviated + '\'' +
                ", distanceMiles=" + distanceMiles +
                ", distanceKilometers=" + distanceKilometers +
                '}';
    }

    public Coordinates getOriginalCoordinates() {
        return originalCoordinates;
    }

    public void setOriginalCoordinates(Coordinates originalCoordinates) {
        this.originalCoordinates = originalCoordinates;
    }
}

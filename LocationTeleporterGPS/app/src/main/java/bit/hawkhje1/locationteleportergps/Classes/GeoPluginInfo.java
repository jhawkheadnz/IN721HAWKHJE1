package bit.hawkhje1.locationteleportergps.Classes;

/*
 *{"geoplugin_place":"Dunedin",
 * "geoplugin_countryCode":"NZ",
 * "geoplugin_region":"Otago",
 * "geoplugin_regionAbbreviated":"F7",
 * "geoplugin_latitude":"-45.8741600",
 * "geoplugin_longitude":"170.5036100",
 * "geoplugin_distanceMiles":0.32,
 * "geoplugin_distanceKilometers":0.52}
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * GeoPluginInfo class for holding GeoPlugin Info
 */
public class GeoPluginInfo {

    // for logcat
    private static final String GEOPLUGIN_INFO = "GEOPLUGIN_INFO";

    // geoplugin properties
    private String place;
    private String countryCode;
    private String region;
    private String regionAbbreviated;
    private double latitude;
    private double longitude;
    private double distanceMile;
    private double distanceKilometers;
    private double originalLongitude;
    private double originalLatitude;

    // default geoplugin constructor
    public GeoPluginInfo() {}

    // override constructor
    public GeoPluginInfo(double originalLatitude, double originalLongitude,
                         double latitude, double longitude,
                         String place, String countryCode, String region,
                         String regionAbbreviated, double distanceMile, double distanceKilometers) {

        this.originalLatitude = originalLatitude;
        this.originalLongitude = originalLongitude;
        this.place = place;
        this.countryCode = countryCode;
        this.region = region;
        this.regionAbbreviated = regionAbbreviated;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceMile = distanceMile;
        this.distanceKilometers = distanceKilometers;
    }

    /**
     * Gets original latitude value (one set for searching)
     * @return Searched Latitude Value
     */
    public double getOriginalLatitude() {
        return originalLatitude;
    }

    /**
     * Sets original search latitude value
     * @param originalLatitude Search Latitude Value
     */
    public void setOriginalLatitude(double originalLatitude) {
        this.originalLatitude = originalLatitude;
    }

    /**
     * Gets original longitude value (one set for searching)
     * @return Searched Longitude Value
     */
    public double getOriginalLongitude() {
        return originalLongitude;
    }

    /**
     * Sets Original Search Longitude Value
     * @param originalLongitude Search Longitude Value
     */
    public void setOriginalLongitude(double originalLongitude) {
        this.originalLongitude = originalLongitude;
    }

    /**
     * Converts JSON into GeoPluginInfo, and passes in original search values
     * @param json GeoPlugin JSON Content
     * @param originalLatitude Latitude Search Value
     * @param originalLongitude Longitude Search Value
     * @return Returns GeoPluginInfo object created from JSON
     */
    public static GeoPluginInfo fromJSON(String json, double originalLatitude, double originalLongitude){

        // create null GeoPluginInfo object
        GeoPluginInfo info = null;

        try {

            // convert the JSON string into a JSONOBject
            JSONObject geoPluginJSON = new JSONObject(json);

            // =========================== Get Content from JSON ===============================
            String place = geoPluginJSON.getString("geoplugin_place");
            String countryCode = geoPluginJSON.getString("geoplugin_countryCode");
            String region = geoPluginJSON.getString("geoplugin_region");
            String regionAbbreviated = geoPluginJSON.getString("geoplugin_regionAbbreviated");

            double geoplugin_longitude = geoPluginJSON.getDouble("geoplugin_longitude");
            double geoplugin_latitude = geoPluginJSON.getDouble("geoplugin_latitude");

            double distanceMiles = geoPluginJSON.getDouble("geoplugin_distanceMiles");
            double distanceKilometers = geoPluginJSON.getDouble("geoplugin_distanceKilometers");
            // =================================================================================

            // Store retrieved content into geoplugin info class
            info = new GeoPluginInfo(originalLatitude, originalLongitude, geoplugin_latitude, geoplugin_longitude, place,
                    countryCode, region, regionAbbreviated, distanceMiles, distanceKilometers);

        } catch (JSONException e) {
            // if exception is thrown show stack trace
            e.printStackTrace();
        }

        // return the new geoplugininfo object
        return info;
    }

    /**
     * Gets the Place (City?)
     * @return Place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Sets the Place
     * @param place Place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Gets the country code
     * @return Country Code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the Country Code
     * @param countryCode Country Code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the region
     * @return Region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region
     * @param region Region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the abbreviated region
     * @return Region Abbreviated
     */
    public String getRegionAbbreviated() {
        return regionAbbreviated;
    }

    /**
     * Sets the Region Abbreviated Value
     * @param regionAbbreviated Region Abbreviated
     */
    public void setRegionAbbreviated(String regionAbbreviated) {
        this.regionAbbreviated = regionAbbreviated;
    }

    /**
     * Gets the latitude location of the nearest place
     * @return Nearest Place Latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude location of the nearest place
     * @param latitude Nearest Place Latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude location of the nearest place
     * @return Longitude Location of Nearest Place
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude location of the nearest place
     * @param longitude Nearest Place Longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets Distance in Miles
     * @return Distance in Miles
     */
    public double getDistanceMile() {
        return distanceMile;
    }

    /**
     * Sets Distance in Miles
     * @param distanceMile Distance in Miles
     */
    public void setDistanceMile(double distanceMile) {
        this.distanceMile = distanceMile;
    }

    /**
     * Gets Distance in Kilometers
     * @return Distance in Kilometers
     */
    public double getDistanceKilometers() {
        return distanceKilometers;
    }

    /**
     * Sets Distance in Kilometers
     * @param distanceKilometers Distance in Kilometers
     */
    public void setDistanceKilometers(double distanceKilometers) {
        this.distanceKilometers = distanceKilometers;
    }

    @Override
    public String toString() {
        return "GeoPluginInfo{" +
                "place='" + place + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", region='" + region + '\'' +
                ", regionAbbreviated='" + regionAbbreviated + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceMile=" + distanceMile +
                ", distanceKilometers=" + distanceKilometers +
                '}';
    }
}

package bit.hawkhje1.locationteleportergps.Classes;

import java.util.Random;

/**
 * Teleportation Application Globals
 */
public class Globals {

    // global constants
    public static double MIN_LONGITUDE = -180;
    public static double MAX_LONGITUDE = 180;
    public static double MIN_LATITUDE = -90;
    public static double MAX_LATITUDE = 90;

    /**
     * Location Manager Constants
     */
    public static class LocationManager {
        public static int REQUEST_CODE = 100;

        public static int MIN_UPDATE_TIME = 2000; // 5000
        public static int MIN_DISTANCE = 10; //10
    }

    /**
     * GeoPlugin Constants
     */
    public static class GeoPlugin {

        public static String GEOPLUGIN_URL = "http://geoplugin.net/extras/location.gp?lat=%s&long=%s&format=json";
        public static String GEOPLUGIN_NOJSONOUTPUT = "[[]]";

        public static String PROGRESS_DIALOG_TITLE = "Retrieving Data From GeoPlugin...";

        public static double randomDouble(double min, double max) {
            Random rand = new Random();
            return rand.nextDouble() * (max - min) + min;
        }
    }

    /**
     * Flickr Constants
     */
    public static class Flickr {

        /**
         * Flickr Image URL
         * https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
         */
        public static final String BASE_IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

        /**
         * Default Image Size to retrevie from Flickr
         */
        public static final String DEFAULT_IMAGE_SIZE = "m";

        /**
         * Default results per page
         */
        public static final int RESULTS_PER_PAGE = 3;

        /**
         * Default page
         */
        public static final int DEFAULT_PAGE = 1;

        /**
         * 1, APIKEY
         * 2, Latitude
         * 3, Longitude
         * 4, Radius
         * 5, Results Per Page
         * 6, Page
         */
        public static String FLICKR_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&lat=%s&lon=%s&radius=%s&format=json&nojsoncallback=1&per_page=%s&page=%s";

        /**
         * Default FlickrAPI Key
         */
        public static String FLICKR_APIKEY = "eda41a123d459be0f85276d37290651e";

        /**
         * Flickr Search Radius
         */
        public static final double DEFAULT_FLICKR_RADIUS = 5;

        /**
         * Flickr Progress Dialog Title
         */
        public static final String FLICKR_PROGRESS_DIALOG_TITLE = "Retrieving Info From Flickr...";

        public static final String FLICKR_LOADING_IMAGE_TITLE = "Loading Flickr Image...";
    }

    public static class ImageAsyncTask {

        public static final String DEFAULT_PROGRESS_TITLE = "Loading Image...";
        public static final int SET_TITLE_PARAMETER = 2;

    }
}

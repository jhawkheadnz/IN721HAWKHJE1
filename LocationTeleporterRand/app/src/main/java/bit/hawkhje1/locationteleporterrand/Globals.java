package bit.hawkhje1.locationteleporterrand;

import java.util.Random;

public class Globals {

    // default coordinate values
    public static double MIN_LONGITUDE = -90;
    public static double MAX_LONGITUDE = 90;
    public static double MIN_LATITUDE = -180;
    public static double MAX_LATITUDE = 180;

    // default geoplugin url
    public static String GEOPLUGIN_URL = "http://www.geoplugin.net/extras/location.gp?lat=%s&long=%s&format=json";

    // geoplugin timeout
    public static int MAX_GEOPLUGIN_ITERATIONS = 100;

    // randomizer for double
    public static double RandomDouble(double min, double max){

        // create random instance
        Random rand = new Random();

        // generate random numbers
        double randDouble = rand.nextDouble() * (max - min) + min;

        // return output
        return randDouble;

    }

}

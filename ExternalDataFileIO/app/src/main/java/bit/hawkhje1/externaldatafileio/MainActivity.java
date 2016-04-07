package bit.hawkhje1.externaldatafileio;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get cities filename
        String citiesFilename = "cities.txt";

        // get listView object
        ListView lvCities = (ListView)findViewById(R.id.lvCities);

        // get list of cities
        List<String> cities = getCities(citiesFilename);

        // create an array adapter for cities
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);

        // set array adapter for cities listview
        lvCities.setAdapter(citiesAdapter);

    }

    private List<String> getCities(String citiesFileName){

        // create cities list
        List<String> cities = new ArrayList<>();

        // get assets manager
        AssetManager am = getAssets();

        InputStream inputStream = null;

        try {

            // get file from assets folder as input stream
            inputStream = am.open(citiesFileName);

            // create input stream reader and pass in input stream
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            // create buffered reader for buffered output
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String city;
            // go through each line in the txt file and add each city
            while((city = bufferedReader.readLine()) != null){
                cities.add(city);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }


}

package bit.hawkhje1.externaldatasqlite2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String DB_NAME = "dbPlaces";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create database
        SQLiteDatabase dbCountries = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        // create table
        String createTblCities = "CREATE TABLE tblCities (" +
                "    cityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "    cityName TEXT NOT NULL, " +
                "    countryName TEXT NOT NULL);";

        // execute sql query
        dbCountries.execSQL(createTblCities);

        // populate table
        List<City> cities = new ArrayList<>();
        cities.add(new City("Amsterdam", "The Netherlands"));
        cities.add(new City("Berlin", "Germany"));
        cities.add(new City("Cairo", "Egypt"));
        cities.add(new City("Dunedin", "New Zealand"));

        // formatted string
        String sqlFormat = "INSERT INTO (cityName, countryName) VALUES ('%s', '%s')";

        for(City city : cities){
            String sql = String.format(sqlFormat, city.getName(), city.getCountry());
            dbCountries.execSQL(sql);
        }

        // display in list view
        String selectAll = "SELECT cityName, countryName FROM tblCities";
        Cursor recordSet = dbCountries.rawQuery(selectAll, null);

        int recordCount = recordSet.getCount();

        String[] citiesStringArray = new String[recordCount];

        int cityNameIndex = recordSet.getColumnIndex("cityName");
        int countryNameIndex = recordSet.getColumnIndex("countryName");

        // move to the first record
        recordSet.moveToFirst();

        for(int i = 0; i < recordCount; i++){
            String cityName = recordSet.getString(cityNameIndex);
            String countryName = recordSet.getString(countryNameIndex);
            citiesStringArray[i] = String.format("%s, %s", cityName, countryName);
            recordSet.moveToNext();
        }
        recordSet.close();

        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, citiesStringArray);
        ListView lvCities = (ListView)findViewById(R.id.lvCities);
        lvCities.setAdapter(citiesAdapter);
    }

    private class City {

        private String name;

        private String country;

        public City(String name, String country){
            this.name = name;
            this.country = country;
        }

        public String getName(){ return this.name; }

        public String getCountry(){ return this.country; }
    }

}

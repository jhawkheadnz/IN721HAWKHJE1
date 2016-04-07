package bit.hawkhje1.externaldatasqlite2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spCountries;
    private ListView lvCities;
    private CitiesDBManager citiesDBManager;
    private String DB_NAME = "dbPlaces";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCities = (ListView)findViewById(R.id.lvCities);

        InitDatabase();
        InitViewObjects();

    }

    private void InitViewObjects(){

        Button btnPopulateTable = (Button)findViewById(R.id.btnPopulateTable);
        BtnPopulateTableHandler btnPopulateTableHandler = new BtnPopulateTableHandler();
        btnPopulateTable.setOnClickListener(btnPopulateTableHandler);

        // get a list of items from the database
        List<City> citiesFromTblCities = citiesDBManager.GetCities();
        ArrayAdapter<City> citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, citiesFromTblCities);
        lvCities.setAdapter(citiesAdapter);

        // spCountries
        spCountries = (Spinner)findViewById(R.id.spCountries);
        List<String> countries = citiesDBManager.getCountries();
        ArrayAdapter<String> countriesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        spCountries.setAdapter(countriesArrayAdapter);

        Button btnUpdateCountry = (Button)findViewById(R.id.btnUpdateCountries);
        BtnUpdateCountryHandler btnUpdateCountryHandler = new BtnUpdateCountryHandler();
        btnUpdateCountry.setOnClickListener(btnUpdateCountryHandler);

    }

    private void InitDatabase(){
        // create database
        SQLiteDatabase dbCountries = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        // create tblCities if it doesn't exist
        citiesDBManager = new CitiesDBManager(dbCountries);
        citiesDBManager.createTblCities();
    }

    private List<City> getCitiesList(){

        List<City> cities = new ArrayList<>();

        cities.add(new City("Amsterdam", "The Netherlands"));
        cities.add(new City("Berlin", "Germany"));
        cities.add(new City("Cairo", "Egypt"));
        cities.add(new City("Dunedin", "New Zealand"));
        cities.add(new City("Auckland", "New Zealand"));
        cities.add(new City("Wellington", "New Zealand"));
        cities.add(new City("Christchurch", "New Zealand"));
        cities.add(new City("Sydney", "Australia"));
        cities.add(new City("Melbourne", "Australia"));
        cities.add(new City("Adalade", "Australia"));
        cities.add(new City("Brisbane", "Australia"));

        return cities;
    }

    private class City {

        private int id;
        private String name;
        private String country;

        public City(String name, String country){
            this.name = name;
            this.country = country;
        }

        public City(int id, String name, String country){
            this(name, country);
            this.id = id;
        }

        public int getId(){ return this.id; }
        public String getName(){ return this.name; }
        public String getCountry(){ return this.country; }

        @Override
        public String toString(){ return String.format("%s, %s", name, country); }
    }

    private class CitiesDBManager {

        private SQLiteDatabase sqLiteDatabase;

        public CitiesDBManager(SQLiteDatabase sqLiteDatabase){
            this.sqLiteDatabase = sqLiteDatabase;
        }

        public void createTblCities(){
            // create table
            String createTblCities = "CREATE TABLE IF NOT EXISTS tblCities (" +
                    "    cityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    cityName TEXT NOT NULL, " +
                    "    countryName TEXT NOT NULL);";

            // execute sql query
            this.sqLiteDatabase.execSQL(createTblCities);
        }

        public void addCityToTblCities(City city){

            String sqlFormat = "INSERT INTO tblCities (cityName, countryName) VALUES('%s', '%s');";
            String sql = String.format(sqlFormat, city.getName(), city.getCountry());

            // execute the sql query
            this.sqLiteDatabase.execSQL(sql);

        }

        public void addCitiesToTblCities(List<City> cities){

            // go through each city and add city to table
            for(City city : cities){
                addCityToTblCities(city);
            }

        }

        public List<City> getCitiesFromSQL(String sql){
            Cursor recordSet = this.sqLiteDatabase.rawQuery(sql, null);
            int recordCount = recordSet.getCount();

            City[] citiesArray = new City[recordCount];

            int idIndex = recordSet.getColumnIndex("cityID");
            int cityNameIndex = recordSet.getColumnIndex("cityName");
            int countryNameIndex = recordSet.getColumnIndex("countryName");

            // move to the first record
            recordSet.moveToFirst();

            for(int i = 0; i < recordCount; i++){

                int id = recordSet.getInt(idIndex);
                String cityName = recordSet.getString(cityNameIndex);
                String countryName = recordSet.getString(countryNameIndex);

                citiesArray[i] = new City(id, cityName, countryName);

                // move to the next item in the recordset
                recordSet.moveToNext();
            }

            return Arrays.asList(citiesArray);
        }

        public List<City> getCitiesFromCountry(String country){

            String sql = String.format("SELECT cityID, cityName, countryName FROM tblCities WHERE countryName = %s", country);
            return getCitiesFromSQL(sql);

        }

        public List<String> getCountries(){

            String sql = "SELECT DISTINCT countryName FROM tblCities";

            Cursor recordSet = this.sqLiteDatabase.rawQuery(sql, null);
            int recordCount = recordSet.getCount();

            String[] countries = new String[recordCount];

            int countryNameIndex = recordSet.getColumnIndex("countryName");

            // move to the first record
            recordSet.moveToFirst();

            for(int i = 0; i < recordCount; i++){

                String countryName = recordSet.getString(countryNameIndex);
                countries[i] = countryName;

                // move to the next item in the recordset
                recordSet.moveToNext();
            }

            return Arrays.asList(countries);
        }

        public List<City> GetCities() {

            // display in list view
            String selectAll = "SELECT cityID, cityName, countryName FROM tblCities";

            return getCitiesFromSQL(selectAll);
            /*Cursor recordSet = this.sqLiteDatabase.rawQuery(selectAll, null);

            int recordCount = recordSet.getCount();

            City[] citiesStringArray = new City[recordCount];

            int idIndex = recordSet.getColumnIndex("cityID");
            int cityNameIndex = recordSet.getColumnIndex("cityName");
            int countryNameIndex = recordSet.getColumnIndex("countryName");

            // move to the first record
            recordSet.moveToFirst();

            for(int i = 0; i < recordCount; i++){

                int id = recordSet.getInt(idIndex);
                String cityName = recordSet.getString(cityNameIndex);
                String countryName = recordSet.getString(countryNameIndex);

                citiesStringArray[i] = new City(id, cityName, countryName);

                // move to the next item in the recordset
                recordSet.moveToNext();
            }

            // close record set
            recordSet.close();

            // return list of items
            return Arrays.asList(citiesStringArray);*/
        }
    }

    private class BtnPopulateTableHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button btnPopulateTable = (Button)findViewById(R.id.btnPopulateTable);

            // populate tbl cities
            List<City> cities = getCitiesList();
            citiesDBManager.addCitiesToTblCities(cities);

            btnPopulateTable.setVisibility(View.INVISIBLE);
        }
    }
    private class BtnUpdateCountryHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String selectedCountry = spCountries.getSelectedItem().toString();

            List<City> cities = citiesDBManager.getCitiesFromCountry(selectedCountry);
            ArrayAdapter<City> filteredCitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
            lvCities.setAdapter(filteredCitiesAdapter);

        }
    }
}

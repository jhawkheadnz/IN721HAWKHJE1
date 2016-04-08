package bit.hawkhje1.formattedfilejson;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "JSONAPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get list view from view
        ListView lvEvents = (ListView)findViewById(R.id.lvEvents);

        // JSON filename
        String dunedinEventsFilename = "dunedin_events.json";

        // get text from file
        String JSONOutput = getTextFromFile(dunedinEventsFilename);

        // get data from
        ArrayList<DunedinEventInfo> dunedinEventList = parseEventsListFromJSON(JSONOutput);

        // creating an array adapter for the events list
        ArrayAdapter<DunedinEventInfo> eventsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dunedinEventList);

        // set the adapter for list view
        lvEvents.setAdapter(eventsAdapter);

        // create and set item click handler
        EventsListViewOnItemClickHandler lvOnItemClickListener = new EventsListViewOnItemClickHandler();
        lvEvents.setOnItemClickListener(lvOnItemClickListener);

    }

    private String getTextFromFile(String filename){

        String outputText = "";

        // get assets manager
        AssetManager am = getAssets();

        try {

            // get input stream from assets
            InputStream is = am.open(filename);

            // get file size in bytes
            int fileSizeInBytes = is.available();

            // create byte array
            byte[] JSONBuffer = new byte[fileSizeInBytes];

            // get bytes data from input stream
            is.read(JSONBuffer);

            // set output text
            outputText = new String(JSONBuffer);

            // close input stream
            is.close();

        }catch(IOException ex) {

            // log error message in console
            Log.d(TAG, ex.getMessage());

            // print stack trace
            ex.printStackTrace();
        }

        // return JSON data
        return outputText;
    }

    // parse events list from JSON
    public ArrayList<DunedinEventInfo> parseEventsListFromJSON(String JSONInput) {

        // create dunedin event info
        ArrayList<DunedinEventInfo> eventsList = new ArrayList<>();

        try {

            // get JSONObject from string input
            JSONObject eventJSON = new JSONObject(JSONInput);

            // get events object
            JSONObject eventsRoot = eventJSON.getJSONObject("events");

            // get event array
            JSONArray event = eventsRoot.getJSONArray("event");

            // get event array size
            int eventsCount = event.length();

            // loop through each event in the event list
            for(int i = 0; i < eventsCount; i++) {

                // get the current event object in the array
                JSONObject currentEvent = event.getJSONObject(i);

                // get the title and description from the current JSONObject
                String title = currentEvent.getString("title");
                String description = currentEvent.getString("description");

                // create info class and pass in title and description
                DunedinEventInfo currentEventInfo = new DunedinEventInfo(title, description);

                // add the event info to the list
                eventsList.add(currentEventInfo);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return the list
        return eventsList;
    }

    // information holder object
    class DunedinEventInfo {

        private String title;
        private String description;

        public DunedinEventInfo(String title, String description){
            this.title = title;
            this.description = description;
        }

        public String getTitle(){ return this.title; }
        public String getDescription(){ return this.description; }

        @Override
        public String toString(){ return this.title; }

    }

    // item click handler to show description when an item in the listview is clicked
    class EventsListViewOnItemClickHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DunedinEventInfo selectedEvent = (DunedinEventInfo)parent.getItemAtPosition(position);
            Toast.makeText(MainActivity.this, selectedEvent.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

}

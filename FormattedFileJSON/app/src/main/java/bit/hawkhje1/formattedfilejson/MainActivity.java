package bit.hawkhje1.formattedfilejson;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DunedinEventInfo> dunedinEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dunedinEventsFilename = "dunedin_events.json";

        AssetManager am = getAssets();
        String JSONInput = "";

        try {

            InputStream is = am.open(dunedinEventsFilename);

            int fileSizeInBytes = is.available();
            byte[] JSONBuffer = new byte[fileSizeInBytes];
            is.read(JSONBuffer);

            JSONInput = new String(JSONBuffer);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        dunedinEventList = new ArrayList<>();

        try {

            JSONObject dunedinEvents = new JSONObject(JSONInput);
            JSONObject events = dunedinEvents.getJSONObject("events");

            JSONArray event = events.getJSONArray("event");
            int eventsCount = event.length();


            for(int i = 0; i < eventsCount; i++) {

                JSONObject currentEvent = event.getJSONObject(i);
                String title = currentEvent.getString("title");
                String description = currentEvent.getString("description");

                dunedinEventList.add(new DunedinEventInfo(title, description));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<DunedinEventInfo> eventsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dunedinEventList);
        ListView lvEvents = (ListView)findViewById(R.id.lvEvents);
        lvEvents.setAdapter(eventsAdapter);

        EventsListViewOnItemClickHandler lvOnItemClickListner = new EventsListViewOnItemClickHandler();
        lvEvents.setOnItemClickListener(lvOnItemClickListner);

    }

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

    class EventsListViewOnItemClickHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            DunedinEventInfo selectedEvent = (DunedinEventInfo)parent.getItemAtPosition(position);
            Toast.makeText(MainActivity.this, selectedEvent.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

}

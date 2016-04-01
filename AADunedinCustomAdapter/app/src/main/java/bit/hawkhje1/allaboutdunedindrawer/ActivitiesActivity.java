package bit.hawkhje1.allaboutdunedindrawer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscreen_layout);

        loadPageContent();

        List<ActivityItem> activities = loadActivities();
        ActivitiesAdapter activitiesAdapter = new ActivitiesAdapter(this, R.layout.activities_listitem, activities);

        ListView lvActivities = (ListView)findViewById(R.id.lvActivities);
        lvActivities.setAdapter(activitiesAdapter);

    }

    private List<ActivityItem> loadActivities(){
        // create list of activities
        List<ActivityItem> activities = new ArrayList<>();

        // get resources
        Resources res = getResources();

        // get drawables
        Drawable larnchCastle = res.getDrawable(R.drawable.larnach_castle);
        Drawable moanaPool = res.getDrawable(R.drawable.moana_pool);
        Drawable monarchCruise = res.getDrawable(R.drawable.monarch);
        Drawable octagon = res.getDrawable(R.drawable.octagon);
        Drawable olverston = res.getDrawable(R.drawable.olveston);
        Drawable peninsula = res.getDrawable(R.drawable.peninsula);

        // populate activities list
        activities.add(new ActivityItem(larnchCastle, "Larnch Castle"));
        activities.add(new ActivityItem(moanaPool, "Moana Pool"));
        activities.add(new ActivityItem(monarchCruise, "Monarch Cruise"));
        activities.add(new ActivityItem(octagon, "Octagon"));
        activities.add(new ActivityItem(olverston, "Olverston"));
        activities.add(new ActivityItem(peninsula, "Peninsula"));

        // return list of activities
        return activities;
    }

    private void loadPageContent() {
        TextView headerText = (TextView)findViewById(R.id.txtSubCategoryTitle);
        ImageView headerImage = (ImageView)findViewById(R.id.imgHeader);

        // Load subcategory heading
        String categoryTitle = getResources().getString(R.string.title_activities);
        headerText.setText(categoryTitle);
        headerImage.setImageResource(R.drawable.big_octagon);
    }

    class ActivityItem {
        private Drawable image;
        private String name;

        public Drawable getImage(){ return this.image; }
        public String getName(){ return this.name; }

        public ActivityItem(Drawable image, String name) {
            this.image = image;
            this.name = name;
        }


    }

    class ActivitiesAdapter extends ArrayAdapter<ActivityItem>{

        public ActivitiesAdapter(Context context, int resource, List<ActivityItem> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // create inflater
            LayoutInflater inflater = LayoutInflater.from(ActivitiesActivity.this);

            // inflate view
            View customView = inflater.inflate(R.layout.activities_listitem, parent);

            // get the layout controls from the inflated layout
            ImageView ivItemImage = (ImageView)customView.findViewById(R.id.ivItemImage);
            TextView tvItemText = (TextView)customView.findViewById(R.id.tvItemText);

            // get the current actvitiy
            ActivityItem currentActivity = getItem(position);

            // place data into the layout
            ivItemImage.setImageDrawable(currentActivity.getImage());
            tvItemText.setText(currentActivity.getName());

            return customView;

        }
    }
}

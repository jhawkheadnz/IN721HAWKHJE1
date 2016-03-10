package bit.hawkhje1.allaboutdunedinlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // http://developer.android.com/training/basics/firstapp/starting-activity.html
    public final static String EXTRA_TITLE = "bit.hawkhje1.allaboutdunedinlist.EXTRA_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // load navigation list
        setupNavigationList();

        // create and attach navigation click listener to listview
        NavigationClickListener navigationClickListener = new NavigationClickListener();
        ListView lvNavigation = (ListView)findViewById(R.id.lvNavigation);
        lvNavigation.setOnItemClickListener(navigationClickListener);
    }

    /**
     * Setup navigation list for all about dunedin navigation
     */
    private void setupNavigationList() {
        // load navigation links from strings resource file
        String[] navigationLinks = getResources().getStringArray(R.array.navigation_text);

        // place items in navigation links into array adaptor
        ArrayAdapter<String> navLinks = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, navigationLinks);

        // place items into listview
        ListView lvNavigation = (ListView)findViewById(R.id.lvNavigation);
        lvNavigation.setAdapter(navLinks);
    }

    /**
     * Navigation Click Listener for Main Menu
     */
    class NavigationClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // create intent for sub screen activity
            Intent categoryIntent = null;

            // get the category from the selected item
            String category = parent.getItemAtPosition(position).toString().toLowerCase();

            // select the intent to load based on the category
            switch(category)
            {
                case "activities":
                    categoryIntent = new Intent(MainActivity.this, ActivitiesActivity.class);
                    break;
                case "shopping":
                    categoryIntent = new Intent(MainActivity.this, ShoppingActivity.class);
                    break;
                case "dining":
                    categoryIntent = new Intent(MainActivity.this, DiningActivity.class);
                    break;
                case "services":
                    categoryIntent = new Intent(MainActivity.this, ServicesActivity.class);
                    break;
            }

            // start selected activity
            if(categoryIntent != null)
                startActivity(categoryIntent);

        }
    }

}
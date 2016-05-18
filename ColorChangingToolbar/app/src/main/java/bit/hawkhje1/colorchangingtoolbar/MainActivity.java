package bit.hawkhje1.colorchangingtoolbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView coloredSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the coloured square
        coloredSquare = (ImageView)findViewById(R.id.colouredSquare);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // get the menu inflater
        MenuInflater menuInflater = getMenuInflater();

        // inflate the layout
        menuInflater.inflate(R.menu.toolbar_colours, menu);

        // return the new layout
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // get the item tile
        String itemTitle = (String)item.getTitle();

        // clear image
        coloredSquare.setImageBitmap(null);

        // set a default color
        int displayColor = Color.MAGENTA;

        // change color based on item title
        switch(itemTitle){
            case "Blue":
                displayColor = Color.BLUE;
                break;
            case "Green":
                displayColor = Color.GREEN;
                break;
            case "Red":
                displayColor = Color.RED;
                break;
        }

        coloredSquare.setBackgroundColor(displayColor);

        return super.onOptionsItemSelected(item);
    }
}

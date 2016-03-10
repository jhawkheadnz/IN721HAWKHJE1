package bit.hawkhje1.allaboutdunedindrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscreen_layout);
        loadPageContent();
    }

    private void loadPageContent()
    {
        TextView headerText = (TextView)findViewById(R.id.txtSubCategoryTitle);
        ImageView headerImage = (ImageView)findViewById(R.id.imgHeader);

        // Load subcategory heading
        String categoryTitle = getResources().getString(R.string.title_shopping);
        headerText.setText(categoryTitle);
        headerImage.setImageResource(R.drawable.thieves_alley);
    }
}

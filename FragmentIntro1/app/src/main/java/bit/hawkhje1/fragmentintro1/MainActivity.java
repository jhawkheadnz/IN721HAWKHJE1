package bit.hawkhje1.fragmentintro1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup button click for image fragment
        LoadImageFragmentHandler loadImageHandler = new LoadImageFragmentHandler();
        Button btnLoadImageFragment = (Button)findViewById(R.id.btnLoadImageFragment);
        btnLoadImageFragment.setOnClickListener(loadImageHandler);

        // setup button click for list view fragment
        LoadListViewFragmentHandler listViewHandler = new LoadListViewFragmentHandler();
        Button btnLoadListViewFragment = (Button)findViewById(R.id.btnLoadListViewFragment);
        btnLoadListViewFragment.setOnClickListener(listViewHandler);
    }

    class LoadImageFragmentHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Fragment imageFragment = new ShowImageFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.linear_layout_placeholder, imageFragment);
            transaction.commit();
        }
    }

    class LoadListViewFragmentHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            Fragment listViewFragment = new ShowListViewFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.linear_layout_placeholder, listViewFragment);
            transaction.commit();
        }
    }

}

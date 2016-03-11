package bit.hawkhje1.fragmentintro1Tablet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Work on 3/11/2016.
 */
public class ShowListViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View listViewFragment = inflater.inflate(R.layout.show_listview_fragment, container, false);

        ListView myListView = (ListView)listViewFragment.findViewById(R.id.lvItems);

        String[] places = getResources().getStringArray(R.array.placesList);

        ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, places);

        myListView.setAdapter(placesAdapter);

        return listViewFragment;
    }
}

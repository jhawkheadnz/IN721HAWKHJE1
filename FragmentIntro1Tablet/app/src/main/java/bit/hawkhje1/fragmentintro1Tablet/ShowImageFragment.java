package bit.hawkhje1.fragmentintro1Tablet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Work on 3/11/2016.
 */
public class ShowImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View imageFragment = inflater.inflate(R.layout.show_image_fragment, container, false);



        return imageFragment;

    }
}

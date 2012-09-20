package novoda.android.typewriter.playground;

import android.app.Activity;
import android.app.Fragment;

public class My2WayFragment extends Fragment implements GetMarker, SetMarker {

    @Override
    public String get() {
        return null;
    }

    @Override
    public void setMarker(String wh) {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}

package novoda.android.typewriter.playground;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;

public class TestFragment extends Fragment implements ObsFragment<TestFragment.Testing> {

    public interface OneWay<Message> {
        Message getInterface();
    }

    public interface TwoWay<sub, pub> {

    }

    @Override
    public void ping(Testing to) {
        to.onTest();
    }

    public static interface Testing {
        void onTest();
    }

    Testing on;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        on = ((ObservableFragment) activity).observe(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                on.onTest();
            }
        }, 2000);
    }
}

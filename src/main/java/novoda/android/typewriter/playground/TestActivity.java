package novoda.android.typewriter.playground;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TestActivity extends FragmentActivity implements ObservableFragment {

    TestFragment.Testing t = new TestFragment.Testing() {
        @Override
        public void onTest() {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v4.app.FragmentTransaction g = getSupportFragmentManager().beginTransaction();
        g.commit();
    }

    @Override
    public <T> T observe(ObsFragment<T> fragment) {
        return (T) t;
    }
}

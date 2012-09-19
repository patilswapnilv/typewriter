package novoda.android.typewriter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.actionbarsherlock.app.SherlockListFragment;
import novoda.android.typewriter.content.Query;
import novoda.android.typewriter.cursor.ListCursor;
import novoda.android.typewriter.loader.TypedLoader;


public abstract class LoadableTypedListFragment<T> extends SherlockListFragment
        implements LoaderManager.LoaderCallbacks<ListCursor<T>> {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public Loader<ListCursor<T>> onCreateLoader(int i, Bundle bundle) {
        Query query = from(bundle);
        Loader<ListCursor<T>> loader = new TypedLoader<T>(
                getActivity().getApplicationContext(),
                getType(),
                query.getUri(), null, null, null, null);
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<ListCursor<T>> loader) {
    }

    protected abstract Query from(Bundle bundle);

    protected abstract Class<T> getType();
}

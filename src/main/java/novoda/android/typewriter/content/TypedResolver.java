package novoda.android.typewriter.content;

import android.content.ContentResolver;
import android.net.Uri;
import novoda.android.typewriter.cursor.ListCursor;

public class TypedResolver {

    private final ContentResolver resolver;

    public TypedResolver(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public <T> ListCursor<T> query(Uri uri, Class<T> type) {
        return null;
    }
}

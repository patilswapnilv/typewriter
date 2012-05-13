package novoda.android.typewriter.content;

import android.content.ContentResolver;
import android.net.Uri;

import java.util.List;

public class TypedResolver {

    private final ContentResolver resolver;

    public TypedResolver(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public <T> List<T> query(Uri uri, Class<T> type) {
        return null;
    }
}

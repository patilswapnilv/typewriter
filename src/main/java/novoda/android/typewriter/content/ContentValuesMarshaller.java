package novoda.android.typewriter.content;

import android.content.ContentValues;
import novoda.android.typewriter.Marshaller;

public abstract class ContentValuesMarshaller<T> implements Marshaller<ContentValues, T> {

    @Override
    public ContentValues marshall(T from, Class<ContentValues> as) {
        return from(from);
    }

    abstract ContentValues from(T from);
}

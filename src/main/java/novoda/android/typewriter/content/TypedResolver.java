package novoda.android.typewriter.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import novoda.android.typewriter.cursor.CursorMarshaller;
import novoda.android.typewriter.cursor.ListCursor;
import novoda.android.typewriter.cursor.TypedCursor;
import novoda.android.typewriter.util.ClassUtil;

public class TypedResolver {

    private final ContentResolver resolver;

    public TypedResolver(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public <T> T get(Uri uri, Class<T> type) {
        return get(uri, null, null, null, type);
    }

    public <T> T get(Uri uri, String[] projection, Class<T> type) {
        return get(uri, projection, null, null, type);
    }

    public <T> T get(Uri uri, String selection, String[] selectionArgs, Class<T> type) {
        return get(uri, null, selection, selectionArgs, type);
    }

    public <T> T get(Uri uri, String[] projection, String selection, String[] selectionArgs, Class<T> type) {
        final Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            return new CursorMarshaller<T>().marshall(cursor, type);
        }
        return ClassUtil.newInstance(type);
    }

    public <T> ListCursor<T> query(Uri uri, Class<T> type) {
        return query(uri, null, null, type);
    }

    public <T> ListCursor<T> query(Uri uri, String selection, String[] selectionArgs, Class<T> type) {
        return query(uri, null, selection, selectionArgs, null, type);
    }

    public <T> ListCursor<T> query(Uri uri, String[] projection, String selection, String[] selectionArgs, Class<T> type) {
        return query(uri, projection, selection, selectionArgs, null, type);
    }

    public <T> ListCursor<T> query(Uri uri, String selection, String[] selectionArgs, String sortOrder, Class<T> type) {
        return query(uri, null, selection, selectionArgs, sortOrder, type);
    }

    public <T> ListCursor<T> query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, Class<T> type) {
        final Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        return new TypedCursor<T>(cursor, type);
    }

    /**
     * INSERT
     */
    public <T> Uri insert(Uri against, T what, ContentValuesMarshaller marshaller) {
        //ContentValuesMarshaller marshaller = new ContentValuesMarshaller<T>();
        ContentValues values = marshaller.marshall(what, ContentValues.class);
        return resolver.insert(against, values);
    }

    public <T> int update(Uri against, T what, String where, String[] arg, ContentValuesMarshaller marshaller) {
        //ContentValuesMarshaller marshaller = new ContentValuesMarshaller<T>();
        ContentValues values = marshaller.marshall(what, ContentValues.class);
        return resolver.update(against, values, where, arg);
    }

}

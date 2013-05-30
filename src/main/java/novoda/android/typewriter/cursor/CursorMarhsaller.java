package novoda.android.typewriter.cursor;

import android.database.Cursor;

public interface CursorMarhsaller<T> {
    void marshall(Cursor cursor);
}

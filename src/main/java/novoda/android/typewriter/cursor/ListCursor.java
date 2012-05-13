package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.util.List;

public interface ListCursor<T> extends List<T>, Closable {
    Cursor getCursor();
}

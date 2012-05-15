package novoda.android.typewriter.cursor;

import android.database.Cursor;
import novoda.android.typewriter.Marshaller;
import novoda.android.typewriter.introspection.RichClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CursorMarshaller<T> implements Marshaller<T, Cursor> {

    private RichClass richClass;

    @Override
    public T marshall(Cursor cur, Class<T> what) {
        if (cur.getPosition() < 0) {
            throw new RuntimeException("Cursor is at position below 0" + cur.getPosition());
        }
        RichClass klass = getRichClass(what);
        T obj = null;
        try {
            obj = what.newInstance();
            List<String> columnNames = Arrays.asList(cur.getColumnNames());
            for (String column : columnNames) {
                if (klass.hasMethod(column)) {
                    final int index = cur.getColumnIndexOrThrow(column);
                    Method setter = klass.setter(column);
                    setter.invoke(obj, getObjectFromCursor(cur, index));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getObjectFromCursor(Cursor cursor, int index) {
        final int type = cursor.getType(index);
        final Object obj;
        switch (type) {
            case Cursor.FIELD_TYPE_FLOAT:
                obj = cursor.getFloat(index);
                break;
            case Cursor.FIELD_TYPE_STRING:
                obj = cursor.getString(index);
                break;
            default:
                obj = "";
        }
        return obj;
    }

    private RichClass getRichClass(Class<T> klass) {
        if (richClass == null) {
            richClass = new RichClass(klass);
        }
        return richClass;
    }
}
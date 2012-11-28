package novoda.android.typewriter.cursor;

import android.database.Cursor;
import novoda.android.typewriter.Marshaller;
import novoda.android.typewriter.introspection.RichClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CursorMarshaller<T> implements Marshaller<T, Cursor> {

    private final static int TYPE_INT = 0;
    private final static int TYPE_DOUBLE = 1;
    private final static int TYPE_LONG = 2;

    private RichClass richClass;

    @Override
    public T marshall(Cursor cur, Class<T> what) {
        if (cur.getPosition() < 0) {
            throw new RuntimeException("Cursor is at position below 0" + cur.getPosition());
        }
        RichClass klass = getRichClass(what);
        T obj = null;
        Method setter = null;
        try {
            obj = what.newInstance();
            List<String> columnNames = Arrays.asList(cur.getColumnNames());
            for (String column : columnNames) {
                if (klass.hasMethod(column)) {
                    final int index = cur.getColumnIndexOrThrow(column);
                    setter = klass.setter(column);
                    int type = TYPE_INT;
                    Class<?> t = setter.getParameterTypes()[0];
                    if (t.equals(long.class)) {
                        type = TYPE_LONG;
                    } else if (t.equals(double.class)) {
                        type = TYPE_DOUBLE;
                    }
                    Object objArg = getObjectFromCursor(cur, index, type);
                    if (objArg != null) {
                        setter.invoke(obj, objArg);
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to marshall " +
                    ((setter != null) ? setter.getName() : " setter is null"), e);
        }
    }

    private Object getObjectFromCursor(Cursor cursor, int index, int type) {
        final int cursorType = cursor.getType(index);
        final Object obj;
        switch (cursorType) {
            case Cursor.FIELD_TYPE_FLOAT:
                obj = cursor.getFloat(index);
                break;
            case Cursor.FIELD_TYPE_STRING:
                obj = cursor.getString(index);
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                switch (type) {
                    case TYPE_DOUBLE:
                        obj = cursor.getDouble(index);
                        break;
                    case TYPE_LONG:
                        obj = cursor.getLong(index);
                        break;
                    default:
                        obj = cursor.getInt(index);
                        break;
                }
                break;
            case Cursor.FIELD_TYPE_BLOB:
                obj = cursor.getBlob(index);
                break;
            default:
                obj = null;
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
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
                final int index = cur.getColumnIndexOrThrow(column);
                int type = cur.getType(index);
                Method setter = klass.setter(column);
//                Object value = cur.getFloat(index);
                System.out.println("T " + type);
                switch (type) {
                    case Cursor.FIELD_TYPE_FLOAT:
                        setter.invoke(obj, cur.getFloat(index));
                        break;

                    case Cursor.FIELD_TYPE_STRING:
                        setter.invoke(obj, cur.getString(index));
                        break;

                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RichClass getRichClass(Class<T> klass) {
        if (richClass == null) {
            richClass = new RichClass(klass);
        }
        return richClass;
    }
}
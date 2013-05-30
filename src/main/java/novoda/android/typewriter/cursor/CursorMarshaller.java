package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import novoda.android.typewriter.Marshaller;
import novoda.android.typewriter.introspection.RichClass;

public class CursorMarshaller<T> implements Marshaller<T, Cursor> {

    private final static int TYPE_INT = 0;
    private final static int TYPE_DOUBLE = 1;
    private final static int TYPE_LONG = 2;

    private final RichClass<T> richClass;
    private final Map<String, Method> methodsByName = new HashMap<String, Method>();

    public CursorMarshaller(Cursor cursor, Class<T> classType) {
        richClass = new RichClass<T>(classType);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String name = cursor.getColumnName(i);
            if (richClass.hasMethod(name)) {
                Method setter = richClass.setter(name);
                methodsByName.put(name, setter);
            }
        }
    }

    @Override
    public T marshall(Cursor cursor, Class<T> what) {
        if (cursor.getPosition() < 0) {
            throw new RuntimeException("Cursor is at position below 0" + cursor.getPosition());
        }
        T obj = null;
        try {
            obj = richClass.newInstance();
            List<String> columnNames = Arrays.asList(cursor.getColumnNames());
            for (String column : columnNames) {
                if (richClass.hasMethod(column)) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    Method setter = richClass.setter(column);
                    int type = TYPE_INT;
                    Class<?> t = setter.getParameterTypes()[0];
                    if (t.equals(long.class)) {
                        type = TYPE_LONG;
                    } else if (t.equals(double.class)) {
                        type = TYPE_DOUBLE;
                    }
                    setter.invoke(obj, getObjectFromCursor(cursor, index, type));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            default:
                obj = "";
        }
        return obj;
    }
}
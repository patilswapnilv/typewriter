package novoda.android.typewriter.cursor;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.*;

public class TypedCursor<T> implements List<T>, Iterator<T>, Closable {

    private static final String TAG = TypedCursor.class.getSimpleName();

    private final Cursor cursor;
    private final Class<T> type;
    private final CursorMarshaller<T> marshaller;

    List<String> columns;
    List<Field> fields;

    public TypedCursor(Cursor cursor, Class<T> type) {
        this.cursor = cursor;
        this.type = type;
        this.marshaller = new CursorMarshaller<T>();

        columns = Arrays.asList(cursor.getColumnNames());
        fields = new ArrayList<Field>();
        for (String name : columns) {
            try {
                fields.add(type.getField(name));
            } catch (NoSuchFieldException e) {
                if (Log.isLoggable(TAG, Log.WARN)) {
                    Log.w(TAG, "Cursor has column " + name + " but field in class not present" + type.getSimpleName());
                }
            }
        }
    }

    @Override
    public void close() {
        if (cursor != null) {
            cursor.close();
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int size() {
        return cursor.getCount() - 1;
    }

    @Override
    public List<T> subList(int i, int i1) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return cursor.getCount() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return cursor.getCount() > (cursor.getPosition() + 1);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new CursorListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new CursorListIterator();
    }

    public class CursorListIterator implements ListIterator<T> {

        @Override
        public void add(T t) {
            throw new RuntimeException("Modification Operations not supported");
        }

        @Override
        public boolean hasNext() {
            return TypedCursor.this.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return getCursor().getPosition() > 0;
        }

        @Override
        public T next() {
            return TypedCursor.this.next();
        }

        @Override
        public int nextIndex() {
            return cursor.getPosition() + 1;
        }

        @Override
        public T previous() {
            cursor.moveToPrevious();
            try {
                return marshaller.marshall(cursor, type);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }

        @Override
        public int previousIndex() {
            return cursor.getPosition() + 1;
        }

        @Override
        public void remove() {
            throw new RuntimeException("Modification Operations not supported");
        }

        @Override
        public void set(T t) {
            throw new RuntimeException("Modification Operations not supported");
        }
    }

    @Override
    public T next() {
        cursor.moveToNext();
        try {
            return marshaller.marshall(cursor, type);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    @Override
    public T get(int index) {
        cursor.move(index);
        try {
            return marshaller.marshall(cursor, type);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public void remove() {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Modification Operations not supported");
    }


    @Override
    public T set(int index, T element) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public boolean add(T t) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException("Modification Operations not supported");
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException("not supported");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException("not supported");
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException("Not implemented");
    }
}

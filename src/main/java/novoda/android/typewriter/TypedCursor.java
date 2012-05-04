package novoda.android.typewriter;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.*;

public class TypedCursor<T> implements List<T>, ListIterator<T>, Iterator<T>, Closable {

    private static final String TAG = TypedCursor.class.getSimpleName();

    private final Cursor cursor;
    private final Class<T> type;
    List<String> columns;
    List<Field> fields;

    public TypedCursor(Cursor cursor, Class<T> type) {
        this.cursor = cursor;
        this.type = type;
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
        return cursor.getCount();
    }

    @Override
    public boolean isEmpty() {
        return cursor.getCount() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public Object[] toArray() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean hasNext() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T next() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasPrevious() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T previous() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int nextIndex() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int previousIndex() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void set(T t) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(T t) {
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
    public T get(int index) {
        return null;
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
    public ListIterator<T> listIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

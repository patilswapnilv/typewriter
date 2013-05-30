package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TypedCursor<T> implements ListCursor<T>, Iterator<T>, Closeable {

    private final Cursor cursor;
    private final Class<T> type;
    private final ReflectionCursorMarshaller<T> marshaller;

    public TypedCursor(Cursor cursor, Class<T> type) {
        this.cursor = cursor;
        this.type = type;
        this.marshaller = new ReflectionCursorMarshaller<T>(cursor, type);
    }

    @Override
    public void close() {
        cursor.close();
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int size() {
        return cursor.getCount();
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
        cursor.moveToPosition(-1);
        return this;
    }

    @Override
    public boolean hasNext() {
        return cursor.getCount() > (cursor.getPosition() + 1);
    }

    @Override
    public ListIterator<T> listIterator() {
        cursor.moveToPosition(-1);
        return new CursorListIterator<T>(cursor, marshaller, type, 0);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new CursorListIterator<T>(cursor, marshaller, type, i);
    }

    @Override
    public T next() {
        cursor.moveToNext();
        return marshaller.marshall(cursor, type);
    }

    @Override
    public T get(int index) {
        cursor.moveToPosition(index);
        return marshaller.marshall(cursor, type);
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

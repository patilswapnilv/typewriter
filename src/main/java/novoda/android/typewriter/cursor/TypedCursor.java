package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TypedCursor<T> implements ListCursor<T>, Closeable {

    private final Cursor cursor;
    private final CursorMarshaller<T> marshaller;

    public TypedCursor(Cursor cursor, Class<T> type) {
        this(cursor, new ReflectionCursorMarshaller<T>(cursor, type));
    }

    public TypedCursor(Cursor cursor, CursorMarshaller<T> marshaller) {
        this.cursor = cursor;
        this.marshaller = marshaller;
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
    public boolean isEmpty() {
        return cursor.getCount() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        cursor.moveToPosition(-1);
        return new CursorListIterator<T>(cursor, marshaller, 0);
    }

    @Override
    public ListIterator<T> listIterator() {
        cursor.moveToPosition(-1);
        return new CursorListIterator<T>(cursor, marshaller, 0);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new CursorListIterator<T>(cursor, marshaller, i);
    }

    @Override
    public T get(int index) {
        cursor.moveToPosition(index);
        return marshaller.marshall(cursor);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException();
    }
}

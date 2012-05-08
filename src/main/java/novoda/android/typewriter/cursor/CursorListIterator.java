package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.util.ListIterator;

public class CursorListIterator<T> implements ListIterator<T> {

    private final Cursor cur;
    private final CursorMarshaller<T> marshaller;

    public CursorListIterator(Cursor cur, CursorMarshaller<T> marshaller) {
        this.cur = cur;
        this.marshaller = marshaller;
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException("no modification support");
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public T previous() {
        return null;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("no modification support");
    }

    @Override
    public void set(T t) {
        throw new UnsupportedOperationException("no modification support");
    }
}

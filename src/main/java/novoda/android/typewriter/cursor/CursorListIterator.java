package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.util.ListIterator;

public class CursorListIterator<T> implements ListIterator<T> {

    private final Cursor cur;
    private final CursorMarshaller<T> marshaller;

    public CursorListIterator(Cursor cur, CursorMarshaller<T> marshaller, int index) {
        this.cur = cur;
        this.marshaller = marshaller;
        cur.move(index);
    }

    @Override
    public boolean hasNext() {
        final int currentPosition = cur.getPosition();
        final int size = cur.getCount();
        return (currentPosition + 1) < size;
    }

    @Override
    public boolean hasPrevious() {
        return cur.getPosition() > 0;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public int nextIndex() {
        return cur.getPosition() + 1;
    }

    @Override
    public T previous() {
        return null;
    }

    @Override
    public int previousIndex() {
        return cur.getPosition() - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("no modification support");
    }

    @Override
    public void set(T t) {
        throw new UnsupportedOperationException("no modification support");
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException("no modification support");
    }
}

package novoda.android.typewriter.cursor;

import android.database.Cursor;

import java.util.ListIterator;

public class CursorListIterator<T> implements ListIterator<T> {

    private final Cursor cur;
    private final ReflectionCursorMarshaller<T> marshaller;
    private final Class<T> type;
    private final int index;

    public CursorListIterator(Cursor cur, ReflectionCursorMarshaller<T> marshaller, Class<T> type, int index) {
        this.cur = cur;
        this.marshaller = marshaller;
        this.type = type;
        this.index = index;
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
        if (cur.getPosition() <= index) return false;
        return cur.getPosition() > 0;
    }

    @Override
    public T next() {
        cur.move(nextIndex());
        return marshaller.marshall(cur);
    }

    @Override
    public int nextIndex() {
        return cur.getPosition() + 1;
    }

    @Override
    public T previous() {
        cur.move(previousIndex());
        return marshaller.marshall(cur);
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

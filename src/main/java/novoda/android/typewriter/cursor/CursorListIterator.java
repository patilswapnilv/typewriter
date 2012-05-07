package novoda.android.typewriter.cursor;

import java.util.ListIterator;

public class CursorListIterator<T> implements ListIterator<T> {

    @Override
    public void add(T t) {
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
    }

    @Override
    public void set(T t) {
    }
}

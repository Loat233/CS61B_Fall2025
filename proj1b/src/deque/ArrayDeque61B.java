package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ArrayDeque61B<T> implements Deque61B<T> {
    int size = 0;
    int nextFirst;
    int nextLast;
    public T[] items;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        nextFirst = items.length / 2;
        nextLast = nextFirst + 1;
    }

    public int circularIndex(int index) {
        return Math.floorMod(index, items.length);
    }

    @Override
    public void addFirst(T x) {
        if (size >= items.length) {
            resizing(size * 4);
        }

        items[nextFirst] = x;
        nextFirst = circularIndex(--nextFirst);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size >= items.length) {
            resizing(size * 4);
        }

        items[nextLast]= x;
        nextLast = circularIndex(++nextLast);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            copy.add(get(i));
        }
        return copy;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        /*
        if (size < items.length / 4) {
            resizing(size / 4);
        }
        */
        T firstItem = get(0);
        nextFirst = circularIndex(++nextFirst);
        items[nextFirst] = null;
        size--;
        return firstItem;
    }

    @Override
    public T removeLast() {
        /*
        if (size < items.length / 4) {
            resizing(size / 4);
        }
        */
        T lastItem = get(size - 1);
        nextLast = circularIndex(--nextLast);
        items[nextLast] = null;
        size--;
        return lastItem;
    }

    @Override
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        int first = nextFirst + 1;
        index = circularIndex(first + index);
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public void replace(int index, T item) {
        int first = nextFirst + 1;
        index = circularIndex(first + index);
        items[index] = item;
    }

    @Override
    public void resizing(int capacity) {
        ArrayDeque61B<T> a = new ArrayDeque61B<>();
        a.items = (T[]) new Object[capacity];
        a.nextFirst = capacity / 2;
        a.nextLast = a.nextFirst + 1;

        int min = Math.min(size, capacity);
        for(int i = 0; i < min; i++) {
            a.addLast(this.get(i));
        }

        this.items = a.items;
        this.nextFirst = a.nextFirst;
        this.nextLast = a.nextLast;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int index;

        private ArrayIterator() {
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return get(circularIndex(index++));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque61B<?> other) {
            if (size != other.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(this.get(i), other.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}

package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private int size;
    private Node sentinel;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    private class LinkedListIterator implements Iterator<T> {
        private int index;

        private LinkedListIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return get(index++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class Node {
        Node pre;
        Node next;
        T item;

        private Node(Node p, Node n, T i) {
            pre = p;
            next = n;
            item = i;
        }
    }

    @Override
    public void addFirst(T x) {
        Node n = new Node(sentinel, sentinel.next, x);
        sentinel.next.pre = n;
        sentinel.next = n;

        if (size == 0) {
            sentinel.pre = n;
        }

        size++;
    }

    @Override
    public void addLast(T x) {
        Node n = new Node(sentinel.pre, sentinel, x);
        sentinel.pre.next = n;
        sentinel.pre = n;

        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node n = sentinel.next;

        while (n != sentinel) {
            returnList.add(n.item);
            n = n.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        sentinel.next = sentinel.next.next;
        sentinel.next.next.pre = sentinel;
        return sentinel.next.item;
    }

    @Override
    public T removeLast() {
        Node n = sentinel.pre;
        n.pre.next = sentinel;
        sentinel.pre = n.pre;
        return sentinel.pre.item;
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }

        Node n = sentinel;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return n.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index > size) {
            return null;
        }
        return getRecursive(this.sentinel, index);
    }

    @Override
    public void resizing(int capacity) {
        throw new UnsupportedOperationException("No need to implement resizing for LinkedList.");
    }

    @Override
    public void replace(int index, T item) {
        throw new UnsupportedOperationException("No need to implement replace for LinkedList.");
    }

    private T getRecursive(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getRecursive(n.next, --index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque61B<?> other) {
            if (this.size() != other.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (! this.get(i).equals(other.get(i))) {
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
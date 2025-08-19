import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T>  implements Deque61B<T> {
    private int size;
    private Node sentinel;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
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

    private T getRecursive(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getRecursive(n.next, --index);
    }
}
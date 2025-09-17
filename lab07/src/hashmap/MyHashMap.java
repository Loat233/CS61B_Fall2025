package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    List<Node> nodes;
    double loadFactor;
    int capacity;
    int size;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.buckets = new Collection[capacity];
        this.nodes = new ArrayList<>();
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    private Collection<Node> getKeyBucket(K key) {
        int hashCode = Math.floorMod(key.hashCode(), capacity);
        Collection<Node> bucket = buckets[hashCode];

        if (bucket == null) {
            buckets[hashCode] = createBucket();
            bucket = buckets[hashCode];
        }
        return bucket;
    }

    private void resize() {
        double curLoadFactor = (double) size / capacity;
        if (curLoadFactor >= loadFactor) {
            capacity *= 2;
            buckets = new Collection[capacity];
            for (Node n : nodes) {
                Collection<Node> bucket = getKeyBucket(n.key);
                bucket.add(n);
            }
        }
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void put(K key, V value) {
        resize();
        if (containsKey(key)) {
            Collection<Node> bucket = getKeyBucket(key);
            for (Node n : bucket) {
                if (n.key.equals(key)) {
                    n.value = value;
                }
            }
        }
        else {
            Collection<Node> bucket = getKeyBucket(key);
            Node node = new Node(key, value);
            bucket.add(node);
            nodes.add(node);
            size++;
        }
    }

    @Override
    public V get(K key) {
        Collection<Node> bucket = getKeyBucket(key);

        for (Node n : bucket) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Collection<Node> bucket = getKeyBucket(key);

        for (Node n : bucket) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        capacity = 16;
        size = 0;
        buckets = new Collection[capacity];
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = new HashSet<>();
        for (Node node : nodes) {
            ks.add(node.key);
        }
        return ks;
    }

    @Override
    public V remove(K key) {
        V value = null;
        if (containsKey(key)) {
            Collection<Node> bucket = getKeyBucket(key);
            value = get(key);
            bucket.removeIf(node -> node.key.equals(key));

            size--;
            nodes.removeIf(node -> node.key.equals(key));
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

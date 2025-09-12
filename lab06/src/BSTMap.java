import java.util.*;

public class BSTMap<k extends Comparable<k>, v> implements Map61B<k, v> {
    private static class Tree<k extends Comparable<k>, v> {
        k key;
        v value;
        Tree<k, v> lTree;
        Tree<k, v> rTree;

        private Tree(k key, v value) {
            this.key = key;
            this.value = value;
            lTree = null;
            rTree = null;
        }

        public static <k extends Comparable<k>, v> Tree<k, v> put(Tree<k, v> t, k key, v value) {
            if (t == null || t.key == null) {
                return new Tree<>(key, value);
            }
            else if (t.key.compareTo(key) > 0) {
                t.lTree = put(t.lTree, key, value);
            } else if (t.key.compareTo(key) < 0) {
                t.rTree = put(t.rTree, key, value);
            }
            else {
                t.value = value;
            }
            return t;
        }

        public static <k extends Comparable<k>, v> v get(Tree<k, v> t, k key) {
            if (t == null || t.key == null) {
                return null;
            }
            else if (t.key.compareTo(key) > 0) {
                return get(t.lTree, key);
            }
            else if (t.key.compareTo(key) < 0) {
                return get(t.rTree, key);
            }
            return t.value;
        }

        public static <k extends Comparable<k>, v> boolean containsKey(Tree<k, v> t, k key) {
            if (t == null || t.key == null) {
                return false;
            }
            else if (t.key.compareTo(key) > 0) {
                return containsKey(t.lTree, key);
            }
            else if (t.key.compareTo(key) < 0) {
                return containsKey(t.rTree, key);
            }
            return true;
        }

        public static <k extends Comparable<k>, v> ArrayList<v> arrayInOrder(ArrayList<v> array, Tree<k, v> t) {
            if (t == null || t.key == null) {
                return null;
            }
            arrayInOrder(array, t.lTree);
            array.add(t.value);
            arrayInOrder(array, t.rTree);
            return array;
        }

        public static <k extends Comparable<k>, v> Set<k> keySet(Set<k> set, Tree<k, v> t) {
            if (t == null || t.key == null) {
                return null;
            }
            keySet(set, t.lTree);
            set.add(t.key);
            keySet(set, t.rTree);
            return set;
        }

        public static <k extends Comparable<k>, v> Tree<k, v> remove(Tree<k, v> t, k key) {
            if (t == null || t.key == null) {
                return null;
            }
            else if (t.key.compareTo(key) > 0) {
                t.lTree = remove(t.lTree, key);
            }
            else if (t.key.compareTo(key) < 0) {
                t.rTree = remove(t.rTree, key);
            }
            else if (t.key.compareTo(key) == 0){
                if (t.lTree == null && t.rTree == null) {
                    t = null;
                }
                else {
                    Tree<k, v> replaceTree;
                    if (t.rTree == null) {
                        replaceTree = t.lTree;
                    }
                    else {
                        replaceTree = removeHelper(t.rTree);
                    }
                    k finalKey = replaceTree.key;
                    replaceTree.lTree = t.lTree;
                    replaceTree.rTree = remove(replaceTree.rTree, finalKey);
                    t = replaceTree;
                }
            }
            return t;
        }

        public static <k extends Comparable<k>, v> Tree<k, v> removeHelper(Tree<k, v> t) {
            if (t == null || t.key == null) {
                return null;
            }
            else if (t.lTree == null) {
                return t;
            }
            return removeHelper(t.lTree);
        }
    }

    Tree<k , v> tree;
    int size;

    public BSTMap(){
        tree = null;
        size = 0;
    }

    @Override
    public void put(k key, v value) {
        if (!containsKey(key)) {
            size++;
        }
        tree = Tree.put(tree, key, value);
    }

    @Override
    public v get(k key) {
        return Tree.get(tree, key);
    }

    @Override
    public boolean containsKey(k key) {
        if (tree == null) {
            return false;
        }
        return Tree.containsKey(tree, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        tree = new Tree<>(null, null);
        size = 0;
    }

    @Override
    public Set<k> keySet() {
        Set<k> set = new TreeSet<>();
        return Tree.keySet(set, tree);
    }

    @Override
    public v remove(k key) {
        v value = Tree.get(tree, key);
        if (value == null) {
            return null;
        }
        tree = Tree.remove(tree, key);
        size--;
        return value;
    }

    public ArrayList<v> arrayInOrder() {
        ArrayList<v> array = new ArrayList<>();
        return Tree.arrayInOrder(array, tree);
    }

    @Override
    public Iterator<k> iterator() {
        return new TreeIterator<>();
    }

    private class TreeIterator<v> implements Iterator<v> {
        ArrayList<v> array = (ArrayList<v>) arrayInOrder();
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public v next() {
            return array.get(index++);
        }
    }
}

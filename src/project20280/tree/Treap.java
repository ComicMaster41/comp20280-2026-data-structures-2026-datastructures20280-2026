package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

public class Treap<K, V> extends TreeMap<K, V> {
    public Treap() {
        super();
        tree = new BalanceableBinaryTree<>();
        tree.addRoot(null);
    }

    public Treap(java.util.Comparator<K> comp) {
        super(comp);
        tree = new BalanceableBinaryTree<>();
        tree.addRoot(null);
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        // Generate random priority
        Position<Entry<K, V>> node = treeSearch(root(), key);

        if (isExternal(node)) {
            expandExternal(node, new MapEntry<>(key, value));
            double random = (Math.random() * 100); // randomize beetween 0-100
            // insert by key
            tree.setAux(node, (int)random);
            // Rotate node up until its priority is less than the parents (heap property)
            while (!isRoot(node) && tree.getAux(node) >= tree.getAux(parent(node))) {
                rotate(node);
            }
            rebalanceInsert(node);
            return null;
        } else {
            V old = node.getElement().getValue();
            tree.set(node, new MapEntry<>(key, value));
            rebalanceAccess(node);
            return old;
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> find = treeSearch(root(), key);
        if (isExternal(find)) { // key not found
            rebalanceAccess(find);
            return null;
        }

        V old = find.getElement().getValue();

        // Has child nodes
        while (isInternal(left(find)) || isInternal(right(find))) { // has two internal chldren
            // If the left priority is greater than current priority, swap like heap
            // Pick which child has higher priority and rotate
            int right = tree.getAux(right(find));
            int left = tree.getAux(left(find));

            Position<Entry<K, V>> child = null;

            // if both children exsist, pick higher priority
            if (isInternal(left(find)) && isInternal(right(find))) {
                child = left > right ? left(find) : right(find);
            }

            else {
                // If there's only one child, pick it
                child = isInternal(left(find)) ? left(find) : right(find);
            }
            rotate(child);
        }

        // Since there are two leaf nodes
        Position<Entry<K, V>> leaf = left(find); // Grab one
        Position<Entry<K, V>> sib = sibling(leaf);
        remove(leaf); // Remove it
        remove(find); // Then remove the other
        rebalanceDelete(sib);

        return old;
    }

    public static void main(String[] args) {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] arr = {35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
        for (Integer i : arr) treap.put(i, i);
        System.out.println(treap.toBinaryTreeString());
        System.out.println(treap.keySet()); // should be sorted
    }
}


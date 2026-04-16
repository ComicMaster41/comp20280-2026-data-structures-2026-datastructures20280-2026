package project20280.tree;

import java.math.BigInteger;
import java.util.*;
import project20280.interfaces.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static Random rnd = new Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    public Node<E> constructHelper (E[] in, int inStart, int inEnd, E[] pre, int preStart, int preEnd) {
        if (inStart > inEnd || preStart > preEnd) {
            return null;
        }

        // create root from first element in pre
        E root = pre[preStart]; // 1. the first element of preorder is the root of the tree

        // Find the root in the inorder traversal
        int rootIndex = -1;
        for (int i = inStart; i <= inEnd; ++i) {
            if (in[i].equals(root)) {
                rootIndex = i;
                break;
            }
        }

        // check if its valid
        if (rootIndex == -1) throw new IllegalArgumentException("Root index is invalid");

        // the left sub-tree will have nodes in range [start, index - 1]
        int leftSize= rootIndex - inStart; // why do we go from the rootIndex -> start

        // Create node to use for later
        Node<E> node = new Node<E>(root, null, null, null);

        // now traverse left and right
        Node<E> left = constructHelper(in, inStart, rootIndex - 1, pre, preStart + 1, preStart + leftSize);
        // QUESTIONS: explain from rootIndex - 1 to preStart + leftSize

        // IMPORTANT BUG: ROOTINDEX - 1 WAS WRITTEN ORIGINALLY, THEN I CHANGED IT TO ROOTINDEX + 1
        Node<E> right = constructHelper(in, rootIndex + 1, inEnd, pre, preStart + leftSize + 1, preEnd);
        // QUESTION: why is it preStart + leftSize + 1, why the 1? -> bc. you don't wanna start from the end that you just finished at, you want to shift 1
        // the right sub-tree will have nodes in range [index + 1, end]

        // Now relink the left and right to the node
        node.setLeft(left);
        node.setRight(right);

        if (left != null) {
            left.setParent(node);
        }

        if (right != null) {
            right.setParent(right);
        }

        return node;
    }

    public void construct(E[] in, E[] pre) {
        if (in == null || pre == null) throw new IllegalArgumentException("In or pre are null");

        if (in.length != pre.length) throw new IllegalArgumentException("In and pre must have the same length");

        if (in.length == 0) {
            this.root = null;
            this.size = 0;
            return;
        }

        // BUG: I forgot to put the this.root and i didn't get a tree... bruh
        this.root = constructHelper(in, 0, in.length - 1, pre, 0, pre.length - 1);
    }

    public void rootToLeafPathsHelper(Position<E> p, List<E> currPath, List<List<E>> res) {
        if (p == null) return;

        // Add the current node to our path
        currPath.add(p.getElement());

        // if leaf, save and copy to current path
        if (left(p) == null && right(p) == null) {
            // copy the array in our main res array
            res.add(new ArrayList<>(currPath));
        }

        else {
            // explore the left and right
            if (left(p) != null) {
                rootToLeafPathsHelper(left(p), currPath, res);
            }

            if (right(p) != null) {
                rootToLeafPathsHelper(right(p), currPath, res);
            }
        }

        // backtrack (meaning we start from the end
        currPath.remove(currPath.size() - 1);

        // QUESTION: why do we backtrack?
        // because we've reached a leaf and we want to go back to the parent
    }

    public List<List<E>> rootToLeafPaths() {
        List<List<E>> res = new ArrayList<>();
        // Originally I had isEmpty() and it was printing the empty array
        // But changing it to root() == null made it work
        if (root() == null) return res;

        List<E> currPath = new ArrayList<>();
        rootToLeafPathsHelper(root(), currPath, res);

        return res;
    }

    public static BigInteger fibSeq(int n) {
        if (n <= 0) return BigInteger.valueOf(0);

        else return fibSeq(n - 1).add(fibSeq(n - 2));
    }

    public static BigInteger tribSeq(int n) {
        if (n <= 0) return BigInteger.valueOf(0);
        if (n == 1) return BigInteger.valueOf(1);
        if (n == 2) return BigInteger.valueOf(1);

        else return tribSeq(n - 1).add(tribSeq(n - 2)).add(tribSeq(n - 3));
    }

    // 91 function
    public static int NintyOneFunction(int n) {
        if (n > 100)
            return n - 10;
        else return NintyOneFunction(NintyOneFunction(n + 11));
    }

    public static int Foo(int x) {
        if (x / 2 == 0) return 0;
        else {
            System.out.println("Result of Foo is: " + x % 2);
            return Foo(x / 2);
        }
    }

    // FOR QUESTION 10
    // Write method to print all leaf nodes from left to right
    public static void leafNodeLeftToRight() {

    }


    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt.createLevelOrder(arr);
        System.out.println("Create Level Order");
        System.out.println(bt.toBinaryTreeString());

        // Inorder and preorder

        Integer[] inorder = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
        };

        Integer[] preorder = {
                18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16,
                17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30
        };

        LinkedBinaryTree<Integer> bt1 = new LinkedBinaryTree<>();
        bt1.construct(inorder, preorder);
        System.out.println("Inorder/Preorder Tree");
        System.out.println(bt1.toBinaryTreeString());

        // Construct random binary tree
        LinkedBinaryTree<Integer> bt2 = new LinkedBinaryTree<>();
        bt2 = makeRandom(10);
        System.out.println("Make Random Tree");
        System.out.println(bt2.toBinaryTreeString());

        // create random binary trees size n = [50, 5000]
        System.out.println("Create random binary trees size n = [50, 5000]");
        LinkedBinaryTree<Integer> bt3 = new LinkedBinaryTree<>();
        int numSizes = 100;
        double[] avgContainer = new double[numSizes];
        int[] nVals = new int[numSizes];
        int index = 0;
        for (int i = 50; i <= 5000; i += 50) {
            int sum = 0;
            for (int j = 0; j < 100; j++) {
                LinkedBinaryTree<Integer> dumTree = makeRandom(i);
                int h = dumTree.height();
                sum += h;
            }

            double avg = sum / 100.0;
            nVals[index] = i;
            avgContainer[index] = avg;
            index++;
        }

        for (double j : avgContainer) {
            System.out.println("Avg " + j);
        }


        // FOR RECURSION LAB
//        final long LIMIT_NS = TimeUnit.MINUTES.toNanos(1);
//
//        for (int n = 1; n <= 100; n++) {
//            long start = System.nanoTime();
//
//            BigInteger result = fibSeq(n);   // call your recursion here
//
//            long elapsed = System.nanoTime() - start;
//
//            System.out.printf(
//                    "n=%d | time=%8.3f ms | fib(n)=%s%n",
//                    n,
//                    elapsed / 1_000_000.0,
//                    result.toString()
//            );
//
//            if (elapsed > LIMIT_NS) {
//                System.out.println("Exceeded 1 minute at n=" + n + " (breaking).");
//                break;
//            }
//        }

        for (int i = 0; i < 9; ++i) {
            System.out.println("Trib seq: " + tribSeq(i).toString());
        }

        System.out.println("Result of the 91 Function: " + NintyOneFunction(87));

        // Call Foo function
        System.out.println(Foo(2468));

        LinkedBinaryTree<String> leafTest = new LinkedBinaryTree<>();
        String[] arr1 = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        leafTest.createLevelOrder(arr1);
        List<List<String>> leafNodes = leafTest.rootToLeafPaths();
        List<String> leaves = new ArrayList<>();
        for (List<String> leafNode : leafNodes) {
            String lastElement = leafNode.get(leafNode.size() - 1);
            leaves.add(lastElement); // get the last item in the leafNode array
        }
        System.out.println("Leaf Test:" + leaves);

    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        // TODO
        // check if the tree is null
        if (root != null) throw new IllegalStateException("Root is not null!");

        Node<E> newNode = new Node<>(e, null, null, null);
        root = newNode;
        // if this is not true, trhow an error

        // then set the size to be 1

        if (size() == 0) {
            size = 1;
        }

        else {
            throw new IllegalStateException("The tree is not null!");
        }

        return newNode;

    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        // create a node using p
        Node<E> n = (Node<E>) p;
        // check if p is not null, meaning that it is not a root and throw an exception
        if (n.getLeft() != null) throw new IllegalArgumentException("Node already has left child");
        // position the created node to the left
        Node<E> child  = createNode(e, n, null, null);
        n.setLeft(child);
        // set the size of this node to be 1
        this.size++;
        // return the node
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        // create a node using p
        Node<E> n = (Node<E>) p;
        // check if p is not null, meaning that it is not a root and throw an exception
        if (n.getRight() != null) throw new IllegalArgumentException("Node already has right child");
        // position the created node to the left
        Node<E> child  = createNode(e, n, null, null);
        n.setRight(child);
        // set the size of this node to be 1
        this.size++;
        // return the node
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        // is the position valid?
        Node<E> myNode = validate(p);
        E oldElem = myNode.getElement();
        myNode.setElement(e);
        return oldElem;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        // TODO
        Node<E> myNode = validate(p); // check if p is a valid position

        // check if p is a leaf
        if (myNode.getLeft() != null || myNode.getRight() != null) {
            throw new IllegalArgumentException("P is not a leaf!");
        }

        if (t1 != null && t1.root != null) {
            // set the left to be the root
            myNode.setLeft(t1.root);
            t1.root.setParent(myNode);
            this.size += t1.size;
            t1.root = null;
            t1.size = 0;
        }

        if (t2 != null && t2.root != null) {
            myNode.setRight(t2.root);
            t2.root.setParent(myNode);
            this.size += t2.size;
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        // TODO
        Node<E> myNode = validate(p);
        E rm = myNode.getElement();

        Node<E> left = myNode.getLeft();
        Node<E> right = myNode.getRight();

        // check if its the root
        if (left != null && right != null){ //hmm, its a leaf node; easy peasy
            throw new IllegalArgumentException("Both left and right have children!");
        }

        // Now check if we're left or right
        Node<E> child;
        if (left != null) {
            child = left;
        }
        else child = right;
        Node<E> parent = myNode.getParent();

        // we want to reconnect the right node now
        if (parent == null) { // are we at the top of the tree?
            root = child;
            if (child != null)
                child.setParent(null);
        }

        // Relink so that the child is now in the tree in place of the removed node
        else {
            // Now set the parent's leaf node to be this child node
            // if the removed nodes parent is left
            if (parent.getLeft() == myNode) {
                parent.setLeft(child);
            }

            else if (parent.getRight() == myNode) {
                parent.setRight(child);
            }

            else {
                throw new IllegalArgumentException("Position is not right");
            }
        }

        size--;
        // Ensure we have a single parent node with no left or right
        // []
        // / \
        // It's detatching the removal node from the tree
        myNode.setParent(myNode);
        myNode.setLeft(null);
        myNode.setRight(null);

        return rm;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        // TODO
        root = createLevelOrderHelper(l, root, 0);
    }

    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> p, int i) {
        // TODO
        // think that each level is an array
        if (i >= l.size()) return null;

        if (l.get(i) == null) return null;

        Node<E> n = createNode(l.get(i), p, null, null);
        n.left  = createLevelOrderHelper(l, n, 2 * i + 1);
        n.right = createLevelOrderHelper(l, n, 2 * i + 2);
        ++size;
        return n;
    }


    public void createLevelOrder(E[] arr) {
        root = null;
        size = 0;
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        // TODO
        if (i >= arr.length) return null;

        if (arr[i] == null) return null;

        Node<E> n = createNode(arr[i], p, null, null);
        n.left  = createLevelOrderHelper(arr, n, 2 * i + 1);
        n.right = createLevelOrderHelper(arr, n, 2 * i + 2);
        ++size;
        return n;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    // Write binary tree from level order representation

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }
}

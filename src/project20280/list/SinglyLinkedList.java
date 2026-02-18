package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            // TODO
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            // TODO
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            // TODO
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        // TODO
        int count = 0;
        Node<E> curr = head;

        while (curr != null) {
            count++;
            curr = curr.next;
        }

        return count;
    }

    //@Override
    public boolean isEmpty() {
        // TODO
        if (head == null)
            return true;
        else
            return false;
    }

    @Override
    public E get(int position) {
        // TODO
        Node<E> curr = head;

        if (position > size()) {
            return null;
        }
        for (int i = 0; i < position; ++i) {
            curr = curr.next;
        }

        return curr.element;
    }

    @Override
    public void add(int position, E e) {
        // TODO
        Node<E> newest = new Node<E>(e, null); // node will eventually be the tail
        Node<E> curr = head;

        if (position > size()) {
            return;
        }

        if (head == null) {
            head = newest;
        }

        else {
            for (int i = 0; i < position; i++) {
                if (i == position - 1) {
                    Node<E> temp = curr.next;
                    curr.next = newest;
                    newest.next = temp;
                    size++;
                    break;
                }

                curr = curr.next;
            }
        }
    }


    @Override
    public void addFirst(E e) {
        // TODO
        Node<E> temp = head;
        head = new Node<E>(e, temp);
        size++;
    }

    @Override
    public void addLast(E e) {
        if (head == null) {
            head = new Node<E>(e, null);;
        }

        else{
            Node<E> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node<E>(e, null);
        }
    }

    @Override
    public E remove(int position) {
        if (position == 0) {
            Node<E> temp = head;
            head = head.next;
            return temp.element;
        }

        if (0 > position || position >= size())
            return null;

        Node<E> curr = head;
        for (int i = 0; i < position - 1; i++) {
            curr = curr.next;
        }

        // [1] - [2] - [3]
        Node<E> temp = curr.next;
        curr.next = temp.next;

        return temp.element;
    }

    @Override
    public E removeFirst() {
        // TODO
        if (head == null) return null;

        Node<E> temp = new Node<E>(head.element, null);
        head = head.next;

        return temp.element;
    }

    @Override
    public E removeLast() {
        // TODO

        if (head == null) {
            return null;
        }

        if (head.next == null) {
            Node<E> temp = head;
            head = null;
            return temp.element;
        }

        Node<E> curr = head;
        while (curr.next.next != null) {
            curr = curr.next;
        }

        Node<E> temp = curr.next;
        curr.next = curr.next.next;

        return temp.element; // temp
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        // LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        ll.removeLast();
        ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

    }
}

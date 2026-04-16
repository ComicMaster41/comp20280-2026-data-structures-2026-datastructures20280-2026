package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public E get(int i) {
        // TODO
        if (size <= 0 || i >= size || i < 0) return null;

        else {
            Node<E> curr = tail.next; // this is the head
            for (int j = 0; j < i; j++) {
                curr = curr.next;
            }

            return curr.getData();
        }

    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        // TODO
        Node<E> temp = new Node<E>(e, null);

        if (i < 0 || i > size) {
            return;
        }

        if (tail == null) {
            temp.next = temp;
            tail = temp;
            size = 1;
            return;
        }

        // if head
        if (i == 0){
            temp.next = tail.next;
            tail.next = temp;
            size++;
            return;
        }

        // if empty
        if (tail == null) {
            temp.next = temp; // point to itself
            tail = temp;
            size++;
        }

        else {
            // if inbetween
            Node<E> curr = tail.next; // this is the head
            for (int j = 0; j < i - 1; j++) { // get the node right before the one we want to place
                curr = curr.next;
            }

            temp.next = curr.next; // so whats after current node is what we inset
            curr.next = temp;
            if (curr == tail) {
                tail = temp;
            }

            size++;
        }
    }

    @Override
    public E remove(int i) {
        // TODO
        // if head
        if (i < 0 || i >= size){ // i == size is one step past the actual size (past tail)
            return null;
        }

        if (tail == null) return null;

        // if we want to remove the node at the beginning
        Node<E> head = tail.next;
        if (i == 0) {
            if (size == 1) { // do we just have a single node H - 1 - T
                tail = null;
            }

            else {
                tail.next = head.next;
            }
            size--;
            return head.getData();

        }

        // if inbetween
        else {
            Node<E> curr = tail.next; // this is the head
            for (int j = 0; j < i - 1; j++) { // get the node right before the one we want to place
                curr = curr.next;
            }

            Node<E> temp = curr.next; // This is the one we remove
            curr.next = temp.next; // this is setting 2 to become 2, for example

            if (tail == temp) {
                tail = curr;
            }

            size--;
            return temp.getData();
        }
    }

    public void rotate() {
        // TODO
        if (tail != null) tail = tail.next;
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        // TODO
        return remove(0);
    }

    @Override
    public E removeLast() {
        // TODO
        if (size == 0) return null;
        return remove(size - 1);
    }

    @Override
    public void addFirst(E e) {
        // TODO
        add(0, e);
    }

    @Override
    public void addLast(E e) {
        // TODO
        add(size, e);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}

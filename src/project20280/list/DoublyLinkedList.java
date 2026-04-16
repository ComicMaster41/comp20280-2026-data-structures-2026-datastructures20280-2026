package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    // Traverse the list
    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        // TODO
        Node<E> curr = new Node<E>(e, null, null);
        curr.prev = pred;
        curr.next = succ;

        pred.next = curr;
        succ.prev = curr;

        size++;
    }

    @Override
    public int size() {
        // TODO
        return size;
    }

    @Override
    public boolean isEmpty() {
        // TODO
        if (size == 0)
            return true;

        else
            return false;
    }

    @Override
    public E get(int i) {
        // TODO

        if (size <= 0 || i < 0 || i >= size) return null;

        Node<E> curr = head.next;
        for (int j = 0; j < i; ++j) {
            curr = curr.next;
        }
        return curr.getData();
    }

    @Override
    public void add(int i, E e) {
        // TODO
        if (i > size || size <= 0) return;
        else {
            Node<E> curr = head.next;
            for (int j = 0; j < i; ++j) {
                curr = curr.next;
            }
            addBetween(e, curr.prev, curr);
        }
    }

    @Override
    public E remove(int i) {
        // TODO
        Node<E> curr = head;
        for (int j = 0; j < i; ++j) {
            curr = curr.next;
        }
        // return curr.getData();
        // [H] - [1] - [2] - [3] - [T]
        Node<E> temp = curr;
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
        return temp.getData();
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

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
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        // TODO
        n.prev.next = n.next;
        n.next.prev = n.prev;

        E data = n.getData();
        n.prev = null;
        n.next = null;

        size--;
        return n.getData();
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        // TODO
        if (size == 0) {
            // [H] - [T]
            return null;
        }

        else {
            return tail.prev.getData();
        }
    }

    @Override
    public E removeFirst() {
        // TODO
        if (size == 0)
            return null;
        else {
            // [H] - [1] - [T]
            Node<E> first = head.next;      // first real node
            Node<E> second = first.next;    // node after it (could be tail)

            head.next = second;
            second.prev = head;
            size--;
            return first.getData();
        }
    }

    @Override
    public E removeLast() {
        // TODO
        if (size == 0) {
            return null;
        }

        else {
            // grab the last
            Node<E> last = tail.prev;
            // let the node

            last.prev.next = tail;
            tail.prev = last.prev;

            // let the node before now point to the tail
            size--;
            return last.getData();
        }

    }

    @Override
    public void addLast(E e) {
        // TODO
        Node<E> last = tail.prev;
        Node<E> temp = new Node<E>(e, null, null); // prev is tail and next is last

        tail.prev = temp;
        temp.next = tail;

        temp.prev = last;
        last.next = temp;

        size++;
    }

    @Override
    public void addFirst(E e) {
        // TODO
        Node<E> temp = new Node<E>(e, head, null);
        if (head == tail) {
            head.next = temp;
            temp.prev = head;

            temp.next = tail;
            temp.prev = temp;
        }


        else {
            Node<E> temp1 = head.getNext();

            head.next = temp;
            temp.prev = head;

            temp.next = temp1;
            temp1.prev = temp;
        }

        size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        ll.last();
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}
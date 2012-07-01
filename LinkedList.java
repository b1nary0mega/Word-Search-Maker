/*
 * LinkedList.java
 */

/**
 * A linked list implementation using a single linked
 * generic node.
 *
 * This code was developed in-class with student participation.
 * 
 * @author Sean Strout
 * @author James Aylesworth
 */
public class LinkedList<T> implements List<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public boolean add(T element) {
        Node<T> node = new Node<T>(element, null);

        if (head == null) {
            head = node;
        } else {
            tail.setNext(node);
        }

        tail = node;
        size++;

        return true;
    }

    public boolean add(int index, T element) throws IndexOutOfBoundsException {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> prev = getNthNode(index - 1);
        Node<T> next = head;
        if (prev != null) next = prev.getNext();

        Node<T> node = new Node<T>(element, next);

        // add to head
        if (prev == null) head = node;
        else prev.setNext(node);

        // add to tail
        if (next == null) tail = node;

        size++;

        return true;
    }

    public Node<T> getNthNode(int index) {
        Node<T> iter = null;

        if (index >= 0) {
            iter = head;
            for (int i = 0; i < index; i++) {
                iter = iter.getNext();
            }
        }
        return iter;

    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public boolean contains(T element) {
        boolean found = false;

        Node<T> iter = head;
        while (iter != null) {
            if (iter.getData().equals(element)) {
                found = true;
                break;
            }
            iter = iter.getNext();
        }

        return found;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        return getNthNode(index).getData();
    }

    public int indexOf(T element) {
        int index = 0;
        Node<T> iter = head;
        while (iter != null) {
            if (element.equals(iter.getData())) {
                break;
            }
            iter = iter.getNext();
            index++;
        }

        if (iter == null) {
            index = -1;
        }

        return index;
    }

    // remove from index position
    public boolean remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<T> prev = getNthNode(index-1);
        Node<T> corpse = head;
        if (prev != null) corpse = prev.getNext();

        // deleting the head which is the corpse
        if (prev == null) head = corpse.getNext();
        // deleting non-head
        else prev.setNext(corpse.getNext());

        // was the corpse the tail?
        if (corpse == tail) tail = prev;

        size--;
        return true;
    }

    // remove the first occurrence of an element
    public boolean remove(T element) {
        boolean found = false;
        Node<T> prev = null;
        Node<T> corpse = head;

        while (corpse != null && !corpse.getData().equals(element)) {
            prev = corpse;
            corpse = corpse.getNext();
        }

        if (corpse != null) {
            found = true;
            // similar code to remove(index)
            if (prev == null) head = corpse.getNext();
            else prev.setNext(corpse.getNext());

            if (corpse == tail) tail = prev;
            size--;
        }

        return found;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String ret = "[";
        Node<T> iter = head;
        while (iter != null) {
            ret += iter.getData();
            if (iter.getNext() != null) {
                ret += ", ";
            }
            iter = iter.getNext();
        }
        ret += "]";
        return ret;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 10; i < 100; i+=10) {
            list.add(i);
        }
    }
}
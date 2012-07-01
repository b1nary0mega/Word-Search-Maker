/*
 * Node.java
 */

public class Node<T> {

    private T data;
    private Node<T> next;

    public Node() {
        this(null, null);
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public static void main(String[] args) {
        Node<Integer> tail = new Node<Integer>(3, null);
        Node<Integer> middle = new Node<Integer>(2, tail);
        Node<Integer> head = new Node<Integer>(1, middle);

        Node<Integer> iter = head;
        while (iter != null) {
            System.out.println(iter.getData());
            iter = iter.getNext();
        }
    }
}
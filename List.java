/*
 * List.java
 */

public interface List<T> {

    public boolean add(T element);

    public boolean add(int index, T element) throws IndexOutOfBoundsException;

    public void clear();

    public boolean contains(T element);

    public T get(int index) throws IndexOutOfBoundsException;

    public int indexOf(T element);

    public boolean remove(int index) throws IndexOutOfBoundsException;

    public boolean remove(T element);

    public int size();
}
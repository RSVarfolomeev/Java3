import java.util.Arrays;
import java.util.NoSuchElementException;

//    2. Создать класс для реализации дека.

public class DoubleSideQueue<T> {
    private T[] list;
    private int size;
    private final int DEFAULT_CAPACITY = 10;
    private int begin;
    private int end;

    public DoubleSideQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        list = (T[]) new Object[capacity];
    }

    public DoubleSideQueue() {
        list = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public T peekFront() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[begin];
    }

    //    Добавлено к двухсторонней очереди.
    public T peekEnd() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = end - 1;
        if (index == -1) {
            index = list.length - 1;
        }
        return list[index];
    }

    public T removeRight() {
        T temp = peekFront();
        size--;
        list[begin] = null;
        begin = nextIndex(begin);
        return temp;
    }

    //    Добавлено к двухсторонней очереди.
    public T removeLeft() {
        T temp = peekEnd();
        size--;

        int index = end - 1;
        if (index == -1) {
            index = list.length - 1;
        }

        list[index] = null;
        end = previousIndex(end);
        return temp;
    }

    public void insertLeft(T item) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        size++;
        list[end] = item;
        end = nextIndex(end);
    }

    //    Добавлено к двухсторонней очереди.
    public void insertRight(T item) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        size++;

        int index = begin - 1;
        if (index == -1) {
            index = list.length - 1;
        }

        list[index] = item;
        begin = previousIndex(begin);
    }

    private int nextIndex(int index) {
        return (index + 1) % list.length;
    }

    //    Добавлено к двухсторонней очереди.
    private int previousIndex(int index) {
        index = (index - 1) % list.length;
        if (index == -1) {
            index = list.length - 1;
        }
        return index;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == list.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(list) + " begin: " + begin + " end: " + end;
    }
}

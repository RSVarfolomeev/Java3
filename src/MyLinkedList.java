import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    public ListIterator<T> listIterator() {
        return new ListIter();
    }

    public MyLinkedList() {
        first = null;
        last = null;
    }

//  2. Добавить лист итератор в MyLinkedList
    private class ListIter implements ListIterator<T> {
        Node current = new Node(null, first);

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public T next() {
            current = current.getNext();
            return current.getValue();
        }

        @Override
        public boolean hasPrevious() {
            return current.getPrev() != null;
        }

        @Override
        public T previous() {
            current = current.getPrev();
            return current.getValue();
        }

        @Override
        public int nextIndex() {
            Node currentIndex = first;
            int index = 0;
            if(current.getNext() != null) {
                while (currentIndex != null) {
                    if (currentIndex.getValue().equals(current.getNext().getValue())) {
                        return index;
                    }
                    currentIndex = currentIndex.getNext();
                    index++;
                }
            }
            return size;
        }

        @Override
        public int previousIndex() {
            Node currentIndex = first;
            int index = 0;
            if(current.getPrev() != null) {
                while (currentIndex != null) {
                    if (currentIndex.getValue().equals(current.getPrev().getValue())) {
                        return index;
                    }
                    currentIndex = currentIndex.getNext();
                    index++;
                }
            }
            return -1;
        }

        @Override
        public void remove() {
            if (first.getValue().equals(current.getValue())) {
                removeFirst();
            } else if (current == null) {
                throw new NoSuchElementException();
            } else if (current == last) {
                removeLast();
            } else {
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
                size--;
            }
        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }

    private class Iter implements Iterator<T> {
        Node current = new Node(null, first);

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public T next() {
            current = current.getNext();
            return current.getValue();
        }

//        1. Реализовать remove() для итератора
        @Override
        public void remove() {
            if (first.getValue().equals(current.getValue())) {
                removeFirst();
            } else if (current == null) {
                throw new NoSuchElementException();
            } else if (current == last) {
                removeLast();
            } else {
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
                size--;
            }
        }
    }

    private class Node {
        T value;
        Node next;
        Node prev;

        public Node(T item) {
            this.value = item;
        }

        public Node(T item, Node next) {
            this.value = item;
            this.next = next;
        }

        public Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void insertFirst(T item) {
        Node newNode = new Node(item);
        newNode.setNext(first);
        if (isEmpty()) {
            last = newNode;
        } else {
            first.setPrev(newNode);
        }
        first = newNode;
        size++;
    }

    public void insertLast(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.setPrev(last);
            last.setNext(newNode);
        }
        last = newNode;
        size++;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.getValue();
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.getValue();
    }

    public T removeFirst() {
        T oldFirst = getFirst();
        first = first.getNext();
        if (isEmpty()) {
            last = null;
        } else {
            first.setPrev(null);
        }
        size--;
        return oldFirst;
    }

    public T removeLast() {
        T oldLast = getLast();
        if (last.getPrev() != null) {
            last.getPrev().setNext(null);
        } else {
            first = null;
        }
        last = last.getPrev();
        size--;
        return oldLast;
    }

    public final int indexOf(T item) {
        Node current = first;
        int index = 0;
        while (current != null) {
            if (current.getValue().equals(item)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    public boolean contains(T item) {
        return indexOf(item) > -1;
    }

    public void insert(int index, T item) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        if (index == 0) {
            insertFirst(item);
            return;
        }
        if (index == size) {
            insertLast(item);
            return;
        }
        Node current = first;
        int i = 0;
        while (i < index - 1) {
            current = current.getNext();
            i++;
        }
        Node newNode = new Node(item);
        newNode.setNext(current.getNext());
        newNode.setPrev(current);
        current.setNext(newNode);
        newNode.getNext().setPrev(newNode);
        size++;
    }

    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        Node current = first;
        int i = 0;
        while (i < index) {
            current = current.getNext();
            i++;
        }
        T temp = current.getValue();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
        return temp;
    }

    public boolean remove(T item) {
        if (isEmpty()) {
            return false;
        }
        if (first.getValue().equals(item)) {
            removeFirst();
            return true;
        }
        Node current = first;
        while (current != null && !current.getValue().equals(item)) {
            current = current.getNext();
        }
        if (current == null) {
            return false;
        }
        if (current == last) {
            removeLast();
            return true;
        }
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
        return true;
    }

    @Override
    public String toString() {
        Node current = first;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.getValue() + ", ");
            current = current.getNext();
        }
        if (!isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
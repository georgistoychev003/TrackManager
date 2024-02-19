package lists;

import model.Station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class DoublyLinkedList<E> implements MyList<E> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    /**
     * Node represents an individual node in my linked list implementation.
     * I preferred to use a nested class to encapsulate the data and links together instead of creating the node in a separate class.
     */

    private class Node<T> {
        Node<T> next;
        Node<T> previous;
        T data;

        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * A nested class to represent individual elements in the linked list.
     */
    private class LinkElement<T> {
        DoublyLinkedList<T>.LinkElement<T> next;
        T data;

        public LinkElement(T data) {
            this.data = data;
        }

        /**
         * Recursively disconnects the link element.
         */
        public void disconnect() {
            if (next != null) {
                next.disconnect();
            }
            next = null;
        }
    }


    @Override
    public E searchByName(String name) {
        if (!(head.data instanceof Station)) {
            throw new UnsupportedOperationException("Only for lists of Station.Station objects.");
        }
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            Station station = (Station) iterator.next();
            if (station.getName().equals(name)) {
                return (E) station;
            }
        }
        return null; // will return null if the station is not found
    }

    /**
     * This method returns the data at a given index.
     * I've created a helper function 'getNodeAtIndex' to separat the logic of
     * retrieving the node.
     */

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return getNodeAtIndex(index).data;
    }


    /**
     * A helper method to retrieve the node at a specified index.
     * This method is 'private' as I felt it was an internal operation that doesnt need to
     * be exposed to the user.
     */

    private Node<E> getNodeAtIndex(int index) {
        Node<E> nodeAtIndex = head;
        for (int i = 0; i < index; i++) {
            nodeAtIndex = nodeAtIndex.next;
        }
        return nodeAtIndex;
    }



    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
        return true;
    }


    @Override
    public boolean containsAll(MyList<E> elements) {
        for (E element : elements) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method checks if my list has any of the items from another given list.
     */
    @Override
    public boolean containsSome(MyList<E> elements) {
        for (E element : elements) {
            if (contains(element)) {
                return true;
            }
        }
        return false;
    }


    /**
     * With this method, I wanted to provide a way to go through my list one item at a time.
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            private Node<E> lastAccessed = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                E data = current.data;
                lastAccessed = current; // Set the lastAccessed to the current node
                current = current.next;
                return data;
            }

            @Override
            public void remove() {
                if (lastAccessed == null) {
                    throw new IllegalStateException();
                }

                if (lastAccessed.previous != null) {
                    lastAccessed.previous.next = lastAccessed.next;
                } else {
                    head = lastAccessed.next;
                }

                if (lastAccessed.next != null) {
                    lastAccessed.next.previous = lastAccessed.previous;
                } else {
                    tail = lastAccessed.previous;
                }

                size--;
                lastAccessed = null;
            }
        };
    }



    @Override
    public boolean remove(E e) {
        Node<E> current = head;

        while (current != null) {
            if (current.data.equals(e)) {
                if (current.previous != null) {
                    current.previous.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.previous = current.previous;
                } else {
                    tail = current.previous;
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }


    @Override
    public boolean contains(E e) {
        for (E item : this) {
            if (item.equals(e)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }



    /**
     * This method gives the number of items (length) in my list.
     */

    @Override
    public int length() {
        return size;
    }



    /**
     * This method converts the doubly linked list to an ArrayList.
     * It traverses through each element of the linked list and adds it to an ArrayList.
     */
    public ArrayList<E> toArrayList() {
        ArrayList<E> list = new ArrayList<>();
        Node<E> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }


    public void clear(){
        head=tail=null;
        size=0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DoublyLinkedList {");

        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }

//    public Node<E> getHead() {
//        return this.head;
//    }
//
//    public Node<E> getNext(Node<E> node) {
//        return node.next;
//    }
//
//    public Node<E> getPrev(Node<E> node) {
//        return node.previous;
//    }
//
//    public E getData(Node<E> node) {
//        return node.data;
//    }

}
package heap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<T>> {
    private final Class<T> clazz;
    private T[] items;
    private int size;


    public MinHeap(Class<T> clazz, int initialSize) {
        this.clazz = clazz;
        this.items = (T[]) Array.newInstance(clazz, initialSize);
    }

    // Returns the smallest element (root of the min heap) without removing it
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty list");
        }
        return items[0];
    }

    // Checks if the heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Adds an element to the heap
    public void push(T data) {
        // If the array is full, resize it
        if (size == items.length) {
            remap();
        }
        // Add the data at the end of the heap (array)
        items[size] = data;
        percolateUp(size);
        size++;
    }

    // Resizes the array when it's full
    private void remap() {
        int newSize = Math.max(items.length * 2, 1);
        T[] newItems = (T[]) Array.newInstance(
                items.length > 0 ? items[0].getClass() : clazz, newSize
        );
        if (size > 0) {
            System.arraycopy(items, 0, newItems, 0, size);  // Copy old items to new array if size > 0
        }
        items = newItems;
    }


    // Removes the smallest element (root of the min heap) and returns it
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove an item from an empty list");
        }
        T result = items[0];   // Store root value
        size--;
        // Replace root with the last element in the heap
        items[0] = items[size];
        // Ensure the heap property is maintained by adjusting the position of the root if necessary
        percolateDown(0);
        return result;
    }

    // Ensures the heap property is maintained by moving an element downwards
    private void percolateDown(int idex) {
        int leftChildIdx = 2 * idex + 1;
        int rightChildIdx = 2 * idex + 2;

        if (leftChildIdx >= size) {
            return; // Node is a leaf and doesn't need to be moved.
        }

        // Assume the left child is the smallest
        int smallestChildIdx = leftChildIdx;

        // Check if the right child is smaller than the left child
        if (rightChildIdx < size && items[rightChildIdx].compareTo(items[leftChildIdx]) < 0) {
            smallestChildIdx = rightChildIdx;
        }

        // Swap the current node with its smallest child if the child is smaller
        if (items[idex].compareTo(items[smallestChildIdx]) > 0) {
            swap(idex, smallestChildIdx);
            percolateDown(smallestChildIdx);
        }
    }

    // Ensures the heap property is maintained by moving an element upwards
    private void percolateUp(int idx) {
        if (idx == 0) {
            return; // Root node, no parent to compare with
        }

        int parentIdx = (idx - 1) / 2;

        // If the current node is smaller than its parent, swap them
        if (items[idx].compareTo(items[parentIdx]) < 0) {
            swap(idx, parentIdx);
            percolateUp(parentIdx);  // Recurse on the parent node
        }
    }

    // method to swap two elements in the array
    private void swap(int i, int j) {
        T temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    // Returns the number of elements in the heap
    public int getSize() {
        return size;
    }

    public boolean contains(T data) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(data)) {
                return true;
            }
        }
        return false;
    }

    public void remove(T data) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (items[i].equals(data)) {
                index = i;
                break;
            }
        }

        if (index == -1) return;

        items[index] = items[size - 1];  // replace the item with the last item in the heap
        size--;  // reduce the size of the heap


        percolateDown(index);
        percolateUp(index);
    }


    //used for the part where i identify the stations that fall within a rectangle
    public void update(T data) {
        // Find the index of data in the heap
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (items[i].equals(data)) {
                index = i;
                break;
            }
        }
        if (index == -1) return;

        // Update the position of data in the heap
        percolateUp(index);
        percolateDown(index);
    }

    // i need this method to retun a copy of the heap's elements without altering the heap, i call this method
    // in my MCSTPrim to obtain the elements of my priority queue which in that case is my MinHeap
    public List<T> getElements() {
        List<T> elements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            elements.add(items[i]);
        }
        return elements;
    }

    // a method that generates a visual representation of the heap
    public String toWebGraph() {
        StringBuilder sb = new StringBuilder();
        sb.append("diGraph MinHeap {\n");

        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // If left child exists, add a connection to it
            if (left < size) {
                sb.append("    ").append(items[i]).append(" -> ").append(items[left]).append(";\n");
            }

            // If right child exists, add a connection to it
            if (right < size) {
                sb.append("    ").append(items[i]).append(" -> ").append(items[right]).append(";\n");
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MinHeap {");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(items[i]);
        }

        sb.append("}");
        return sb.toString();
    }
}


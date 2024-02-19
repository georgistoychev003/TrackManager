package sorting;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Station.InsertionSort class to sort an ArrayList of any type using the Insertion Station.Sort algorithm.
 */
public class InsertionSort {


    public static <T> void sort(ArrayList<T> list, Comparator<? super T> comparator) {
        // Iterate over the ArrayList from the second element to the end.
        for (int i = 1; i < list.size(); i++) {
            // currentElement holds the current element to be compared.
            T currentElement = list.get(i);
            // Start comparing with the previous element.
            int j = i - 1;

            // If the current element is less than the compared element,
            // shift the compared element to the right.
            while (j >= 0 && comparator.compare(currentElement, list.get(j)) < 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            // Insert the current element at its correct position.
            list.set(j + 1, currentElement);

        }
    }
}
package sorting;

import lists.DoublyLinkedList;

import java.util.Comparator;

public class MergeSort<E> {

    public static <E> void mergeSort(DoublyLinkedList<E> list, Comparator<? super E> comparator) {
        if (list.length() > 1) {

            DoublyLinkedList<E> leftList = new DoublyLinkedList<>();
            DoublyLinkedList<E> rightList = new DoublyLinkedList<>();
            int middle = list.length() / 2;

            int index = 0;
            for (E item : list) {
                if (index < middle)
                    leftList.add(item);
                else
                    rightList.add(item);
                index++;
            }

            mergeSort(leftList, comparator);
            mergeSort(rightList, comparator);
            merge(list, leftList, rightList, comparator);
        }
    }

    private static <E> void merge(DoublyLinkedList<E> result, DoublyLinkedList<E> left, DoublyLinkedList<E> right, Comparator<? super E> comparator) {
        int leftIndex = 0, rightIndex = 0;

        result.clear();

        while (leftIndex < left.length() && rightIndex < right.length()) {
            E leftElement = left.get(leftIndex);
            E rightElement = right.get(rightIndex);

            if (comparator.compare(leftElement, rightElement) <= 0) {
                result.add(leftElement);
                leftIndex++;
            } else {
                result.add(rightElement);
                rightIndex++;
            }
        }

        while (leftIndex < left.length()) {
            result.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.length()) {
            result.add(right.get(rightIndex));
            rightIndex++;
        }
    }
}
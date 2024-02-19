package sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestInsertionSort {

    private ArrayList<Integer> integerList;
    private ArrayList<String> stringList;
    private Comparator<Integer> integerComparator;
    private Comparator<String> stringComparator;

    @BeforeEach
    void setUp() {
        integerList = new ArrayList<>();
        stringList = new ArrayList<>();
        integerComparator = Comparator.naturalOrder();
        stringComparator = Comparator.naturalOrder();
    }

    @Test
    void givenFourRandomNumbersInAnArrayListTestSortIntegerList() {
        integerList.add(30);
        integerList.add(1);
        integerList.add(44);
        integerList.add(2);

        InsertionSort.sort(integerList, integerComparator);

        List<Integer> expected = List.of(1, 2, 30, 44);
        assertEquals(expected, integerList);
    }

    @Test
    void givenFourWordsInAnArrayListtestSortStringListInAlphabeticOrder() {
        stringList.add("angular");
        stringList.add("bison");
        stringList.add("curiosity");
        stringList.add("database");

        InsertionSort.sort(stringList, stringComparator);

        List<String> expected = List.of("angular", "bison", "curiosity", "database");
        assertEquals(expected, stringList);
    }

    @Test
    void givenEmptyIntegerListShouldNotThrowException() {
        InsertionSort.sort(integerList, integerComparator);
        assertEquals(List.of(), integerList);
    }
    @Test
    void givenEmptyStringListShouldNotThrowException() {
        InsertionSort.sort(stringList, stringComparator);
        assertEquals(List.of(), stringList);
    }

    @Test
    void givenOneElementInIntegerListShouldNotChangeList() {
        integerList.add(4090);
        InsertionSort.sort(integerList, integerComparator);
        List<Integer> expected = List.of(4090);
        assertEquals(expected, integerList);
    }

    @Test
    void givenOneElementInStringListShouldNotChangeList() {
        stringList.add("complexdata");
        InsertionSort.sort(stringList, stringComparator);
        List<String> expected = List.of("complexdata");
        assertEquals(expected, stringList);
    }

    @Test
    void givenDescendingOrderIntegerListShouldSortInAscendingOrder() {
        integerList.add(5);
        integerList.add(4);
        integerList.add(3);
        integerList.add(2);
        integerList.add(1);

        InsertionSort.sort(integerList, integerComparator);

        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        assertEquals(expected, integerList);
    }

    @Test
    void givenDescendingOrderStringListShouldSortInAlphabeticOrder() {
        stringList.add("zero");
        stringList.add("Android");
        stringList.add("business");
        stringList.add("curiosity");

        InsertionSort.sort(stringList, stringComparator);

        List<String> expected = List.of("Android", "business", "curiosity", "zero");
        assertEquals(expected, stringList);
    }

    @Test
    void givenNullListShouldThrowException() {
        assertThrows(NullPointerException.class, () -> InsertionSort.sort(null, integerComparator));
    }


    @Test
    void givenLargeIntegerListShouldSortCorrectly() {
        for (int i = 6745; i >= 1; i--) {
            integerList.add(i);
        }

        InsertionSort.sort(integerList, integerComparator);

        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= 6745; i++) {
            expected.add(i);
        }
        assertEquals(expected, integerList);
    }


}

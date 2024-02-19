package sorting;
import lists.DoublyLinkedList;

import java.util.Comparator;
import lists.DoublyLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestMergeSort {

    private DoublyLinkedList<String> list;
    private Comparator<String> comparator;

    @BeforeEach
    public void setUp() {
        list = new DoublyLinkedList<>();
        comparator = String::compareTo;
    }

    @Test
    public void testMergeSortEmptyList() {
        MergeSort.mergeSort(list, comparator);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testMergeSortSingleElement() {
        list.add("A");
        MergeSort.mergeSort(list, comparator);
        assertEquals(1, list.length());
        assertEquals("A", list.get(0));
    }

    @Test
    public void testMergeSortTwoElements() {
        list.add("B");
        list.add("A");
        MergeSort.mergeSort(list, comparator);
        assertEquals(2, list.length());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    public void testMergeSortMultipleElements() {
        list.add("Cow");
        list.add("Apple");
        list.add("Buffalo");
        list.add("River");
        list.add("Sort");
        list.add("Bison");
        list.add("Mammal");
        list.add("Georgi");
        list.add("Stoychev");
        MergeSort.mergeSort(list, comparator);
        assertEquals("Apple", list.get(0));
        assertEquals("Bison", list.get(1));
        assertEquals("Buffalo", list.get(2));
        assertEquals("Cow", list.get(3));
        assertEquals("Georgi", list.get(4));
        assertEquals("Mammal", list.get(5));
        assertEquals("River", list.get(6));
        assertEquals("Sort", list.get(7));
        assertEquals("Stoychev", list.get(8));
    }

    @Test
    public void testMergeSortDuplicateElements() {
        list.add("Cow");
        list.add("Apple");
        list.add("Buffalo");
        list.add("River");
        list.add("Cow");
        MergeSort.mergeSort(list, comparator);
        assertEquals("Apple", list.get(0));
        assertEquals("Buffalo", list.get(1));
        assertEquals("Cow", list.get(2));
        assertEquals("Cow", list.get(3));
        assertEquals("River", list.get(4));

    }
    @Test
    public void testMergeSortReverseOrder() {
        list.add("Zebra");
        list.add("Yellow");
        list.add("X");
        list.add("Windows");
        MergeSort.mergeSort(list, comparator);
        assertEquals("Windows", list.get(0));
        assertEquals("X", list.get(1));
        assertEquals("Yellow", list.get(2));
        assertEquals("Zebra", list.get(3));
    }

    @Test
    public void testMergeSortSameElements() {
        list.add("And");
        list.add("And");
        list.add("And");
        list.add("And");
        MergeSort.mergeSort(list, comparator);
        assertEquals("And", list.get(0));
        assertEquals("And", list.get(1));
        assertEquals("And", list.get(2));
        assertEquals("And", list.get(3));
    }


    //I discovered with those tests that  Java's default Comparator for Strings is case-sensitive and thus it
    // prioritizes upper cases letters and words over lower case

    @Test
    public void testMergeSortCaseSensitivityUpperCaseLettersAreWithPriority() {
        list.add("a");
        list.add("B");
        list.add("A");
        list.add("b");
        MergeSort.mergeSort(list, comparator);
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("a", list.get(2));
        assertEquals("b", list.get(3));
    }

    //I discovered with those tests that  Java's default Comparator for Strings is case-sensitive and thus it
    // prioritizes upper cases letters and words over lower case
    @Test
    public void testMergeSortMixedCasesUpperCaseLettersAreWithPriority() {
        list.add("apple");
        list.add("Banana");
        list.add("grape");
        list.add("Apricot");
        MergeSort.mergeSort(list, comparator);
        assertEquals("Apricot", list.get(0));
        assertEquals("Banana", list.get(1));
        assertEquals("apple", list.get(2));
        assertEquals("grape", list.get(3));
    }


    //test with no case sensitivity this time apple goes before Apricot
    @Test
    public void testMergeSortMixedCasesWithNoCaseSensitivity() {
        DoublyLinkedList<String> doublyLinkedList = new DoublyLinkedList<>();
        Comparator<String> caseInsensitiveComparator = String::compareToIgnoreCase;

        doublyLinkedList.add("apple");
        doublyLinkedList.add("Banana");
        doublyLinkedList.add("grape");
        doublyLinkedList.add("Apricot");
        MergeSort.mergeSort(doublyLinkedList, caseInsensitiveComparator);
        assertEquals("apple", doublyLinkedList.get(0));
        assertEquals("Apricot", doublyLinkedList.get(1));
        assertEquals("Banana", doublyLinkedList.get(2));
        assertEquals("grape", doublyLinkedList.get(3));
    }


}


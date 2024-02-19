package heap;

import heap.MinHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMinHeap {
    private MinHeap<Integer> heapTest;

    @BeforeEach
    public void setUp() {
        heapTest = new MinHeap<>(Integer.class, 5);
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(heapTest.isEmpty());
    }

    @Test
    public void testPushAndPeek() {
        heapTest.push(9);
        assertEquals(Integer.valueOf(9), heapTest.peek());

        heapTest.push(58);
        assertEquals(Integer.valueOf(9), heapTest.peek());

        heapTest.push(1);
        assertEquals(Integer.valueOf(1), heapTest.peek());
    }

    @Test
    public void testRemap() {
        heapTest.push(8);
        heapTest.push(3);
        heapTest.push(44);
        heapTest.push(1);
        heapTest.push(4);
        heapTest.push(16);
        heapTest.push(15);
        assertEquals(7, heapTest.getSize());
    }

    @Test
    public void testPop() {
        heapTest.push(9);
        heapTest.push(6);
        heapTest.push(31);
        heapTest.push(3);
        heapTest.push(14);

        assertEquals(Integer.valueOf(3), heapTest.pop());
        assertEquals(Integer.valueOf(6), heapTest.pop());
        assertEquals(Integer.valueOf(9), heapTest.pop());
    }

    @Test
    public void testPushingInDescendingOrder() {
        for (int i = 333; i > 0; i--) {
            heapTest.push(i);
        }

        for (int i = 1; i <= 333; i++) {
            assertEquals(Integer.valueOf(i), heapTest.pop());
        }
    }

    @Test
    public void testPushAndPop() {
        heapTest.push(17);
        assertEquals(Integer.valueOf(17), heapTest.pop());

        heapTest.push(18);
        heapTest.push(12);
        assertEquals(Integer.valueOf(12), heapTest.pop());
        assertEquals(Integer.valueOf(18), heapTest.pop());
    }

    @Test
    public void testPopEmptyHeap() {
        assertThrows(IllegalStateException.class, () -> heapTest.pop());
    }

    @Test
    public void testPeekEmptyHeap() {
        assertThrows(IllegalStateException.class, () -> heapTest.peek());
    }



//    @Test
//    public void testToWebGraphSingleNode() {
//        heapTest.push(5);
//        String expectedGraph = "diGraph MinHeap {\n" +
//                "    5;\n" +
//                "}" ;
//        assertEquals(expectedGraph, heapTest.toWebGraph());
//    }

    @Test
    void testContains() {
        heapTest.push(247);
        heapTest.push(333);

        assertTrue(heapTest.contains(247));
        assertFalse(heapTest.contains(124));
    }

    @Test
    void testRemove() {
        heapTest.push(343);


        assertTrue(heapTest.contains(343), "Heap should contain the element before removal.");
        heapTest.remove(343);
        assertFalse(heapTest.contains(343), "Heap should not contain the element after removal.");
    }

    @Test
    void testRemoveNonExistentElement() {
        heapTest.push(341);
        heapTest.remove(222); // Element not in heap
        assertEquals(1, heapTest.getSize(), "Size should remain unchanged after trying to remove a non-existent element.");
    }

    @Test
    void testUpdate() {
        heapTest.push(3);
        heapTest.push(7);
        heapTest.push(10);
        heapTest.push(5);
        heapTest.update(7);

        assertEquals(Integer.valueOf(3), heapTest.pop(), "The smallest element should still be at the top after update.");
        heapTest.remove(7);
        assertEquals(Integer.valueOf(5), heapTest.pop(), "Next element should be 5 after removing 7.");
    }

    @Test
    void testGetElements() {
        heapTest.push(339);
        heapTest.push(225);
        heapTest.push(119);
        List<Integer> elements = heapTest.getElements();

        assertArrayEquals(new Integer[]{119, 339, 225}, elements.toArray(), "Should return a copy of the heap's elements.");
    }

    @Test
    void testToString() {
        heapTest.push(25);
        heapTest.push(5);
        heapTest.push(15);
        heapTest.push(10);


        String expected = "MinHeap {5, 10, 15, 25}";
        assertEquals(expected, heapTest.toString());
    }


    @Test
    void testGetElementsEmptyHeap() {
        List<Integer> elements = heapTest.getElements();
        assertTrue(elements.isEmpty());
    }
}

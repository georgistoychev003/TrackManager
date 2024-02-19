package trees;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trees.AVLTree;

import java.util.Comparator;

public class TestAVLTree {

    private AVLTree<Integer> tree;

    @BeforeEach
    public void setUp() {
        Comparator comparator = Comparator.naturalOrder();
        tree = new AVLTree<>(comparator);
    }

    @Test
    public void testAdd() {
        tree.add(5);
        assertEquals(5, tree.getRoot().getData());

        tree.add(10);
        tree.add(1);

        assertEquals(5, tree.getRoot().getData());
        assertEquals(10, tree.getRoot().getRight().getData());
        assertEquals(1, tree.getRoot().getLeft().getData());
    }

    @Test
    public void testBalanceAfterAdd() {
        tree.add(1);
        tree.add(2);
        tree.add(3);

        // Right rotation expected
        assertEquals(2, tree.getRoot().getData());
        assertEquals(1, tree.getRoot().getLeft().getData());
        assertEquals(3, tree.getRoot().getRight().getData());
    }

    @Test
    public void testRemove() {
        tree.add(5);
        tree.add(10);
        tree.add(1);
        tree.remove(5);

        assertEquals(10, tree.getRoot().getData());
        assertEquals(1, tree.getRoot().getLeft().getData());
    }

    @Test
    public void testBalanceAfterRemove() {
        tree.add(5);
        tree.add(10);
        tree.add(1);
        tree.add(15);
        tree.remove(1);

        // Left rotation expected
        assertEquals(10, tree.getRoot().getData());
        assertEquals(5, tree.getRoot().getLeft().getData());
        assertEquals(15, tree.getRoot().getRight().getData());
    }

    @Test
    public void testDuplicateValueNotAllowed() {
        tree.add(5);
        assertThrows(IllegalArgumentException.class, () -> tree.add(5));
    }

    @Test
    public void testIsBalanced() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        assertTrue(AVLTree.isBalanced(tree.getRoot()));

        tree.add(2);
        tree.add(1);
        assertTrue(AVLTree.isBalanced(tree.getRoot()));
    }

    @Test
    public void testDoubleRightLeftRotation() {

        tree.add(6);
        tree.add(11);
        tree.add(9);

        assertEquals(9, tree.getRoot().getData());
        assertEquals(6, tree.getRoot().getLeft().getData());
        assertEquals(11, tree.getRoot().getRight().getData());
    }

    @Test
    public void testDoubleLeftRightRotation() {

        tree.add(30);
        tree.add(20);
        tree.add(25);

        assertEquals(25, tree.getRoot().getData());
        assertEquals(20, tree.getRoot().getLeft().getData());
        assertEquals(30, tree.getRoot().getRight().getData());
    }

    @Test
    public void testBigInsertionAndRemoval() {

        for (int i = 0; i < 777; i++) {
            tree.add(i);
        }
        assertTrue(tree.isBalanced(tree.getRoot()));

        for (int i = 0; i < 444; i++) {
            tree.remove(i);
        }
        assertTrue(tree.isBalanced(tree.getRoot()));
    }

    @Test
    public void testHeightCalculation() {

        tree.add(30);
        tree.add(20);
        tree.add(40);
        tree.add(10);
        tree.add(25);
        assertEquals(3, tree.getHeight(tree.getRoot()));
    }



    @Test
    public void testWithStringDataType() {
        Comparator comparator = Comparator.naturalOrder();
        AVLTree<String> tree = new AVLTree<>(comparator);
        tree.add("angular");
        tree.add("business");
        tree.add("curiosity");

        assertEquals("business", tree.getRoot().getData());
        assertEquals("angular", tree.getRoot().getLeft().getData());
        assertEquals("curiosity", tree.getRoot().getRight().getData());
    }


    @Test
    public void testRemoveValueFromRightSubtree() {
        // Im intentionally inserting values such that the root node is 5 and it has both left and right subtrees.
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(6);
        tree.add(8);

        // here i check that 7 has the right child 8 before removal
        assertEquals(Integer.valueOf(8), tree.getRoot().getRight().getRight().getData());

        // Now, i remove 8, which will execute the line in question
        tree.remove(8);

        // and then i verify that 8 has been removed from 7's right child.
        assertNull(tree.getRoot().getRight().getRight());
    }

    @Test
    public void testContains() {
        tree.add(1);
        tree.add(3);

        assertTrue(tree.contains(1), "Tree should contain 1");
        assertTrue(tree.contains(3), "Tree should contain 3");

        assertFalse(tree.contains(2), "Tree should not contain 2");
        assertFalse(tree.contains(4), "Tree should not contain 4");
    }


    @Test
    public void testGraphvizOutput() {
        tree.add(5);
        tree.add(10);
        tree.add(1);

        String expected = "digraph AVLTree {\n" +
                "    \"5\" -> \"1\";\n" +
                "    \"5\" -> \"10\";\n" +
                "}\n";
        assertEquals(expected, tree.toGraphviz());
    }
}

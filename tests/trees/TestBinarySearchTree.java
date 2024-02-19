package trees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trees.BinarySearchTree;
import trees.BinaryTreeNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinarySearchTree {

    private BinarySearchTree<Integer> binarySearchTree;

    @BeforeEach
    public void setUp() {
        Comparator comparator = Comparator.naturalOrder();
        binarySearchTree = new BinarySearchTree<>(comparator);
    }

    @Test
    public void testAddSingleElement() {
        binarySearchTree.add(29);
        assertEquals(1, binarySearchTree.getSize());
        assertEquals(29, binarySearchTree.getRoot().getData());
    }

    @Test
    public void testAddMultipleElementsAndIdentifyTheRootTheLeftAndTheRight() {
        binarySearchTree.add(10);
        binarySearchTree.add(8);
        binarySearchTree.add(9);
        binarySearchTree.add(98);
        binarySearchTree.add(21);
        assertEquals(5, binarySearchTree.getSize());
        assertEquals(10, binarySearchTree.getRoot().getData());
        assertEquals(8, binarySearchTree.getRoot().getLeft().getData());
        assertEquals(98, binarySearchTree.getRoot().getRight().getData());
    }

    // Tests related to adding elements
    @Test
    void testAddRoot() {
        binarySearchTree.add(5);
        assertEquals(5, binarySearchTree.getRoot().getData());
    }

    @Test
    void testAddLeftChild() {
        binarySearchTree.add(8);
        binarySearchTree.add(6);
        assertEquals(6, binarySearchTree.getRoot().getLeft().getData());
    }

    @Test
    void testAddRightChild() {
        binarySearchTree.add(5);
        binarySearchTree.add(7);
        assertEquals(7, binarySearchTree.getRoot().getRight().getData());
    }

    // Tests related to tree properties
    @Test
    void testIsEmpty() {
        assertTrue(binarySearchTree.isEmpty());
    }

    @Test
    void testIsNotEmpty() {
        binarySearchTree.add(5);
        assertFalse(binarySearchTree.isEmpty());
    }

    @Test
    public void testContains() {
        binarySearchTree.add(55);
        binarySearchTree.add(2);
        binarySearchTree.add(5);
        binarySearchTree.add(6);
        assertTrue(binarySearchTree.contains(55));
        assertTrue(binarySearchTree.contains(2));
        assertTrue(binarySearchTree.contains(5));
        assertTrue(binarySearchTree.contains(6));
        assertFalse(binarySearchTree.contains(18));
        assertFalse(binarySearchTree.contains(1));
    }

    @Test
    void testContainsNonExistingElement() {
        binarySearchTree.add(10);
        assertFalse(binarySearchTree.contains(1000));
    }

    @Test
    public void testGetHeight() {
        binarySearchTree.add(5);
        assertEquals(0, binarySearchTree.getHeight());
        binarySearchTree.add(3);
        assertEquals(1, binarySearchTree.getHeight());
        binarySearchTree.add(2);
        assertEquals(2, binarySearchTree.getHeight());
        binarySearchTree.add(8);
        assertEquals(2, binarySearchTree.getHeight());
    }

    @Test
    public void testRemoveLeaf() {
        binarySearchTree.add(7);
        binarySearchTree.add(4);
        binarySearchTree.add(9);
        binarySearchTree.remove(4);
        assertNull(binarySearchTree.getRoot().getLeft());
        assertTrue(binarySearchTree.contains(7));
        assertTrue(binarySearchTree.contains(9));
        assertFalse(binarySearchTree.contains(4));
        assertNull(binarySearchTree.getRoot().getLeft());
    }

    @Test
    public void testRemoveNodeWithOneChild() {
        binarySearchTree.add(3);
        binarySearchTree.add(1);
        binarySearchTree.add(6);
        binarySearchTree.add(5);
        binarySearchTree.remove(6);
        assertNull(binarySearchTree.getRoot().getRight().getRight());
        assertTrue(binarySearchTree.contains(5));
        assertFalse(binarySearchTree.contains(6));
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        binarySearchTree.add(8);
        binarySearchTree.add(6);
        binarySearchTree.add(11);
        binarySearchTree.add(10);
        binarySearchTree.add(12);
        binarySearchTree.remove(11);
        assertEquals(12, binarySearchTree.getRoot().getRight().getData());
        assertNull(binarySearchTree.getRoot().getRight().getRight());
        assertTrue(binarySearchTree.contains(10));
        assertFalse(binarySearchTree.contains(11));
    }

    @Test
    public void testIsRootBinaryTreeNode() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(5);
        assertTrue(node.isRoot());
    }

    @Test
    void testIsRootBinarySearchTree() {
        binarySearchTree.add(5);
        assertTrue(binarySearchTree.getRoot().isRoot());
    }

    @Test
    void testIsNotRootBinarySearchTree() {
        binarySearchTree.add(5);
        binarySearchTree.add(3);
        assertFalse(binarySearchTree.getRoot().getLeft().isRoot());
    }

    @Test
    void testAddDuplicateElementToBinarySearchTreeShouldNotAllowDuplicates() {
        binarySearchTree.add(5);
        binarySearchTree.add(5);

        assertEquals(1, binarySearchTree.getSize());
    }


    @Test
    void testRemoveNonExistentNode() {
        binarySearchTree.add(5);
        binarySearchTree.remove(7);
        assertEquals(1, binarySearchTree.getSize());
        assertTrue(binarySearchTree.contains(5));
    }


    @Test
    void testInOrderTraversal() {
        binarySearchTree.add(5);
        binarySearchTree.add(3);
        binarySearchTree.add(7);
        binarySearchTree.add(1);
        binarySearchTree.add(4);
        List<Integer> result = binarySearchTree.inorderTraversal();
        assertEquals(Arrays.asList(1, 3, 4, 5, 7), result);
    }


    @Test
    public void testIsLeaf() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(5);
        assertTrue(node.isLeaf());
    }

    @Test
    public void testNodeHeight() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(5);
        assertEquals(0, node.getHeight());
        node.setLeft(new BinaryTreeNode<>(3));
        assertEquals(1, node.getHeight());
        node.setRight(new BinaryTreeNode<>(8));
        assertEquals(1, node.getHeight());
    }

    @Test
    public void testNodeDepth() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> leftChild = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> rightChild = new BinaryTreeNode<>(8);
        root.setLeft(leftChild);
        root.setRight(rightChild);
        assertEquals(0, root.getDepth());
        assertEquals(1, leftChild.getDepth());
        assertEquals(1, rightChild.getDepth());
    }

    @Test
    void testGetParent() {
        BinaryTreeNode<Integer> parentNode = new BinaryTreeNode<>(10);
        BinaryTreeNode<Integer> childNode = new BinaryTreeNode<>(5);

        // Manually setting child's parent to be parentNode
        childNode.setParent(parentNode);

        assertEquals(parentNode, childNode.getParent());
        assertNull(parentNode.getParent()); // Root node has no parent
    }


    @Test
    void testIsBalancedTreeNode() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(10);

        BinaryTreeNode<Integer> leftChild = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> rightChild = new BinaryTreeNode<>(15);

        // Root with two children should be balanced
        root.setLeft(leftChild);
        root.setRight(rightChild);
        assertTrue(root.isBalanced());

        // Adding another level to left side
        leftChild.setLeft(new BinaryTreeNode<>(3));
        assertTrue(root.isBalanced()); // Still balanced because the difference in heights is 1

        // Adding another level to left side making it unbalanced
        leftChild.getLeft().setLeft(new BinaryTreeNode<>(1));
        assertFalse(root.isBalanced()); // Now unbalanced
    }


    @Test
    public void testGetSize() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(5);
        assertEquals(1, root.getSize());
        root.setLeft(new BinaryTreeNode<>(3));
        assertEquals(2, root.getSize());
        root.setRight(new BinaryTreeNode<>(8));
        assertEquals(3, root.getSize());
    }


    @Test
    void testAddMaxValue() {
        binarySearchTree.add(Integer.MAX_VALUE);
        assertTrue(binarySearchTree.contains(Integer.MAX_VALUE));
    }

    @Test
    void testAddMinValue() {
        binarySearchTree.add(Integer.MIN_VALUE);
        assertTrue(binarySearchTree.contains(Integer.MIN_VALUE));
    }

    @Test
    void testComplexTreeStructureHeight() {
        binarySearchTree.add(70);
        binarySearchTree.add(50);
        binarySearchTree.add(90);
        binarySearchTree.add(40);
        binarySearchTree.add(60);
        binarySearchTree.add(80);
        binarySearchTree.add(100);
        assertEquals(2, binarySearchTree.getHeight());
    }

    @Test
    public void testPreorderTraversal() {
        binarySearchTree.add(100);
        binarySearchTree.add(80);
        binarySearchTree.add(120);
        binarySearchTree.add(60);
        binarySearchTree.add(90);
        binarySearchTree.add(110);
        binarySearchTree.add(130);

        List<Integer> expected = Arrays.asList(100, 80, 60, 90, 120, 110, 130);
        List<Integer> result = binarySearchTree.preorderTraversal();

        assertEquals(expected, result);
    }

    @Test
    public void testInorderTraversal() {
        binarySearchTree.add(100);
        binarySearchTree.add(80);
        binarySearchTree.add(120);
        binarySearchTree.add(60);
        binarySearchTree.add(90);
        binarySearchTree.add(110);
        binarySearchTree.add(130);

        List<Integer> expected = Arrays.asList(60, 80, 90, 100, 110, 120, 130);
        List<Integer> result = binarySearchTree.inorderTraversal();

        assertEquals(expected, result);
    }

    @Test
    public void testPostorderTraversal() {
        binarySearchTree.add(100);
        binarySearchTree.add(80);
        binarySearchTree.add(120);
        binarySearchTree.add(60);
        binarySearchTree.add(90);
        binarySearchTree.add(110);
        binarySearchTree.add(130);

        List<Integer> expected = Arrays.asList(60, 90, 80, 110, 130, 120, 100);
        List<Integer> result = binarySearchTree.postorderTraversal();

        assertEquals(expected, result);
    }

    @Test
    public void testEmptyTreeToGraphviz() {
        String expected = "digraph BinarySearchTree {\n}\n";
        assertEquals(expected, binarySearchTree.toGraphviz());
    }

    @Test
    public void testSingleElementTreeToGraphviz() {
        binarySearchTree.add(100);
        String expected = "digraph BinarySearchTree {\n}\n"; // Single node tree will have no edges
        assertEquals(expected, binarySearchTree.toGraphviz());
    }

    @Test
    public void testRandomOneOrTwoDigitElementsTreeToGraphviz() {
        binarySearchTree.add(56);
        binarySearchTree.add(34);
        binarySearchTree.add(78);
        binarySearchTree.add(12);
        binarySearchTree.add(45);
        binarySearchTree.add(67);
        binarySearchTree.add(89);

        String expected =
                "digraph BinarySearchTree {\n" +
                        "    \"56\" -> \"34\";\n" +
                        "    \"34\" -> \"12\";\n" +
                        "    \"34\" -> \"45\";\n" +
                        "    \"56\" -> \"78\";\n" +
                        "    \"78\" -> \"67\";\n" +
                        "    \"78\" -> \"89\";\n" +
                        "}\n";
        assertEquals(expected, binarySearchTree.toGraphviz());
    }

}

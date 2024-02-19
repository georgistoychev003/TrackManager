
package trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinarySearchTree<T> {
    protected BinaryTreeNode<T> root;
    protected final Comparator<T> comparator;


    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }


    public BinaryTreeNode<T> getRoot() {
        return root;
    }


    public boolean isEmpty() {
        return root == null;
    }


    public int getSize() {
        if (isEmpty()) {
            return 0;
        } else {
            return root.getSize();
        }
    }

    public int getHeight() {
        if (isEmpty()) {
            return 0;
        } else {
            return root.getHeight();
        }
    }


    public void add(T data) {
        if (root == null) {
            root = new BinaryTreeNode<>(data);
        } else {
            add(root, data);
        }
    }

    /**
     * Recursive helper method to add a new element to the tree.

     */
    private void add(BinaryTreeNode<T> node, T data) {
        int compare = comparator.compare(data, node.getData());
        if (compare < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTreeNode<>(data));
            } else {
                add(node.getLeft(), data);
            }
        } else if (compare > 0) {
            if (node.getRight() == null) {
                node.setRight(new BinaryTreeNode<>(data));
            } else {
                add(node.getRight(), data);
            }
        }
    }


    public boolean contains(T data) {
        return contains(root, data);
    }

    /**
     * Recursive helper method to check whether the tree contains the specified element.
     */
    private boolean contains(BinaryTreeNode<T> node, T data) {
        if (node == null) {
            return false;
        }

        int compare = comparator.compare(data, node.getData());
        if (compare < 0) {
            return contains(node.getLeft(), data);
        } else if (compare > 0) {
            return contains(node.getRight(), data);
        } else {
            return true;
        }
    }

    public void remove(T data) {
        root = remove(root, data);
    }

    /**
     * Recursive helper method to remove the specified element from the tree.
     */
    private BinaryTreeNode<T> remove(BinaryTreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        int compare = comparator.compare(data, node.getData());
        if (compare < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (compare > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            BinaryTreeNode<T> minLargestNode = findMinimum(node.getRight());
            node.setRight(deleteMinimum(node.getRight()));
            minLargestNode.setLeft(node.getLeft());
            minLargestNode.setRight(node.getRight());
            node = minLargestNode;
        }
        return node;
    }

    /**
     * here i delete the minimum value node in the binary search tree.
     * I traverse to the leftmost node in the subtree rooted at the provided node,
     * and then i replace this leftmost node with its right child
     */
    protected BinaryTreeNode<T> deleteMinimum(BinaryTreeNode<T> node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(deleteMinimum(node.getLeft()));
        return node;
    }

    /**
     * here i find the node with the minimum value in the binary search tree.
     * This is the leftmost node in the subtree rooted at the provided node.
     */
    protected BinaryTreeNode<T> findMinimum(BinaryTreeNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public List<T> preorderTraversal() {
        List<T> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(BinaryTreeNode<T> node, List<T> result) {
        if (node != null) {
            result.add(node.getData());
            preorderTraversal(node.getLeft(), result);
            preorderTraversal(node.getRight(), result);
        }
    }

    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(BinaryTreeNode<T> node, List<T> result) {
        if (node != null) {
            inorderTraversal(node.getLeft(), result);
            result.add(node.getData());
            inorderTraversal(node.getRight(), result);
        }
    }

    public List<T> postorderTraversal() {
        List<T> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }

    private void postorderTraversal(BinaryTreeNode<T> node, List<T> result) {
        if (node != null) {
            postorderTraversal(node.getLeft(), result);
            postorderTraversal(node.getRight(), result);
            result.add(node.getData());
        }
    }

    public String toGraphviz() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph BinarySearchTree {\n");
        if(root != null) {
            traverseAndPrint(sb, root);
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Helper method to traverse the tree and append nodes and edges to the StringBuilder.
     */
    private void traverseAndPrint(StringBuilder sb, BinaryTreeNode<T> node) {
        if(node.getLeft() != null) {
            sb.append("    \"").append(node.getData()).append("\" -> \"").append(node.getLeft().getData()).append("\";\n");
            traverseAndPrint(sb, node.getLeft());
        }
        if(node.getRight() != null) {
            sb.append("    \"").append(node.getData()).append("\" -> \"").append(node.getRight().getData()).append("\";\n");
            traverseAndPrint(sb, node.getRight());
        }
    }

}



package trees;

import java.util.Comparator;

public class AVLTree<T> extends BinarySearchTree<T> {


    //NO DUPLICATE VALUES ALLOWED!!!!!!!!!!!!!!!!!!!!!! if i give example in documantation i can get extra points
    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void add(T data) {
        if (getRoot() == null) {
            root = new BinaryTreeNode<>(data);
        } else {
            root = add(root, data);
        }
    }

    private BinaryTreeNode<T> add(BinaryTreeNode<T> node, T data) {
        if (node == null) return new BinaryTreeNode<>(data);

        int compare = comparator.compare(data, node.getData());

        if (compare < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (compare > 0) {
            node.setRight(add(node.getRight(), data));
        }else{
            throw new IllegalArgumentException("Duplicate value not allowed: " + data);
        }


        return balance(node);
    }

    @Override
    public void remove(T data) {
        if (getRoot() != null) {
            root = remove(root, data);
        }
    }

    public boolean contains(T data) {
        return contains(root, data);
    }

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
            return true; // data found
        }
    }


    private BinaryTreeNode<T> remove(BinaryTreeNode<T> node, T data) {
        if (node == null) return null;

        int compare = comparator.compare(data, node.getData());

        if (compare < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (compare > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            BinaryTreeNode<T> minLargestNode = findMinimum(node.getRight());
            minLargestNode.setRight(deleteMinimum(node.getRight()));
            minLargestNode.setLeft(node.getLeft());
            node = minLargestNode;
        }

        return balance(node);
    }

    private BinaryTreeNode<T> balance(BinaryTreeNode<T> node) {
        int balance = node.getBalance();

        if (balance > 1) {
            if (node.getRight().getBalance() < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        } else if (balance < -1) {
            if (node.getLeft().getBalance() > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }

        return node;
    }
    public static boolean isBalanced(BinaryTreeNode<Integer> node) {
        if (node == null) return true;

        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        boolean balanced = Math.abs(leftHeight - rightHeight) <= 1;

        return balanced && isBalanced(node.getLeft()) && isBalanced(node.getRight());
    }

    public static int getHeight(BinaryTreeNode<Integer> node) {
        if (node == null) return 0;
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        return Math.max(leftHeight, rightHeight) + 1;
    }

    private BinaryTreeNode<T> rotateLeft(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        return newRoot;
    }

    private BinaryTreeNode<T> rotateRight(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        return newRoot;
    }


    public String toGraphviz() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph AVLTree {\n");
        if (getRoot() != null) {
            traverseAndPrint(sb, getRoot());
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Helper method to traverse the tree and append nodes and edges to the StringBuilder.
     */
    private void traverseAndPrint(StringBuilder sb, BinaryTreeNode<T> node) {
        if (node.getLeft() != null) {
            sb.append("    \"").append(node.getData()).append("\" -> \"").append(node.getLeft().getData()).append("\";\n");
            traverseAndPrint(sb, node.getLeft());
        }
        if (node.getRight() != null) {
            sb.append("    \"").append(node.getData()).append("\" -> \"").append(node.getRight().getData()).append("\";\n");
            traverseAndPrint(sb, node.getRight());
        }
    }
}

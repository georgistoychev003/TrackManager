package trees;

/**
 * This represents a node in my binary tree structure. I will use it in my binarySearchTree
 */
public class BinaryTreeNode<T> {
    private T data;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> parent;


    public BinaryTreeNode(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    /**
     * @return the left child of this node.
     */
    public BinaryTreeNode<T> getLeft() {
        return left;
    }
    /**
     * @return the roight child of this node.
     */
    public BinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * @return the parent of this node.
     */
    public BinaryTreeNode<T> getParent() {
        return parent;
    }

     public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
        if (left != null) {
            this.left.setParent(this);
        }
    }


    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
        if (right != null) {
            this.right.setParent(this);
        }
    }



    public void setParent(BinaryTreeNode<T> parent){
        this.parent= parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }


    /**
     * Calculates and returns the height of the subtree rooted at this node.
     */
    public int getHeight() {
        if (this == null) return -1;
        return 1 + Math.max(
                (left == null ? -1 : left.getHeight()),
                (right == null ? -1 : right.getHeight())
        );
    }

    /**
     * Checks whether the subtree rooted at this node is balanced.
     */
    public boolean isBalanced() {
        return Math.abs(
                (left == null ? -1 : left.getHeight()) -
                        (right == null ? -1 : right.getHeight())
        ) <= 1;
    }

    /**
     * Calculates and returns the balance factor of this node.
     */
    public int getBalance() {
        int leftHeight = left == null ? -1 : left.getHeight();
        int rightHeight = right == null ? -1 : right.getHeight();
        return rightHeight - leftHeight;
    }

    /**
     * Calculates and returns the depth of this node.
     */
    public int getDepth() {
        int depth = 0;
        BinaryTreeNode<T> current = this;
        while (current.parent != null) {
            depth++;
            current = current.parent;
        }
        return depth;
    }

    /**
     * Calculates and returns the size of the subtree rooted at this node.
     * reference: I saw this code from our teacher Frederik Bonte in class
     */
    public int getSize() {

        int result = 1;
        if(getLeft()!=null){
            result +=getLeft().getSize();
        }
        if(getRight()!=null){
            result +=getRight().getSize();
        }
        return result;
    }
}




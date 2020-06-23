import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a binary search tree.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        Iterator<T> iterator = data.iterator();

        while (iterator.hasNext()) {
            this.add(iterator.next());
        }

    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return;
        }

        BSTNode<T> nodeToAddTo = addHelper(root, data);
        if (nodeToAddTo == null) {
            return;
        } else if (data.compareTo(nodeToAddTo.getData()) < 0) {
            nodeToAddTo.setLeft(new BSTNode<>(data));
            size++;
            return;
        } else if (data.compareTo(nodeToAddTo.getData()) > 0) {
            nodeToAddTo.setRight(new BSTNode<>(data));
            size++;
            return;
        }
    }

    /**
     * Private helper for the add method. Recursively finds the node to add to.
     *
     * @param current the node from which to continue recursive search
     * @param data the data to find a location for
     * @return the parent node of the new data to be added
     */
    private BSTNode<T> addHelper(BSTNode<T> current, T data) {
        if (data.compareTo(current.getData()) < 0) {
            if (current.getLeft() == null) {
                return current;
            } else {
                return addHelper(current.getLeft(), data);
            }
        } else if (current.getData().equals(data)) {
            return null;
        } else {
            if (current.getRight() == null) {
                return current;
            } else {
                return addHelper(current.getRight(), data);
            }
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null values");
        }
        BSTNode<T> newRoot = removeHelper(root, data);
        if (newRoot == null) {
            throw new NoSuchElementException("Could not remove element with"
                    + "data because it is not in the tree.");
        }
        root = newRoot;
        size--;
        return newRoot.getData();
    }

    /**
     * Private hellper method for the remove method. Recursively finds the
     * parent of the node to be removed.
     *
     * @param current the current node in the recursion from where to continue
     *                search
     * @param data the data to be removed the parent of which is being searched
     *             for
     * @return the new root node or null if data is not in tree
     */
    private BSTNode<T> removeHelper(BSTNode<T> current, T data) {
        if (current == null) {
            return current;
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(removeHelper(current.getRight(), data));
        } else {
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            }
            current.setData(removeHelper(current,
                    getSuccesor(current).getData()).getData());
        }
        return current;
    }

    /**
     * Private helper for the remove method to find a nodes successor in tree.
     *
     * @param node the node to find a successor for
     * @return the successor node of the node argument
     */
    private BSTNode<T> getSuccesor(BSTNode<T> node) {
        node = node.getRight();

        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        BSTNode<T> returnDataNode = getHelper(root, data);

        if (returnDataNode == null) {
            throw new NoSuchElementException("That data is not in the tree.");
        }
        return returnDataNode.getData();
    }

    /**
     * Private helper for the get method. Recursively finds the data passed into
     * the get method.
     *
     * @param node the node from which to continue search for the data
     * @param data the data to be searched for
     * @return the node containing the data being searched for
     */
    private BSTNode<T> getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (node.getData().equals(data)) {
            return node;
        } else if (data.compareTo(node.getData()) < 0) {
            return getHelper(node.getLeft(), data);
        } else {
            return getHelper(node.getRight(), data);
        }

    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        BSTNode<T> node = containsHelper(root, data);
        return (node != null);
    }

    /**
     * Private helper for contains method. Recursevely tries to find the data
     * from the contains method.
     *
     * @param node the node from where to continue searching for the data
     * @param data the data which we want to know if it is in the tree
     * @return the node containing the data we are looking for or null if the
     *          data is not in the tree
     */
    private BSTNode<T> containsHelper(BSTNode<T> node, T data) {
        if (node == null || node.getData().equals(data)) {
            return node;
        }
        if (data.compareTo(node.getData()) < 0) {
            node = containsHelper(node.getLeft(), data);
        } else {
            node = containsHelper(node.getRight(), data);
        }
        return node;
    }
    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>(size);
        preorderHelper(root, list);
        return list;
    }

    /**
     * Private helper for the preorder method. Recursively adds elements to the
     * list in preorder.
     *
     * @param node the node from which to continue adding elements to the list
     * @param list the list to which elements should be added
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        preorderHelper(node.getLeft(), list);
        preorderHelper(node.getRight(), list);
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>(size);
        inorderHelper(root, list);
        return list;
    }

    /**
     * Private helper for inorder method. Recursively adds elements in order to
     * the list.
     *
     * @param node the node from which to continue adding elements to the list
     * @param list the list to which elements should be added in order
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        inorderHelper(node.getLeft(), list);
        list.add(node.getData());
        inorderHelper(node.getRight(), list);

    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>(size);
        postorderHelper(root, list);
        return list;
    }

    /**
     * Private helper for the postorder method. Recursively iterates through the
     * elements of the tree ading them to the list.
     *
     * @param node the node from which to continue adding elements to the list
     * @param list the list to which elements are added in post order
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        postorderHelper(node.getLeft(), list);
        postorderHelper(node.getRight(), list);
        list.add(node.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>(size);
        LinkedList<BSTNode<T>> orderQueue = new LinkedList<>();
        orderQueue.add(root);
        while (!orderQueue.isEmpty()) {
            BSTNode<T> current = orderQueue.pop();
            if (current != null) {
                list.add(current.getData());
                orderQueue.add(current.getLeft());
                orderQueue.add(current.getRight());
            }
        }
        return list;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     * in the BST
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new IllegalArgumentException("Invalid k value, k must be"
                    + "equal to or larger than 1");
        }
        LinkedList<T> list = new LinkedList<T>();
        kLargestHelper(k, root, list);
        return list;
    }

    /**
     * Private helper for the kLargest method. Recursively adds the last k
     * elemnts in the tree to the list.
     *
     * @param k the number of largest elements to add to the list
     * @param node the current node from where to continue finding the k largest
     *            elements
     * @param list the list of k largest elements to which the elements are
     *             added in order
     */
    private void kLargestHelper(int k, BSTNode<T> node, LinkedList<T> list) {
        if (node == null || list.size() >= k) {
            return;
        }
        kLargestHelper(k, node.getRight(), list);
        if (list.size() >= k) {
            return;
        }
        list.addFirst(node.getData());
        kLargestHelper(k, node.getLeft(), list);
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Returns the height of the given node, used to help find height of tree.
     *
     * @param node the node to find the height of.
     * @return the height of the given node.
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = heightHelper(node.getLeft());
        int rightHeight = heightHelper(node.getRight());

        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

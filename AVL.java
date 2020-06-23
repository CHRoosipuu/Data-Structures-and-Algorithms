import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot create AVL from null"
                    + "data");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Cannot add null data to"
                        + "AVL");
            }
            add(element);
        }
    }

    /**
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to AVL");
        }
        root = add(root, data);
    }

    /**
     * Private helper method to recursively add to an AVL tree.
     *
     * @param currentNode the current node
     * @param data the data to add to the AVL tree
     * @return currentNode the new updated node
     */
    private AVLNode<T> add(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (currentNode.getData().compareTo(data) > 0) {
            currentNode.setLeft(add(currentNode.getLeft(), data));
        } else if (currentNode.getData().compareTo(data) < 0) {
            currentNode.setRight(add(currentNode.getRight(), data));
        }
        update(currentNode);

        if (currentNode.getBalanceFactor() > 1) {
            if (currentNode.getLeft().getBalanceFactor() == -1) {
                currentNode = leftRightRotation(currentNode);
                update(currentNode);
            } else {
                currentNode = rotateRight(currentNode);
                update(currentNode);
            }
        } else if (currentNode.getBalanceFactor() < -1) {
            if (currentNode.getRight().getBalanceFactor() == 1) {
                currentNode = rightLeftRotation(currentNode);
                update(currentNode);
            } else {
                currentNode = rotateLeft(currentNode);
                update(currentNode);
            }
        }
        return currentNode;
    }

    /**
     * Private helper method to update the height and balance factor of the AVL
     * node.
     *
     * @param currentNode the current node
     */
    private void update(AVLNode<T> currentNode) {
        int leftNodeHeight = -1;
        if (currentNode.getLeft() != null) {
            leftNodeHeight = currentNode.getLeft().getHeight();
        }
        int rightNodeHeight = -1;
        if (currentNode.getRight() != null) {
            rightNodeHeight = currentNode.getRight().getHeight();
        }
        currentNode.setHeight(Math.max(leftNodeHeight, rightNodeHeight) + 1);
        currentNode.setBalanceFactor(leftNodeHeight - rightNodeHeight);
    }

    /**
     * Private helper method for left rotation.
     *
     * @param currentNode the node we are rotating
     * @return returns the node that should become the new parent node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
        AVLNode<T> rightNode = currentNode.getRight();
        AVLNode<T> temp = rightNode.getLeft();

        // Rotation
        rightNode.setLeft(currentNode);
        currentNode.setRight(temp);

        // Update Nodes
        update(currentNode);
        update(rightNode);


        return rightNode;
    }

    /**
     * Private helper method for right rotation.
     *
     * @param currentNode the node we are rotating
     * @return returnNode the node that should become the new parent node
     */
    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        AVLNode<T> leftNode = currentNode.getLeft();
        AVLNode<T> temp = leftNode.getRight();

        // Rotation
        leftNode.setRight(currentNode);
        currentNode.setLeft(temp);

        // Update Nodes
        update(currentNode);
        update(leftNode);


        return leftNode;
    }

    /**
     * Private helper method for left-right rotation.
     *
     * @param currentNode the node we are rotating
     * @return returns the new parent node after the rotation operation
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> currentNode) {
        currentNode.setLeft(rotateLeft(currentNode.getLeft()));
        AVLNode<T> returnNode = rotateRight(currentNode);
        update(currentNode);
        return returnNode;
    }

    /**
     * Private helper method for right-left rotation.
     *
     * @param currentNode the node we are rotating
     * @return returns the new parent node after the rotation operation
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> currentNode) {
        currentNode.setRight(rotateRight(currentNode.getRight()));
        AVLNode<T> returnNode = rotateLeft(currentNode);
        update(currentNode);
        return returnNode;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from"
                    + "AVL");
        }
        AVLNode<T> returnNode = new AVLNode<T>(null);
        root = remove(root, data, returnNode);
        return returnNode.getData();
    }

    /**
     * Private helper method to recursively remove from an AVL tree.
     *
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking to destroy
     * @param returnNode the dummy AVL node
     * @return returns the new node after the rotation operation
     */
    private AVLNode<T> remove(AVLNode<T> currentNode, T data,
                              AVLNode<T> returnNode) {
        if (currentNode == null) {
            throw new NoSuchElementException("Element does not exist");
        } else if (currentNode.getData().compareTo(data) > 0) {
            currentNode.setLeft(remove(currentNode.getLeft(), data,
                returnNode));
        } else if (currentNode.getData().compareTo(data) < 0) {
            currentNode.setRight(remove(currentNode.getRight(), data,
                returnNode));
        } else {
            size--;
            returnNode.setData(currentNode.getData());

            if (currentNode.getLeft() == null
                    && currentNode.getRight() == null) {
                currentNode = null;
            } else if (currentNode.getLeft() != null
                    && currentNode.getRight() == null) {
                currentNode = currentNode.getLeft();
            } else if (currentNode.getLeft() == null
                    && currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
            } else {
                AVLNode<T> dummyNode = new AVLNode<T>(null);
                currentNode.setLeft(removePredecessorRecursive(
                    currentNode.getLeft(), dummyNode));
                currentNode.setData(dummyNode.getData());
            }
        }

        if (currentNode != null) {
            update(currentNode);
            if (currentNode.getBalanceFactor() > 1) {
                if (currentNode.getLeft().getBalanceFactor() == -1) {
                    currentNode = leftRightRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rotateRight(currentNode);
                    update(currentNode);
                }
            } else if (currentNode.getBalanceFactor() < -1) {
                if (currentNode.getRight().getBalanceFactor() == 1) {
                    currentNode = rightLeftRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rotateLeft(currentNode);
                    update(currentNode);
                }
            }
        }

        return currentNode;
    }

    /**
     * Private helper method for remove() to deal with AVL successors.
     *
     * @param currentNode the current node we're traversing for successor
     * @param dummyNode the dummy node for storing the successor data
     * @return currentNode the new updated parent node
     */
    private AVLNode<T> removePredecessorRecursive(AVLNode<T> currentNode,
                                              AVLNode<T> dummyNode) {

        if (currentNode.getLeft() != null) {
            currentNode.setLeft(removePredecessorRecursive(
                currentNode.getLeft(), dummyNode));
        } else {
            dummyNode.setData(currentNode.getData());
            currentNode = currentNode.getLeft();
        }

        if (currentNode != null) {
            update(currentNode);

            if (currentNode.getBalanceFactor() > 1) {
                if (currentNode.getLeft().getBalanceFactor() == -1) {
                    currentNode = leftRightRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rotateRight(currentNode);
                    update(currentNode);
                }
            } else if (currentNode.getBalanceFactor() < -1) {
                if (currentNode.getLeft().getBalanceFactor() == 1) {
                    currentNode = rightLeftRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rotateLeft(currentNode);
                    update(currentNode);
                }
            }
        }
        return currentNode;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
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
            throw new IllegalArgumentException("Data cannot be null");
        }
        return get(root, data);
    }

    /**
     * Private helper method to recursively get from an AVL tree.
     *
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking
     * @return returns the data of the found node; else throws exception
     */
    private T get(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            throw new NoSuchElementException("Element does not exist");
        } else if (currentNode.getData().compareTo(data) > 0) {
            return get(currentNode.getLeft(), data);
        } else if (currentNode.getData().compareTo(data) < 0) {
            return get(currentNode.getRight(), data);
        }
        return currentNode.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return contains(root, data);
    }

    /**
     * Private helper method to recursively check if an element exists.
     *
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking
     * @return returns true if data is in AVL tree; else returns false
     */
    private boolean contains(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {

            return false;

        } else if (currentNode.getData().compareTo(data) > 0) {
            return contains(currentNode.getLeft(), data);

        } else if (currentNode.getData().compareTo(data) < 0) {
            return contains(currentNode.getRight(), data);

        }

        return true;
    }

    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return maxDeepestNodeHelper(root).getData();
    }

    /**
     * Private helper method to find the deepest node
     *
     * @param currentNode the node currently being looked at
     * @return the maximum deepest node
     */
    private AVLNode<T> maxDeepestNodeHelper(AVLNode<T> currentNode) {
        if (currentNode.getHeight() != 0) {
            if (currentNode.getRight() == null) {
                return maxDeepestNodeHelper(currentNode.getLeft());
            } else if (currentNode.getLeft() == null) {
                return maxDeepestNodeHelper(currentNode.getRight());
            }
            AVLNode<T> leftChild = currentNode.getLeft();
            AVLNode<T> rightChild = currentNode.getRight();
            if (leftChild.getHeight() > rightChild.getHeight()) {
                return maxDeepestNodeHelper(leftChild);
            } else {
                return maxDeepestNodeHelper(rightChild);
            }
        }
        return currentNode;
    }

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * deepestCommonAncestor(3, 1): 2
     *
     * Example
     * Tree:
     *           3
     *        /    \
     *       1      4
     *      / \
     *     0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @throws IllegalArgumentException if one or more of the data
     *          are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *          not in the tree
     * @return the data of the deepest common ancestor
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null");

        }

        ArrayList<AVLNode<T>> list1 = new ArrayList<AVLNode<T>>();

        ArrayList<AVLNode<T>> list2 = new ArrayList<AVLNode<T>>();

        AVLNode<T> temp1 = getNode(list1, root, data1);

        AVLNode<T> temp2 = getNode(list2, root, data2);

        AVLNode<T> deepestParent = null;

        for (int i = 0; i < size; i++) {
            if (i >= list1.size() || i >= list2.size()) {

                break;

            }

            if (list1.get(i) == list2.get(i)) {

                deepestParent = list1.get(i);

            } else {

                break;

            }

        }

        return deepestParent.getData();
    }

    /**
     * Private helper method to recursively get node from an AVL tree.
     *
     * @param list the list of parents
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking
     * @return returns the node of data passed; else throws exception
     */
    private AVLNode<T> getNode(ArrayList<AVLNode<T>> list,
                               AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            throw new NoSuchElementException("Element does not exist");
        } else if (currentNode.getData().compareTo(data) > 0) {
            list.add(currentNode);
            return getNode(list, currentNode.getLeft(), data);
        } else if (currentNode.getData().compareTo(data) < 0) {
            list.add(currentNode);
            return getNode(list, currentNode.getRight(), data);
        }
        list.add(currentNode);
        return currentNode;
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        size = 0;

        root = null;
    }

    /**
     * Return the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Private helper method to recursively check the height of the AVL tree.
     *
     * @param currentNode the node you are currently traversing
     * @return returns the height of the current node
     */
    private int height(AVLNode<T> currentNode) {
        if (currentNode == null) {
            return -1;
        }
        return Math.max(height(currentNode.getLeft()),
                height(currentNode.getLeft())) + 1;
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
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

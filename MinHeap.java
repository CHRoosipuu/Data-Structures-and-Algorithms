import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot build MinHeap with null"
                    + "data!");
        }
        T[] newBackingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Cannot have null items in"
                        + "MinHeap");
            }
            newBackingArray[i + 1] = data.get(i);
        }
        backingArray = newBackingArray;
        size = data.size();

        for (int n = size / 2; n >= 1; n--) {
            heapifyDown(n);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to "
                    + "MinHeap");
        }
        if (size - 1 == backingArray.length) {
            T[] newBackingArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                newBackingArray[i] = backingArray[i];
            }
            backingArray = newBackingArray;
        }
        backingArray[++size] = item;
        int current = size;
        while (backingArray[getParentOf(current)] != null
                && backingArray[current]
                .compareTo(backingArray[getParentOf(current)]) < 0) {
            swap(current, getParentOf(current));
            current = getParentOf(current);
        }
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("You cannot remove from an empty "
                    + "MinHeap!");
        }
        T removedItem = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapifyDown(1);

        return removedItem;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Private method to to get the index of the parent of a item at a given
     * index
     *
     * @param pos the index of the child item who's parents index will be
     *            returned
     * @return the index of the parent of the given child item at the given
     * index
     */
    private int getParentOf(int pos) {
        return pos / 2;
    }

    /**
     * Private helper method to to get the index of the left child of a item at
     * a given index
     *
     * @param pos the index of the item who's left child index will be
     *            returned
     * @return the index of the left child of the given item at teh given index
     */
    private int getLeftChildOf(int pos) {
        return pos * 2;
    }

    /**
     * Private helper method to to get the index of the right child of a item at
     * a given index
     *
     * @param pos the index of the item who's right child index will be
     *            returned
     * @return the index of the right child of the given item at teh given index
     */
    private int getRightChildOf(int pos) {
        return (pos * 2) + 1;
    }

    /**
     * Private helper method to swap items at index a and b.
     *
     * @param a the index of one item to swap
     * @param b the index of the other item to swap
     */
    private void swap(int a, int b) {
        T dataA = backingArray[a];
        backingArray[a] = backingArray[b];
        backingArray[b] = dataA;
    }

    /**
     * Private helper method to heapify down the tree starting at a index
     * position
     *
     * @param pos the position in the backing array to heapify down the tree
     */
    private void heapifyDown(int pos) {
        if (!(pos >=  (size / 2)  &&  pos <= size)
                || ((backingArray[getLeftChildOf(pos)]
                != null && backingArray[getRightChildOf(pos)] != null))) {
            if (backingArray[pos]
                    .compareTo(backingArray[getLeftChildOf(pos)]) > 0
                    || backingArray[pos]
                    .compareTo(backingArray[getRightChildOf(pos)]) > 0) {
                if (backingArray[getLeftChildOf(pos)]
                        .compareTo(backingArray[getRightChildOf(pos)]) < 0)  {
                    swap(pos, getLeftChildOf(pos));
                    heapifyDown(getLeftChildOf(pos));
                } else {
                    swap(pos, getRightChildOf(pos));
                    heapifyDown(getRightChildOf(pos));
                }
            }
        } else if (getLeftChildOf(pos) <= size && size > 1
                && backingArray[getLeftChildOf(pos)] != null
                && backingArray[getLeftChildOf(pos)]
                .compareTo(backingArray[pos]) < 0) {
            swap(pos, getLeftChildOf(pos));
        } else if (getRightChildOf(pos) <= size && size > 1
                && backingArray[getRightChildOf(pos)] != null
                && backingArray[getRightChildOf(pos)]
                .compareTo(backingArray[pos]) < 0) {
            swap(pos, getRightChildOf(pos));
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}

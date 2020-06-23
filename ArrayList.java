/**
 * Your implementation of an ArrayList.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index The index where you want the new element.
     * @param data The data to add to the list.
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + "between 0 and the size of the ArrayList");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "data structure.");
        }

        T[] newBackingArray = (T[]) new Object[backingArray.length];

        if (backingArray.length == size) {
            newBackingArray = (T[]) new Object[backingArray.length * 2];
        } else if (index == size) {
            backingArray[size] = data;
            size += 1;
            return;
        }

        for (int i = 0; i < size + 1; i++) {
            if (i < index) {
                newBackingArray[i] = backingArray[i];
            } else if (i == index) {
                newBackingArray[i] = data;
            } else {
                newBackingArray[i] = backingArray[i - 1];
            }
        }

        backingArray = newBackingArray;
        size += 1;
    }

    /**
     * Add the given data to the front of your array list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
                    + "data structure.");
        }

        T[] newBackingArray = (T[]) new Object[backingArray.length];

        if (backingArray.length == size) {
            newBackingArray = (T[]) new Object[backingArray.length * 2];
        }

        newBackingArray[0] = data;

        for (int i = 1; i <= size; i++) {
            newBackingArray[i] = backingArray[i - 1];
        }

        backingArray = newBackingArray;
        size += 1;
    }

    /**
     * Add the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
                    + "data structure.");
        }

        if (backingArray.length == size) {
            T[] newBackingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newBackingArray[i] = backingArray[i];
            }
            backingArray = newBackingArray;
        }

        backingArray[size] = data;
        size += 1;
    }

    /**
     * Removes and returns the element at index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in
     * all other cases.
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + "between 0 and the size of the ArrayList");
        }

        T removedData = backingArray[index];

        backingArray[index] = null;

        if (index != size - 1) {
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
        }

        backingArray[size - 1] = null;

        size -= 1;
        return removedData;
    }

    /**
     * Remove the first element in the list and return it.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return The data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }

        T removedData = backingArray[0];

        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }

        size -= 1;
        return removedData;
    }

    /**
     * Remove the last element in the list and return it.
     *
     * Must be O(1).
     *
     * @return The data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }

        T removedData = backingArray[size - 1];

        backingArray[size - 1] = null;

        size -= 1;
        return removedData;
    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index The index of the element
     * @return The data stored at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + "between 0 and the size of the ArrayList");
        }
        return backingArray[index];
    }

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Return the size of the list as an integer.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for this list.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}

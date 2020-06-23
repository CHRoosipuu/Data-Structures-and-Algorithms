/**
 * Your implementation of a non-circular doubly linked list with a tail pointer.
 *
 * @author Carl Henry Rooispuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class DoublyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + " between 0 and the size of the list");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
                    + " data structure.");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);
            LinkedListNode<T> current = head;
            if (index < size / 2) {
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                newNode.setPrevious(current);
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                newNode.getNext().setPrevious(newNode);
            } else {
                current = tail;
                for (int i = size - 1; i > index - 1; i--) {
                    current = current.getPrevious();
                }
                newNode.setPrevious(current);
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                newNode.getNext().setPrevious(newNode);
            }

            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
                    + " data structure.");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        newNode.setNext(head);

        if (head == null) {
            tail = newNode;
        } else {
            head.setPrevious(newNode);
        }

        head = newNode;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
                    + " data structure.");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        newNode.setPrevious(tail);

        if (tail == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }

        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 and {@code size - 1} should be O(1), all other
     * cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + " between 0 and the size of the list");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            LinkedListNode<T> current = head;
            if (index < size / 2) {
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
            }

            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());

            size--;
            return current.getData();
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        }

        LinkedListNode<T> removedNode = head;
        head = head.getNext();
        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }

        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            return null;
        }

        LinkedListNode<T> removedNode = tail;
        tail = tail.getPrevious();
        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }

        size--;
        return removedNode.getData();
    }

    /**
     * Returns the index of the last occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the tail, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the last occurrence or -1 if not in the list
     */
    public int lastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data structure cannot have"
                    + " null data values.");
        }
        
        int index = size;
        LinkedListNode<T> current = tail;
        while (current != null) {
            index--;
            if (current.getData().equals(data)) {
                return index;
            } else {
                current = current.getPrevious();
            }
        }
        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index for adding has to be"
                    + " between 0 and the size of the list");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            LinkedListNode<T> current = head.getNext();
            if (index < size / 2) {
                int i = 1;
                while (i != index) {
                    current = current.getNext();
                    i++;
                }
            } else {
                current = tail.getPrevious();
                int i = size - 2;
                while (i != index) {
                    current = current.getPrevious();
                    i--;
                }
            }
            return current.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order from head to tail
     */
    public Object[] toArray() {
        Object[] listArray = (T[]) new Object[size];
        LinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            listArray[i] = current.getData();
            current = current.getNext();
        }
        return listArray;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}

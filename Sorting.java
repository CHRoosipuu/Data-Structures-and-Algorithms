import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Carl Herny Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        boolean swaped = true;
        int lastSwapForward = arr.length - 1;
        int lastSwapBackward = 0;

        while (swaped) {
            swaped = false;
            int lastSwapForwardTracker = lastSwapForward;
            int lastSwapBackwardTracker = lastSwapBackward;
            for (int i = lastSwapBackward; i < lastSwapForward; i++) {
                T currentElement = arr[i];
                T nextElement = arr[i + 1];
                if (comparator.compare(currentElement, nextElement) > 0) {
                    arr[i] = nextElement;
                    arr[i + 1] = currentElement;
                    lastSwapForwardTracker = i;
                    swaped = true;
                }
            }
            if (!swaped) {
                break;
            }
            lastSwapForward = lastSwapForwardTracker;
            for (int i = lastSwapForward; i > lastSwapBackward; i--) {
                T currentElement = arr[i];
                T previousElement = arr[i - 1];
                if (comparator.compare(currentElement, previousElement) < 0) {
                    arr[i] = previousElement;
                    arr[i - 1] = currentElement;
                    lastSwapBackwardTracker = i;
                    swaped = true;
                }
            }
            lastSwapBackward = lastSwapBackwardTracker;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            T unsortedElement = arr[i];
            int sortIndex = i - 1;
            while (sortIndex >= 0
                    && comparator.compare(unsortedElement, arr[sortIndex])
                    < 0) {
                arr[sortIndex + 1] = arr[sortIndex];
                arr[sortIndex] = unsortedElement;
                sortIndex--;
            }
            arr[sortIndex + 1] = unsortedElement;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; i < arr.length - 1; j++) {
                if (comparator.compare(arr[minIndex], arr[j]) > 0) {
                    minIndex = j;
                }
            }
            T minElement = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = minElement;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (arr.length <= 1) {
            return;
        }

        int middle = arr.length / 2;

        // Left Array
        T[] leftArr = (T[]) new Object[middle];
        for (int i = 0; i < leftArr.length; i++) {
            leftArr[i] = arr[i];
        }
        // Right Array
        T[] rightArr = (T[]) new Object[arr.length - middle];
        for (int i = 0; i < rightArr.length; i++) {
            rightArr[i] = arr[middle + i];
        }

        // Merge sort sub-arrays
        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        // Merge
        int l = 0;
        int r = 0;
        while (l + r < arr.length) {
            if (l > leftArr.length - 1) {
                arr[l + r] = rightArr[r++];
            } else if (r > rightArr.length - 1) {
                arr[l + r] = leftArr[l++];
            } else if (comparator.compare(leftArr[l], rightArr[r]) <= 0) {
                arr[l + r] = leftArr[l++];
            } else {
                arr[l + r] = rightArr[r++];
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        LinkedList<Integer>[] counter;
        counter = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            counter[i] = new LinkedList<>();
        }
        int mod = 10;
        long dev = 1; // int will be negative when reaches max size
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int beforeMod = (int) ((long) arr[i] / dev);
                if (beforeMod != 0) {
                    cont = true;
                }
                int bucket = beforeMod % mod + 9;
                counter[bucket].add(arr[i]);
            }
            int idx = 0;
            for (int i = 0; i < counter.length; i++) {
                while (!counter[i].isEmpty()) {
                    arr[idx] = counter[i].remove();
                    idx++;
                }
            }
            dev *= 10;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     *          the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *          1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random object cannot be null");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k needs to be within array"
                    + "length");
        }
        return quickSelect(arr, 0, arr.length - 1, k, comparator,
                rand);
    }

    /**
     * Recursive helper for kth select using quick sort
     *
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     *          the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *          1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start the index to sort from
     * @param end the index to sort to
     * @return the kth smallest element
     */
    private static <T> T quickSelect(T[] arr, int start, int end, int k,
                                     Comparator<T> comparator, Random rand) {
        if (k > 0 && k <= end) {
            T pivot = arr[rand.nextInt(end)];
            int pivotIndex = start;
            for (int j = start; j <= end - 1; j++) {
                if (comparator.compare(arr[j], pivot) <= 0) {
                    T temp = arr[pivotIndex];
                    arr[pivotIndex] = arr[j];
                    arr[j] = temp;
                    pivotIndex++;
                }
            }
            T temp = arr[pivotIndex];
            arr[pivotIndex] = arr[end];
            arr[end] = temp;
            if (pivotIndex == k) {
                return arr[pivotIndex];
            } else if (pivotIndex > k) {
                return quickSelect(arr, 1, pivotIndex - 1, k,
                        comparator, rand);
            } else {
                return quickSelect(arr, pivotIndex + 1, end,
                        k - pivotIndex, comparator, rand);
            }
        }
        return null;
    }
}

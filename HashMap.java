import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot put an entry with null"
                    + "key into HashMap.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Cannot put an entry with null"
                    + "value into HashMap.");
        }
        // Load factor check for resizing table
        if (((double) size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        // Probe and add
        int originalIndex = compressHash(key.hashCode(), table.length);
        int probeCount = 0;
        while (true) {
            int index = originalIndex + probeCount;
            if (index == table.length) {
                index = 0;
                originalIndex = 0;
                probeCount = 0;
            }
            MapEntry<K, V> probeEntry = table[index];
            if (probeEntry == null
                    || (probeEntry.isRemoved() && !containsKey(key))) {
                table[index] = new MapEntry<>(key, value);
                size++;
                return null;
            } else if (probeEntry.getKey().equals(key)) {
                probeEntry.setRemoved(false);
                V oldValue = probeEntry.getValue();
                probeEntry.setValue(value);
                return oldValue;
            } else {
                probeCount++;
            }
        }
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove entry with null"
                    + "key.");
        }
        // Probe and remove
        int originalIndex = compressHash(key.hashCode(), table.length);
        int probeCount = 0;
        while (true) {
            int index = (originalIndex + probeCount) % table.length;
            MapEntry<K, V> probeEntry = table[index];
            if (probeEntry == null) {
                throw new NoSuchElementException("Cannot remove entry which is"
                        + "not in HashMap");
            } else if (probeEntry.getKey().equals(key)) {
                if (probeEntry.isRemoved()) {
                    throw new NoSuchElementException("Cannot remove entry which"
                            + "is not in HashMap");
                }
                probeEntry.setRemoved(true);
                size--;
                return probeEntry.getValue();
            } else {
                probeCount++;
            }
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot get entry with null"
                    + "key.");
        }
        int originalIndex = compressHash(key.hashCode(), table.length);
        int probeCount = 0;
        while (true) {
            int index = originalIndex + probeCount;
            if (index == table.length) {
                index = 0;
                originalIndex = 0;
                probeCount = 0;
            }
            MapEntry<K, V> probeEntry = table[index];
            if (probeEntry == null
                    || (probeEntry.getKey().equals(key)
                        && probeEntry.isRemoved())) {
                throw new NoSuchElementException("No element with this key in"
                        + "HashMap");
            } else if (probeEntry.getKey().equals(key)) {
                return probeEntry.getValue();
            } else {
                probeCount++;
            }
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("HashMap cannot contain null"
                    + "keys");
        }
        int originalIndex = compressHash(key.hashCode(), table.length);
        int probeCount = 0;
        while (true) {
            if (probeCount > table.length) {
                return false;
            }
            int index = (originalIndex + probeCount) % table.length;
            MapEntry<K, V> probeEntry = table[index];
            if (probeEntry == null) {
                return false;
            } else if (probeEntry.getKey().equals(key)
                    && !probeEntry.isRemoved()) {
                return true;
            } else {
                probeCount++;
            }
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> keyHashSet = new HashSet<>();
        for (int i = 0, added = 0; added < size; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keyHashSet.add(table[i].getKey());
                added++;
            }
        }
        return keyHashSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> valueList = new ArrayList<>(size);
        for (int i = 0, added = 0; added < size; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                valueList.add(table[i].getValue());
                added++;
            }
        }
        return valueList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Cannot resize to less than"
                    + "HashMap size.");
        }
        MapEntry<K, V>[] resizedTable = new MapEntry[length];
        for (int i = 0, added = 0; added < size; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                MapEntry<K, V> probeEntry = table[i];
                int originalIndex =
                        compressHash(probeEntry.getKey().hashCode(),
                                resizedTable.length);
                int probeCount = 0;
                while (true) {
                    int index = originalIndex + probeCount;
                    if (index == resizedTable.length) {
                        index = 0;
                        originalIndex = 0;
                        probeCount = 0;
                    }
                    MapEntry<K, V> newProbeEntry = resizedTable[index];
                    if (newProbeEntry == null) {
                        resizedTable[index] = probeEntry;
                        added++;
                        break;
                    } else {
                        probeCount++;
                    }
                }
            }
        }
        table = resizedTable;
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Private helper method to get the positive compressed hash of a hash code
     * based on the table length.
     *
     * @param hashCode the hash code to be compressed for a table
     * @param tableLength the length of the table for which the hash code will
     *                    be compressed
     * @return the compressed hashcode
     */
    private int compressHash(int hashCode, int tableLength) {
        int compressedHash = hashCode % tableLength;
        if (compressedHash < 0) {
            return -1 * compressedHash;
        }
        return compressedHash;
    }

    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}

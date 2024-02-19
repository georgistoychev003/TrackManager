package map;

import lists.DoublyLinkedList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SeparateChainingHashMap<K, V> {
    private int capacity; // initial array size
    private int size;  // number of key-value pairs

    /**
     * LOAD_FACTOR determines when to resize the internal array.
     * Once the size reaches 75% of the capacity, the array will be resized .
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * The array of doubly-linked lists that stores the entries.
     */
    private DoublyLinkedList<Entry<K, V>>[] table;

    /**
     * Entry class to represent a key-value pair.
     * i created it as nested class to keep my implementation more simple
     */
    private static class Entry<K, V> {
        K key;
        V value;

        /**
         * Constructs a new entry with the given key and value.
         * @param key the key of the entry
         * @param value the value associated with the key
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    /**
     * here i initialize a hash map with a default capacity of 10 (10 array size).
     */
    public SeparateChainingHashMap() {
        this.capacity = 10; // default capacity
        table = new DoublyLinkedList[capacity];
    }

    /**
     * with get(key) i retrieve the value associated with the given key.
     */
    public V get(K key) {
        int index = hash(key);
        DoublyLinkedList<Entry<K, V>> chain = table[index];
        if (chain == null) {
            return null;
        }

        for (Entry<K, V> entry : chain) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }


    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new DoublyLinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // replacing the existing value
                return;
            }
        }

        table[index].add(new Entry<>(key, value));
        size++;

        if ((float) size / capacity >= LOAD_FACTOR) {
            remap();
        }
    }

    /**
     * Removes the entry for the specified key if it is present in the hashmap.
     *
     * here is how the remove method works:
     * 1. Computes the index in the array for the key using a hash function.
     * 2. Retrieves the chain at that index.
     * 3. Iterate through the chain to find an entry with the given key.
     * 4. If it finds it, it removes that entry from the chain and reduces the size of the hashmap.
     *
     * @param key the key whose mapping is to be removed
     */

    public void remove(K key) {
        int index = hash(key);
        DoublyLinkedList<Entry<K, V>> chain = table[index];
        if (chain != null) {
            Iterator<Entry<K, V>> iterator = chain.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> current = iterator.next();
                if (current.key.equals(key)) {
                    iterator.remove();
                    size--;
                    return;
                }
            }
        }
    }



    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (DoublyLinkedList<Entry<K, V>> chain : table) {
            if (chain != null) {
                for (Entry<K, V> entry : chain) {
                    keys.add(entry.key);
                }
            }
        }
        return keys;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(K key) {
        int index = hash(key);
        DoublyLinkedList<Entry<K, V>> chain = table[index];
        if (chain == null) {
            return false;
        }

        for (Entry<K, V> entry : chain) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }


    private void remap() {
        int oldCapacity = capacity;
        capacity *= 2;
        DoublyLinkedList<Entry<K, V>>[] oldTable = table;
        table = new DoublyLinkedList[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            DoublyLinkedList<Entry<K, V>> chain = oldTable[i];
            if (chain != null) {
                for (Entry<K, V> entry : chain) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SeparateChainingHashMap {");

        boolean first = true;
        for (DoublyLinkedList<Entry<K, V>> chain : table) {
            if (chain != null) {
                for (Entry<K, V> entry : chain) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(entry.key).append("=").append(entry.value);
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }


    private int hash(K key) {
        return Math.abs((key.hashCode()) % capacity);
    }

}

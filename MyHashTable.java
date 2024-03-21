package assignment.dictionary;

import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public class MyHashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private LinkedList<Entry<K, V>>[] table;
    private int size = 0;

    public MyHashTable() {
        table = (LinkedList<Entry<K, V>>[]) new LinkedList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int getHashCode(K key) {
        return Math.abs(key.hashCode()) % DEFAULT_CAPACITY;
    }

    public V put(K key, V value) {
        int index = getHashCode(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
        return null;
    }

    public V get(K key) {
        int index = getHashCode(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = getHashCode(key);
        Iterator<Entry<K, V>> iterator = table[index].iterator();

        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.key.equals(key)) {
                V value = entry.value;
                iterator.remove(); // Remove the entry from the LinkedList
                size--; // Decrement the size of the hashtable
                return value; // Return the value that was associated with the key
            }
        }
        return null; // Return null if the key was not found
    }


    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                values.add(entry.value);
            }
        }
        return values;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i].clear();
        }
        size = 0;
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}





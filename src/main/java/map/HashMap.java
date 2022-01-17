package map;

import java.lang.reflect.Array;
import java.util.*;

public class HashMap<K, V> implements Map<K, V> {

    public static class EntryNode<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        EntryNode<K, V> next;

        public EntryNode(K key, V value, EntryNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private EntryNode<K, V>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        table = (EntryNode<K, V>[]) Array.newInstance(EntryNode.class, capacity);
    }

    private int getIndex(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        return index;
    }

    private EntryNode<K, V> getEntry(Object key, int index) {
        if (index < 0) {
            index = getIndex(key);
        }
        for (EntryNode<K, V> curr = table[index]; curr != null; curr = curr.next) {
            if (key.equals(curr.key)) {
                return curr;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key, -1) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return entrySet().stream().anyMatch(kv -> value.equals(kv.getValue()));
    }

    @Override
    public V get(Object key) {
        EntryNode<K, V> entry = getEntry(key, -1);
        if (entry != null) {
            return entry.value;
        } else return null;
    }

    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        EntryNode<K, V> item = getEntry(key, index);
        if (item != null) {
            V oldValue = item.value;
            item.value = value;
            return oldValue;
        }
        table[index] = new EntryNode<>(key, value, table[index]);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = getIndex(key);
        EntryNode<K, V> parent = null;
        for (EntryNode<K, V> current = table[index]; current != null; current = current.next) {
            if (key.equals(current.key)) {
                if (parent == null) {
                    table[index] = current.next;
                } else {
                    parent.next = current.next;
                }
                size--;
                return current.value;
            }
            parent = current;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Entry<K, V> entry : entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> entry : entrySet()) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>() {
            @Override
            public int size() {
                return HashMap.this.size();
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<>() {
                    int tableIndex = -1;
                    EntryNode<K, V> curr = null;

                    {
                        findNext();
                    }

                    @Override
                    public boolean hasNext() {
                        return curr != null;
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        Map.Entry<K, V> temp = curr;
                        findNext();
                        return temp;
                    }

                    private void findNext() {
                        if (tableIndex >= table.length) {
                            return;
                        }
                        if (curr != null) {
                            curr = curr.next;
                        }
                        if (curr == null) {
                            for (tableIndex = tableIndex + 1; tableIndex < table.length; tableIndex++) {
                                curr = table[tableIndex];
                                if (curr != null) {
                                    break;
                                }
                            }
                        }
                    }
                };
            }
        };
    }
}

package multimap;

import map.HashMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class HashMultimap<K, V> implements Multimap<K, V> {
    private Map<K, Collection<V>> map;

    public HashMultimap(int capacity) {
        map = new HashMap<>(capacity);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return map;
    }

    @Override
    public int size() {
        return map.values().stream().mapToInt(Collection::size).sum();
    }

    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsEntry(K key, V value) {
        return map.get(key).contains(value);
    }

    @Override
    public boolean containsValue(V value) {
        return map.values().stream().anyMatch(vs -> vs.contains(value));
    }

    @Override
    public boolean put(K key, V value) {
        Collection<V> keyValues = get(key);
        if (keyValues == null) {
            keyValues = new HashSet<>();
        }
        boolean result = keyValues.add(value);
        map.put(key, keyValues);
        return result;
    }

    @Override
    public boolean putAll(K key, Iterable<? extends V> values) {
        return false;
    }

    @Override
    public boolean putAll(Map<? extends K, ? extends V> map) {
        return false;
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> map) {
        return false;
    }

    @Override
    public Collection<V> get(K key) {
        return map.get(key);
    }

    @Override
    public Collection<V> remove(K key) {
        return map.remove(key);
    }

    @Override
    public boolean removeEntry(K key, V value) {
        Collection<V> keyCollection = get(key);
        if (keyCollection != null) {
            if (keyCollection.size() > 1) {
                keyCollection.remove(value);
            } else {
                remove(key);
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        /*
        return map.entrySet().stream().flatMap(new Function<Map.Entry<K, Collection<V>>, Stream<Map.Entry<K, V>>>() {
            @Override
            public Stream<Map.Entry<K, V>> apply(Map.Entry<K, Collection<V>> entry) {
                return entry.getValue().stream().map(new Function<V, Map.Entry<K, V>>() {
                    @Override
                    public Map.Entry<K, V> apply(V v) {
                        return new HashMap.EntryNode<>(entry.getKey(), v, null);
                    }
                });
            }
        }).collect(Collectors.toSet());*/
        Set<Map.Entry<K, V>> entries = new HashSet<>();

        for (Map.Entry<K, Collection<V>> outerEntry : map.entrySet()) {
            for (V value : outerEntry.getValue()) {
                entries.add(new HashMap.EntryNode<>(outerEntry.getKey(), value, null));
            }
        }
        return entries;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values().stream().flatMap((Function<Collection<V>, Stream<V>>) Collection::stream).toList();
    }
}

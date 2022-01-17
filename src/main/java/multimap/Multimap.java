package multimap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsEntry(K key, V value);

    boolean containsValue(V value);

    boolean put(K key, V value);

    boolean putAll(K key, Iterable<? extends V> values);

    boolean putAll(Map<? extends K, ? extends V> map);

    boolean putAll(Multimap<? extends K, ? extends V> map);

    Collection<V> get(K key);

    Collection<V> remove(K key);

    boolean removeEntry(K key, V value);

    void clear();

    Collection<Map.Entry<K, V>> entrySet();

    Set<K> keySet();

    Collection<V> values();
}

package org.hexworks.zircon.internal.util

// TODO: placeholder while kotlinx.collections.immutable is not MPP
/**
 * A generic immutable collection that holds pairs of objects (keys and values) and supports efficiently retrieving
 * the value corresponding to each key. Map keys are unique; the map holds only one value for each key.
 * Methods in this interface support only read-only access to the immutable map.
 *
 * Modification operations are supported through the [PersistentMap] interface.
 *
 * Implementors of this interface take responsibility to be immutable.
 * Once constructed they must contain the same elements in the same order.
 *
 * @param K the type of map keys. The map is invariant on its key type, as it
 *          can accept key as a parameter (of [containsKey] for example) and return it in [keys] set.
 * @param V the type of map values. The map is covariant on its value type.
 */
interface ImmutableMap<K, out V>: Map<K, V> {

    override val keys: ImmutableSet<K>

    override val values: ImmutableCollection<V>

    override val entries: ImmutableSet<Map.Entry<K, V>>
}


/**
 * A generic persistent collection that holds pairs of objects (keys and values) and supports efficiently retrieving
 * the value corresponding to each key. Map keys are unique; the map holds only one value for each key.
 *
 * Modification operations return new instances of the persistent map with the modification applied.
 *
 * @param K the type of map keys. The map is invariant on its key type.
 * @param V the type of map values. The persistent map is covariant on its value type.
 */
interface PersistentMap<K, out V> : ImmutableMap<K, V> {
    /**
     * Returns the result of associating the specified [value] with the specified [key] in this map.
     *
     * If this map already contains a mapping for the key, the old value is replaced by the specified value.
     *
     * @return a new persistent map with the specified [value] associated with the specified [key];
     * or this instance if no modifications were made in the result of this operation.
     */
    fun put(key: K, value: @UnsafeVariance V): PersistentMap<K, V>

    /**
     * Returns the result of removing the specified [key] and its corresponding value from this map.
     *
     * @return a new persistent map with the specified [key] and its corresponding value removed;
     * or this instance if it contains no mapping for the key.
     */
    fun remove(key: K): PersistentMap<K, V>

    /**
     * Returns the result of removing the entry that maps the specified [key] to the specified [value].
     *
     * @return a new persistent map with the entry for the specified [key] and [value] removed;
     * or this instance if it contains no entry with the specified key and value.
     */
    fun remove(key: K, value: @UnsafeVariance V): PersistentMap<K, V>

    /**
     * Returns the result of merging the specified [m] map with this map.
     *
     * The effect of this call is equivalent to that of calling `put(k, v)` once for each
     * mapping from key `k` to value `v` in the specified map.
     *
     * @return a new persistent map with keys and values from the specified map [m] associated;
     * or this instance if no modifications were made in the result of this operation.
     */
    fun putAll(m: Map<out K, @UnsafeVariance V>): PersistentMap<K, V>  // m: Iterable<Map.Entry<K, V>> or Map<out K,V> or Iterable<Pair<K, V>>

    /**
     * Returns an empty persistent map.
     */
    fun clear(): PersistentMap<K, V>
}

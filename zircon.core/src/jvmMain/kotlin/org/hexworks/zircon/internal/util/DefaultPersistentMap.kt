package org.hexworks.zircon.internal.util

class DefaultPersistentMap<K, V>(private val backend: kotlinx.collections.immutable.PersistentMap<K, V>) : PersistentMap<K, V> {

    override val keys: ImmutableSet<K>
        get() = DefaultImmutableSet(backend.keys)

    override val values: ImmutableCollection<V>
        get() = DefaultImmutableCollection(backend.values)

    override val entries: ImmutableSet<Map.Entry<K, V>>
        get() = DefaultImmutableSet(backend.entries)

    override fun put(key: K, value: V): PersistentMap<K, V> = DefaultPersistentMap(backend.put(key, value))

    override fun remove(key: K): PersistentMap<K, V> = DefaultPersistentMap(backend.remove(key))

    override fun remove(key: K, value: V): PersistentMap<K, V> = DefaultPersistentMap(backend.remove(key, value))

    override fun putAll(m: Map<out K, V>): PersistentMap<K, V> = DefaultPersistentMap(backend.putAll(m))

    override fun clear(): PersistentMap<K, V> = DefaultPersistentMap(backend.clear())

    override val size: Int = backend.size

    override fun containsKey(key: K): Boolean = backend.containsKey(key)

    override fun containsValue(value: V): Boolean = backend.containsValue(value)

    override fun get(key: K): V? = backend[key]

    override fun isEmpty(): Boolean = backend.isEmpty()
}

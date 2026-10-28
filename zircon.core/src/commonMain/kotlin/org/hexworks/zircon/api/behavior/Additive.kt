package org.hexworks.zircon.api.behavior

public interface Additive<T> {
    public operator fun plus(other: T): T
    public operator fun minus(other: T): T
}

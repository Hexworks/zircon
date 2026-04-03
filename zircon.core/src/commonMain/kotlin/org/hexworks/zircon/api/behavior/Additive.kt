package org.hexworks.zircon.api.behavior

interface Additive<T> {
    operator fun plus(other: T): T
    operator fun minus(other: T): T
}

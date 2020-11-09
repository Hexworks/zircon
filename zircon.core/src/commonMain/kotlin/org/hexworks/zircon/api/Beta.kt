package org.hexworks.zircon.api

import kotlin.annotation.AnnotationTarget.*

/**
 * Anything that's annotated with [Beta] is considered to be an **incomplete**
 * implementation that's subject to change.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(CLASS, FUNCTION, TYPE, FIELD, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class Beta

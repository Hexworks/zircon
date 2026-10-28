package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.AppConfigKey
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl

@ZirconDsl
public class CustomPropertyBuilder<T : Any> : Builder<Pair<AppConfigKey<T>, T>> {

    public var key: AppConfigKey<T>? = null
    public var value: T? = null

    override fun build(): Pair<AppConfigKey<T>, T> {
        requireNotNull(key) {
            "key is missing."
        }
        requireNotNull(value) {
            "value is missing"
        }
        return key!! to value!!
    }
}
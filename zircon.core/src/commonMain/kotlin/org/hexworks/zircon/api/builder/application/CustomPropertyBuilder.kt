package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.AppConfigKey
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class CustomPropertyBuilder<T : Any> : Builder<Pair<AppConfigKey<T>, T>> {

    var key: AppConfigKey<T>? = null
    var value: T? = null

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
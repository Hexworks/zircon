package org.hexworks.zircon.examples.utilities

import org.hexworks.zircon.api.Maybes
import org.hexworks.zircon.api.kotlin.flatMap
import org.hexworks.zircon.api.kotlin.ifPresent
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.kotlin.orElseThrow

object MaybeExampleKotlin {

    @JvmStatic
    fun main(args: Array<String>) {

        // just like with Optional
        val emptyMaybe = Maybes.empty<String>()
        println(emptyMaybe)

        // only called if the Maybe has a value in it
        emptyMaybe.ifPresent { value -> println("Value is : $value") }

        // returns the "other value" if this maybe has a value, otherwise an empty maybe
        println(emptyMaybe.flatMap { Maybes.of("Other value") }.toString())

        val otherValue = Maybes.of("bar")

        // transforms the value stored in `otherValue` only if there is a value and returns it
        val one = otherValue.map {
            println("I am only printed because there is a value in `otherValue`")
            1
        }

        // returns the value
        println(one.get())


        // returns the value of this maybe or if it has no value returns the supplied value.
        val foo = emptyMaybe.orElse("foo")
        println(foo)

        try {
            emptyMaybe.orElseThrow { IllegalArgumentException("Not present") }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

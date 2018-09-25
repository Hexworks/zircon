package org.hexworks.zircon.examples.utilities;

import org.hexworks.zircon.api.Maybes;
import org.hexworks.zircon.api.util.Maybe;
import org.hexworks.zircon.api.util.Supplier;

public class MaybeExampleJava {

    public static void main(String[] args) {

        // just like with Optional
        Maybe<String> emptyMaybe = Maybes.empty();
        System.out.println(emptyMaybe);

        // only called if the Maybe has a value in it
        emptyMaybe.ifPresent(value -> System.out.println("Value is : " + value));

        // returns the "other value" if this maybe has a value, otherwise an empty maybe
        System.out.println(emptyMaybe.flatMap(value -> Maybes.of("Other value")).toString());

        Maybe<String> otherValue = Maybes.of("bar");

                // transforms the value stored in `otherValue` only if there is a value and returns it
        Maybe<Integer> one = otherValue.map(value -> {
            System.out.println("I am only printed because there is a value in `otherValue`");
            return 1;
        });

        // returns the value
        System.out.println(one.get());


        // returns the value of this maybe or if it has no value returns the supplied value.
        String foo = emptyMaybe.orElse("foo");
        System.out.println(foo);

        try {
            emptyMaybe.orElseThrow((Supplier<Throwable>) () -> new IllegalArgumentException("Not present"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

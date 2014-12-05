hungarian-checker
=================

An example of enforcing [Hungarian Notation](https://en.wikipedia.org/wiki/Hungarian_notation) in Java using the [Checker Framework](http://checkerframework.org).

__System Requirements__

1. The [Checker Framework](http://checkerframework.org)
2. Java 8, or the JSR 308 annotation tools (available on the Checker Framework website)

__How it Works__

The checker defines two [type annotations](http://www.infoq.com/articles/Type-Annotations-in-Java-8) `@Encrypted` and `@MaybeEncrypted` that specify whether or not an expression is encrypted.

The checker enforces that expressions with type `@MaybeEncrypted` are not used where a value with type `@Encrypted`  is expected.

By default, the checker assumes everything is `@MaybeEncrypted`. The developer can write an `@Encrypted` annotation on a type to denote that the expression is encrypted.  The checker also supports Hungarian Notation: variables and parameters that start with the "e" prefix are automatically given the `@Encrypted` annotation.

__Running the Checker__

To run the checker, use `javac` with the `-processor com.toddschiller.checker.HungarianChecker` option. 
Remember to include the Hungarian Checker and the Checker Framework on the Java classpath (e.g., using the `-cp` flag).

```
javac -processor com.toddschiller.checker.HungarianChecker MyFile.java
```

The run the checker in debug mode, use the `-Alint=debugSpew` flag.

__Example Output__

Example source:
```
public @Encrypted String encrypt(String str){ ... }

// The eMsg parameter is given the @Encrypted annotation because of the prefix "e"
public void sendOverNetwork(String eMsg){ ... }

public void shouldWarn() {

  String msg = "Top secret message";

  // Warning! msg is known to be @MaybeEncrypted
  sendOverNetwork(msg);
  
  msg = encrypt(msg);
  
  // Safe! msg is known to be @Encrypted
  sendOverNetwork(msg);
}
```

The corresponding checker output:

```
HungarianExample.java:29: error: [argument.type.incompatible] incompatible types in argument.
        sendOverNetwork(msg);
                        ^
  found   : @MaybeEncrypted String
  required: @Encrypted String
```


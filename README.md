hungarian-checker
=================

An example of enforcing [Hungarian Notation](https://en.wikipedia.org/wiki/Hungarian_notation) in Java using the [Checker Framework](http://checkerframework.org).

__System Requirements__

1. The [Checker Framework](http://checkerframework.org)
2. Java 8, or the JSR 308 annotation tools (available on the Checker Framework website)

__How it Works__

The checker defines two [type annotations](http://www.infoq.com/articles/Type-Annotations-in-Java-8) `@Safe` and `@Unsafe` that specify whether or not an expression contains has been encoded/escaped.

The checker enforces that expressions with type `@Unsafe` are not used where a value with type `@Safe`  is expected.

By default, the checker assumes everything is `@Unsafe`. The developer can write a `@Safe` annotation on a type to denote that the expression is encoded/escaped.  The checker also supports Hungarian Notation: variables and parameters that start with the "s" prefix are automatically given the `@Safe` annotation.

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
public @Safe String encode(String str){ ... }
public @Unsafe String getUserInput(String str){ ... }

// The sQuery parameter is given the @Safe annotation because of the prefix "s"
public void executeSqlQuery(String sQuery){ ... }

public void shouldWarn() {
  String user = getUserInput();

  // WARNING: user is known to be @Unsafe
  executeSqlQuery("SELECT * FROM table WHERE user='" + user + "'");
 
  user = encode(user);

  // SAFE: user is known to be @Safe
  executeSqlQuery("SELECT * FROM table WHERE user='" + user + "'");
}
```

The corresponding checker output:

```
HungarianExample.java:26: error: [argument.type.incompatible] incompatible types in argument.
        executeSqlQuery("SELECT * FROM table WHERE user='" + user + "'");
                                                                  ^
  found   : @Unsafe String
  required: @Safe String
```


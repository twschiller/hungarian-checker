package com.toddschiller.checker.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeQualifier;

/**
 * By default, expressions may be either encrypted or unencrypted.
 * @author Todd Schiller
 */
@TypeQualifier
@SubtypeOf({})
@DefaultQualifierInHierarchy
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE_USE, ElementType.TYPE_PARAMETER })
public @interface MaybeEncrypted {
    // NOP
}

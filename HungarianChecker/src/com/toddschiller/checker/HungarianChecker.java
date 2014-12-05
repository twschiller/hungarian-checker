package com.toddschiller.checker;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.qual.TypeQualifiers;
import org.checkerframework.framework.source.SupportedLintOptions;

import com.toddschiller.checker.qual.Unsafe;
import com.toddschiller.checker.qual.Safe;

/**
 * A checker for enforcing safe/unsafe value patterns. Demonstrates the use of 
 * Hungarian notation for type introduction.
 * @author Todd Schiller
 */
@SupportedLintOptions({ "debugSpew" })
@TypeQualifiers({ Unsafe.class, Safe.class })
public class HungarianChecker extends BaseTypeChecker {
    // NOP
}

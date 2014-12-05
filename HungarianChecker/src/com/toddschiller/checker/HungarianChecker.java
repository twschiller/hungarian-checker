package com.toddschiller.checker;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.qual.TypeQualifiers;
import org.checkerframework.framework.source.SupportedLintOptions;

import com.toddschiller.checker.qual.MaybeEncrypted;
import com.toddschiller.checker.qual.Encrypted;

/**
 * A checker for enforcing encryption patterns. Demonstrates the use of 
 * Hungarian notation for type introduction.
 * @author Todd Schiller
 */
@SupportedLintOptions({ "debugSpew" })
@TypeQualifiers({ MaybeEncrypted.class, Encrypted.class })
public class HungarianChecker extends BaseTypeChecker {
    // NOP
}

package com.toddschiller.checker;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;

/**
 * A {@link org.checkerframework.framework.source.SourceVisitor} that performs assignment 
 * and pseudo-assignment checking, method invocation checking, and assignability checking.
 * 
 * <p>
 * 
 * Uses the default implementations provided by {@link BaseTypeVisitor}.
 * 
 * @author Todd Schiller
 */
public class HungarianVisitor extends BaseTypeVisitor<HungarianAnnotatedTypeFactory> {
    public HungarianVisitor(BaseTypeChecker checker) {
        super(checker);
    }
}

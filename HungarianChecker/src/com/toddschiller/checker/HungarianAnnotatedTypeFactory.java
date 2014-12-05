package com.toddschiller.checker;

import java.util.List;
import java.util.regex.Pattern;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedExecutableType;
import org.checkerframework.framework.type.ListTreeAnnotator;
import org.checkerframework.framework.type.TreeAnnotator;
import org.checkerframework.framework.type.TypeAnnotator;
import org.checkerframework.javacutil.AnnotationUtils;

import com.sun.source.tree.VariableTree;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Symbol.VarSymbol;
import com.toddschiller.checker.qual.Encrypted;

/**
 * This type factory annotates formal parameters and variables as {@code @Encrypted} if their
 * name starts with the "e" prefix.
 * @author Todd Schiller
 */
public class HungarianAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    protected final boolean debugSpew;

    protected final AnnotationMirror ENCRYPTED;
    
    /**
     * Regex for the "e" prefix. 
     */
    private final Pattern encryptedRegex = Pattern.compile("e[A-Z_].*");
    
    public HungarianAnnotatedTypeFactory(BaseTypeChecker checker) {
        // use flow inference
        super(checker, true);

        debugSpew = checker.getLintOption("debugSpew", false);
        
        ENCRYPTED = AnnotationUtils.fromClass(elements, Encrypted.class);
        
        this.postInit();
    }
      
    @Override
    protected TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(new HungarianTreeAnnotator(), super.createTreeAnnotator());
    }

    @Override
    protected TypeAnnotator createTypeAnnotator() {
        return new HungarianTypeAnnotator();
    }
    
    private class HungarianTypeAnnotator extends TypeAnnotator {
        public HungarianTypeAnnotator() {
            super(HungarianAnnotatedTypeFactory.this);
        }

        @Override
        public Void visitExecutable(AnnotatedExecutableType t, Void p) {
            ExecutableElement elt = t.getElement();
            
            if (elt instanceof MethodSymbol){
                MethodSymbol method = (MethodSymbol) elt;
                
                if (debugSpew){
                    System.out.println("Visiting method " + method.getQualifiedName() + "(...)");
                }
                
                List<? extends VarSymbol> params = method.params();
                List<AnnotatedTypeMirror> paramTypes = t.getParameterTypes();
                assert params.size() == paramTypes.size();
               
                for (int i = 0; i < params.size(); i++){
                    VarSymbol param = params.get(i);
                    
                    if (encryptedRegex.matcher(params.get(i).getSimpleName()).matches()){
                        if (debugSpew){
                            System.out.println("Considering parameter " + param + " [MATCH]");
                        }
                        paramTypes.get(i).replaceAnnotation(ENCRYPTED);
                    }else if (debugSpew){
                        System.out.println("Considering parameter " + param + " [NO MATCH]");
                    }
                }
            }
            
            return super.visitExecutable(t, p);
        }
    }
    
    private class HungarianTreeAnnotator extends TreeAnnotator {
        public HungarianTreeAnnotator() {
            super(HungarianAnnotatedTypeFactory.this);
        }

        @Override
        public Void visitVariable(VariableTree node, AnnotatedTypeMirror type) {
            
            if (debugSpew){
                System.out.println("Visiting variable " + node.getName());
            }
            
            if (encryptedRegex.matcher(node.getName()).matches()){
                if (debugSpew){
                    System.out.println("Considering parameter " + node.getName() + " [MATCH]");
                }
                type.replaceAnnotation(ENCRYPTED);
                
            }else if (debugSpew){
                System.out.println("Considering parameter " + node.getName() + " [NO MATCH]");
            }
            
            return super.visitVariable(node, type);
        }
    }
}

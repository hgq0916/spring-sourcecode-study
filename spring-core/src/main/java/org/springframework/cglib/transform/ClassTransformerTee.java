//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.transform;

import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.core.Constants;

public class ClassTransformerTee extends ClassTransformer {
    private ClassVisitor branch;

    public ClassTransformerTee(ClassVisitor branch) {
        super(Constants.ASM_API);
        this.branch = branch;
    }

    @Override
    public void setTarget(ClassVisitor target) {
        this.cv = new ClassVisitorTee(this.branch, target);
    }
}

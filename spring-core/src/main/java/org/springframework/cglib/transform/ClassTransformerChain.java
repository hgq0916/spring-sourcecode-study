//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.transform;

import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;

public class ClassTransformerChain extends AbstractClassTransformer {
    private ClassTransformer[] chain;

    public ClassTransformerChain(ClassTransformer[] chain) {
        this.chain = (ClassTransformer[])((ClassTransformer[])chain.clone());
    }

    @Override
    public void setTarget(ClassVisitor v) {
        super.setTarget(this.chain[0]);
        ClassVisitor next = v;

        for(int i = this.chain.length - 1; i >= 0; --i) {
            this.chain[i].setTarget((ClassVisitor)next);
            next = this.chain[i];
        }

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return this.cv.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ClassTransformerChain{");

        for(int i = 0; i < this.chain.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }

            sb.append(this.chain[i].toString());
        }

        sb.append("}");
        return sb.toString();
    }
}

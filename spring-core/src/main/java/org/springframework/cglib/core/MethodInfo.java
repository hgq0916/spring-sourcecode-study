//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.core;

import org.springframework.asm.Type;

public abstract class MethodInfo {
    protected MethodInfo() {
    }

    public abstract ClassInfo getClassInfo();

    public abstract int getModifiers();

    public abstract Signature getSignature();

    public abstract Type[] getExceptionTypes();

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return !(o instanceof MethodInfo) ? false : this.getSignature().equals(((MethodInfo)o).getSignature());
        }
    }

    @Override
    public int hashCode() {
        return this.getSignature().hashCode();
    }

    @Override
    public String toString() {
        return this.getSignature().toString();
    }
}

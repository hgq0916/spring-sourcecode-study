//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.core;

import org.springframework.asm.Type;

public interface FieldTypeCustomizer extends KeyFactoryCustomizer {
    void customize(CodeEmitter var1, int var2, Type var3);

    Type getOutType(int var1, Type var2);
}

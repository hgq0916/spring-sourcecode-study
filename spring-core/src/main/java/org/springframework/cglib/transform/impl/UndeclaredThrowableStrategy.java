//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.transform.impl;

import org.springframework.cglib.core.ClassGenerator;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.cglib.core.TypeUtils;
import org.springframework.cglib.transform.ClassTransformer;
import org.springframework.cglib.transform.MethodFilter;
import org.springframework.cglib.transform.MethodFilterTransformer;
import org.springframework.cglib.transform.TransformingClassGenerator;

public class UndeclaredThrowableStrategy extends DefaultGeneratorStrategy {
    private Class wrapper;
    private static final MethodFilter TRANSFORM_FILTER = new MethodFilter() {
        @Override
        public boolean accept(int access, String name, String desc, String signature, String[] exceptions) {
            return !TypeUtils.isPrivate(access) && name.indexOf(36) < 0;
        }
    };

    public UndeclaredThrowableStrategy(Class wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    protected ClassGenerator transform(ClassGenerator cg) throws Exception {
        ClassTransformer tr = new UndeclaredThrowableTransformer(this.wrapper);
        tr = new MethodFilterTransformer(TRANSFORM_FILTER, tr);
        return new TransformingClassGenerator(cg, tr);
    }
}

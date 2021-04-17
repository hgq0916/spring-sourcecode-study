//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.cglib.core.Constants;
import org.springframework.cglib.core.Signature;

class BridgeMethodResolver {
    private final Map declToBridge;
    private final ClassLoader classLoader;

    public BridgeMethodResolver(Map declToBridge, ClassLoader classLoader) {
        this.declToBridge = declToBridge;
        this.classLoader = classLoader;
    }

    public Map resolveAll() {
        Map resolved = new HashMap();
        Iterator entryIter = this.declToBridge.entrySet().iterator();

        while(entryIter.hasNext()) {
            Entry entry = (Entry)entryIter.next();
            Class owner = (Class)entry.getKey();
            Set bridges = (Set)entry.getValue();

            try {
                InputStream is = this.classLoader.getResourceAsStream(owner.getName().replace('.', '/') + ".class");
                if (is == null) {
                    return resolved;
                }

                try {
                    (new ClassReader(is)).accept(new BridgeMethodResolver.BridgedFinder(bridges, resolved), 6);
                } finally {
                    is.close();
                }
            } catch (IOException var11) {
                ;
            }
        }

        return resolved;
    }

    private static class BridgedFinder extends ClassVisitor {
        private Map resolved;
        private Set eligibleMethods;
        private Signature currentMethod = null;

        BridgedFinder(Set eligibleMethods, Map resolved) {
            super(Constants.ASM_API);
            this.resolved = resolved;
            this.eligibleMethods = eligibleMethods;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            Signature sig = new Signature(name, desc);
            if (this.eligibleMethods.remove(sig)) {
                this.currentMethod = sig;
                return new MethodVisitor(Constants.ASM_API) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        if ((opcode == 183 || itf && opcode == 185) && BridgedFinder.this.currentMethod != null) {
                            Signature target = new Signature(name, desc);
                            if (!target.equals(BridgedFinder.this.currentMethod)) {
                                BridgedFinder.this.resolved.put(BridgedFinder.this.currentMethod, target);
                            }

                            BridgedFinder.this.currentMethod = null;
                        }

                    }
                };
            } else {
                return null;
            }
        }
    }
}

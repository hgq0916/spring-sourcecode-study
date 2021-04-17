//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.transform;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import org.springframework.asm.Attribute;
import org.springframework.asm.ClassReader;
import org.springframework.cglib.core.ClassGenerator;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.DebuggingClassWriter;

public abstract class AbstractClassLoader extends ClassLoader {
    private ClassFilter filter;
    private ClassLoader classPath;
    private static ProtectionDomain DOMAIN = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
        @Override
        public Object run() {
            return AbstractClassLoader.class.getProtectionDomain();
        }
    });

    protected AbstractClassLoader(ClassLoader parent, ClassLoader classPath, ClassFilter filter) {
        super(parent);
        this.filter = filter;
        this.classPath = classPath;
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        Class loaded = this.findLoadedClass(name);
        if (loaded != null && loaded.getClassLoader() == this) {
            return loaded;
        } else if (!this.filter.accept(name)) {
            return super.loadClass(name);
        } else {
            ClassReader r;
            try {
                InputStream is = this.classPath.getResourceAsStream(name.replace('.', '/') + ".class");
                if (is == null) {
                    throw new ClassNotFoundException(name);
                }

                try {
                    r = new ClassReader(is);
                } finally {
                    is.close();
                }
            } catch (IOException var16) {
                throw new ClassNotFoundException(name + ":" + var16.getMessage());
            }

            try {
                DebuggingClassWriter w = new DebuggingClassWriter(2);
                this.getGenerator(r).generateClass(w);
                byte[] b = w.toByteArray();
                Class c = super.defineClass(name, b, 0, b.length, DOMAIN);
                this.postProcess(c);
                return c;
            } catch (RuntimeException var12) {
                throw var12;
            } catch (Error var13) {
                throw var13;
            } catch (Exception var14) {
                throw new CodeGenerationException(var14);
            }
        }
    }

    protected ClassGenerator getGenerator(ClassReader r) {
        return new ClassReaderGenerator(r, this.attributes(), this.getFlags());
    }

    protected int getFlags() {
        return 0;
    }

    protected Attribute[] attributes() {
        return null;
    }

    protected void postProcess(Class c) {
    }
}

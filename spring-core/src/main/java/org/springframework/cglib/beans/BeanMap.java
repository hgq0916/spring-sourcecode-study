//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.beans;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.AbstractClassGenerator.Source;

public abstract class BeanMap implements Map {
    public static final int REQUIRE_GETTER = 1;
    public static final int REQUIRE_SETTER = 2;
    protected Object bean;

    public static BeanMap create(Object bean) {
        BeanMap.Generator gen = new BeanMap.Generator();
        gen.setBean(bean);
        return gen.create();
    }

    public abstract BeanMap newInstance(Object var1);

    public abstract Class getPropertyType(String var1);

    protected BeanMap() {
    }

    protected BeanMap(Object bean) {
        this.setBean(bean);
    }

    @Override
    public Object get(Object key) {
        return this.get(this.bean, key);
    }

    @Override
    public Object put(Object key, Object value) {
        return this.put(this.bean, key, value);
    }

    public abstract Object get(Object var1, Object var2);

    public abstract Object put(Object var1, Object var2, Object var3);

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return this.bean;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.keySet().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator it = this.keySet().iterator();

        Object v;
        do {
            if (!it.hasNext()) {
                return false;
            }

            v = this.get(it.next());
        } while((value != null || v != null) && (value == null || !value.equals(v)));

        return true;
    }

    @Override
    public int size() {
        return this.keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map t) {
        Iterator it = t.keySet().iterator();

        while(it.hasNext()) {
            Object key = it.next();
            this.put(key, t.get(key));
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Map) {
            Map other = (Map)o;
            if (this.size() != other.size()) {
                return false;
            } else {
                Iterator it = this.keySet().iterator();

                while(true) {
                    if (!it.hasNext()) {
                        return true;
                    }

                    Object key = it.next();
                    if (!other.containsKey(key)) {
                        return false;
                    }

                    Object v1 = this.get(key);
                    Object v2 = other.get(key);
                    if (v1 == null) {
                        if (v2 == null) {
                            continue;
                        }
                        break;
                    } else if (!v1.equals(v2)) {
                        break;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int code = 0;

        Object key;
        Object value;
        for(Iterator it = this.keySet().iterator(); it.hasNext(); code += (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode())) {
            key = it.next();
            value = this.get(key);
        }

        return code;
    }

    @Override
    public Set entrySet() {
        HashMap copy = new HashMap();
        Iterator it = this.keySet().iterator();

        while(it.hasNext()) {
            Object key = it.next();
            copy.put(key, this.get(key));
        }

        return Collections.unmodifiableMap(copy).entrySet();
    }

    @Override
    public Collection values() {
        Set keys = this.keySet();
        List values = new ArrayList(keys.size());
        Iterator it = keys.iterator();

        while(it.hasNext()) {
            values.add(this.get(it.next()));
        }

        return Collections.unmodifiableCollection(values);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('{');
        Iterator it = this.keySet().iterator();

        while(it.hasNext()) {
            Object key = it.next();
            sb.append(key);
            sb.append('=');
            sb.append(this.get(key));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append('}');
        return sb.toString();
    }

    public static class Generator extends AbstractClassGenerator {
        private static final Source SOURCE = new Source(BeanMap.class.getName());
        private static final BeanMap.Generator.BeanMapKey KEY_FACTORY;
        private Object bean;
        private Class beanClass;
        private int require;

        public Generator() {
            super(SOURCE);
        }

        public void setBean(Object bean) {
            this.bean = bean;
            if (bean != null) {
                this.beanClass = bean.getClass();
            }

        }

        public void setBeanClass(Class beanClass) {
            this.beanClass = beanClass;
        }

        public void setRequire(int require) {
            this.require = require;
        }

        @Override
        protected ClassLoader getDefaultClassLoader() {
            return this.beanClass.getClassLoader();
        }

        @Override
        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(this.beanClass);
        }

        public BeanMap create() {
            if (this.beanClass == null) {
                throw new IllegalArgumentException("Class of bean unknown");
            } else {
                this.setNamePrefix(this.beanClass.getName());
                return (BeanMap)super.create(KEY_FACTORY.newInstance(this.beanClass, this.require));
            }
        }

        @Override
        public void generateClass(ClassVisitor v) throws Exception {
            new BeanMapEmitter(v, this.getClassName(), this.beanClass, this.require);
        }

        @Override
        protected Object firstInstance(Class type) {
            return ((BeanMap)ReflectUtils.newInstance(type)).newInstance(this.bean);
        }

        @Override
        protected Object nextInstance(Object instance) {
            return ((BeanMap)instance).newInstance(this.bean);
        }

        static {
            KEY_FACTORY = (BeanMap.Generator.BeanMapKey)KeyFactory.create(BeanMap.Generator.BeanMapKey.class, KeyFactory.CLASS_BY_NAME);
        }

        interface BeanMapKey {
            Object newInstance(Class var1, int var2);
        }
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.beans;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FixedKeySet extends AbstractSet {
    private Set set;
    private int size;

    public FixedKeySet(String[] keys) {
        this.size = keys.length;
        this.set = Collections.unmodifiableSet(new HashSet(Arrays.asList(keys)));
    }

    @Override
    public Iterator iterator() {
        return this.set.iterator();
    }

    @Override
    public int size() {
        return this.size;
    }
}

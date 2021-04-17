//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.core;

import java.lang.ref.WeakReference;

public class WeakCacheKey<T> extends WeakReference<T> {
    private final int hash;

    public WeakCacheKey(T referent) {
        super(referent);
        this.hash = referent.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WeakCacheKey)) {
            return false;
        } else {
            Object ours = this.get();
            Object theirs = ((WeakCacheKey)obj).get();
            return ours != null && theirs != null && ours.equals(theirs);
        }
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public String toString() {
        T t = this.get();
        return t == null ? "Clean WeakIdentityKey, hash: " + this.hash : t.toString();
    }
}

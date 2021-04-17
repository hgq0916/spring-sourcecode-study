//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cglib.util;

import java.util.Comparator;
import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ClassesKey;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.AbstractClassGenerator.Source;

public abstract class ParallelSorter extends SorterTemplate {
    protected Object[] a;
    private ParallelSorter.Comparer comparer;

    protected ParallelSorter() {
    }

    public abstract ParallelSorter newInstance(Object[] var1);

    public static ParallelSorter create(Object[] arrays) {
        ParallelSorter.Generator gen = new ParallelSorter.Generator();
        gen.setArrays(arrays);
        return gen.create();
    }

    private int len() {
        return ((Object[])((Object[])this.a[0])).length;
    }

    public void quickSort(int index) {
        this.quickSort(index, 0, this.len(), (Comparator)null);
    }

    public void quickSort(int index, int lo, int hi) {
        this.quickSort(index, lo, hi, (Comparator)null);
    }

    public void quickSort(int index, Comparator cmp) {
        this.quickSort(index, 0, this.len(), cmp);
    }

    public void quickSort(int index, int lo, int hi, Comparator cmp) {
        this.chooseComparer(index, cmp);
        super.quickSort(lo, hi - 1);
    }

    public void mergeSort(int index) {
        this.mergeSort(index, 0, this.len(), (Comparator)null);
    }

    public void mergeSort(int index, int lo, int hi) {
        this.mergeSort(index, lo, hi, (Comparator)null);
    }

    public void mergeSort(int index, Comparator cmp) {
        this.mergeSort(index, 0, this.len(), cmp);
    }

    public void mergeSort(int index, int lo, int hi, Comparator cmp) {
        this.chooseComparer(index, cmp);
        super.mergeSort(lo, hi - 1);
    }

    private void chooseComparer(int index, Comparator cmp) {
        Object array = this.a[index];
        Class type = array.getClass().getComponentType();
        if (type.equals(Integer.TYPE)) {
            this.comparer = new ParallelSorter.IntComparer((int[])((int[])array));
        } else if (type.equals(Long.TYPE)) {
            this.comparer = new ParallelSorter.LongComparer((long[])((long[])array));
        } else if (type.equals(Double.TYPE)) {
            this.comparer = new ParallelSorter.DoubleComparer((double[])((double[])array));
        } else if (type.equals(Float.TYPE)) {
            this.comparer = new ParallelSorter.FloatComparer((float[])((float[])array));
        } else if (type.equals(Short.TYPE)) {
            this.comparer = new ParallelSorter.ShortComparer((short[])((short[])array));
        } else if (type.equals(Byte.TYPE)) {
            this.comparer = new ParallelSorter.ByteComparer((byte[])((byte[])array));
        } else if (cmp != null) {
            this.comparer = new ParallelSorter.ComparatorComparer((Object[])((Object[])array), cmp);
        } else {
            this.comparer = new ParallelSorter.ObjectComparer((Object[])((Object[])array));
        }

    }

    @Override
    protected int compare(int i, int j) {
        return this.comparer.compare(i, j);
    }

    public static class Generator extends AbstractClassGenerator {
        private static final Source SOURCE = new Source(ParallelSorter.class.getName());
        private Object[] arrays;

        public Generator() {
            super(SOURCE);
        }

        @Override
        protected ClassLoader getDefaultClassLoader() {
            return null;
        }

        public void setArrays(Object[] arrays) {
            this.arrays = arrays;
        }

        public ParallelSorter create() {
            return (ParallelSorter)super.create(ClassesKey.create(this.arrays));
        }

        @Override
        public void generateClass(ClassVisitor v) throws Exception {
            if (this.arrays.length == 0) {
                throw new IllegalArgumentException("No arrays specified to sort");
            } else {
                for(int i = 0; i < this.arrays.length; ++i) {
                    if (!this.arrays[i].getClass().isArray()) {
                        throw new IllegalArgumentException(this.arrays[i].getClass() + " is not an array");
                    }
                }

                new ParallelSorterEmitter(v, this.getClassName(), this.arrays);
            }
        }

        @Override
        protected Object firstInstance(Class type) {
            return ((ParallelSorter)ReflectUtils.newInstance(type)).newInstance(this.arrays);
        }

        @Override
        protected Object nextInstance(Object instance) {
            return ((ParallelSorter)instance).newInstance(this.arrays);
        }
    }

    static class ByteComparer implements ParallelSorter.Comparer {
        private byte[] a;

        public ByteComparer(byte[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            return this.a[i] - this.a[j];
        }
    }

    static class ShortComparer implements ParallelSorter.Comparer {
        private short[] a;

        public ShortComparer(short[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            return this.a[i] - this.a[j];
        }
    }

    static class DoubleComparer implements ParallelSorter.Comparer {
        private double[] a;

        public DoubleComparer(double[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            double vi = this.a[i];
            double vj = this.a[j];
            return vi == vj ? 0 : (vi > vj ? 1 : -1);
        }
    }

    static class FloatComparer implements ParallelSorter.Comparer {
        private float[] a;

        public FloatComparer(float[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            float vi = this.a[i];
            float vj = this.a[j];
            return vi == vj ? 0 : (vi > vj ? 1 : -1);
        }
    }

    static class LongComparer implements ParallelSorter.Comparer {
        private long[] a;

        public LongComparer(long[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            long vi = this.a[i];
            long vj = this.a[j];
            return vi == vj ? 0 : (vi > vj ? 1 : -1);
        }
    }

    static class IntComparer implements ParallelSorter.Comparer {
        private int[] a;

        public IntComparer(int[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            return this.a[i] - this.a[j];
        }
    }

    static class ObjectComparer implements ParallelSorter.Comparer {
        private Object[] a;

        public ObjectComparer(Object[] a) {
            this.a = a;
        }

        @Override
        public int compare(int i, int j) {
            return ((Comparable)this.a[i]).compareTo(this.a[j]);
        }
    }

    static class ComparatorComparer implements ParallelSorter.Comparer {
        private Object[] a;
        private Comparator cmp;

        public ComparatorComparer(Object[] a, Comparator cmp) {
            this.a = a;
            this.cmp = cmp;
        }

        @Override
        public int compare(int i, int j) {
            return this.cmp.compare(this.a[i], this.a[j]);
        }
    }

    interface Comparer {
        int compare(int var1, int var2);
    }
}

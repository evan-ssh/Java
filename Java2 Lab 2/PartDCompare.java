import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

public class PartDCompare {
    private static final int[] FULL_SIZES = {1_000, 10_000, 100_000, 1_000_000, 10_000_000};
    private static final int[] QUICK_SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final int WARMUP_ROUNDS = 3;
    private static final int TRIALS = 5;

    private static volatile long sink;
    private static volatile Object objectSink;
    private static final int BASE = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);

        boolean quick = args.length > 0 && args[0].equalsIgnoreCase("quick");
        int[] sizes = quick ? QUICK_SIZES : FULL_SIZES;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("compareD.csv"))) {
            writer.write("collection,n,operation,yourNsOp,jdkNsOp,yourBytesElem,jdkBytesElem,yourBigO,jdkBigO");
            writer.newLine();

            runArrayListComparison(writer, sizes);
            runHashMapComparison(writer, sizes);
        }

        System.out.println("Wrote compareD.csv");
        System.out.println("sink=" + sink);
    }

    private static void runArrayListComparison(BufferedWriter writer, int[] sizes) throws IOException {
        double[] myAdd = new double[sizes.length];
        double[] jdkAdd = new double[sizes.length];
        double[] myGet = new double[sizes.length];
        double[] jdkGet = new double[sizes.length];
        double[] myContains = new double[sizes.length];
        double[] jdkContains = new double[sizes.length];

        long[] myBytes = new long[sizes.length];
        long[] jdkBytes = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];

            myAdd[i] = medianNs(() -> timeMyArrayListAdd(n));
            jdkAdd[i] = medianNs(() -> timeJdkArrayListAdd(n));

            myGet[i] = medianNs(() -> timeMyArrayListGet(n));
            jdkGet[i] = medianNs(() -> timeJdkArrayListGet(n));

            myContains[i] = medianNs(() -> timeMyArrayListContains(n));
            jdkContains[i] = medianNs(() -> timeJdkArrayListContains(n));

            myBytes[i] = measureBytesPerElement(() -> buildMyArrayList(n), n);
            jdkBytes[i] = measureBytesPerElement(() -> buildJdkArrayList(n), n);
        }

        writeRows(writer, "MyArrayList vs ArrayList", sizes, "add", myAdd, jdkAdd, myBytes, jdkBytes);
        writeRows(writer, "MyArrayList vs ArrayList", sizes, "get(index)", myGet, jdkGet, myBytes, jdkBytes);
        writeRows(writer, "MyArrayList vs ArrayList", sizes, "contains", myContains, jdkContains, myBytes, jdkBytes);
    }

    private static void runHashMapComparison(BufferedWriter writer, int[] sizes) throws IOException {
        double[] myPut = new double[sizes.length];
        double[] jdkPut = new double[sizes.length];
        double[] myGet = new double[sizes.length];
        double[] jdkGet = new double[sizes.length];
        double[] myContainsKey = new double[sizes.length];
        double[] jdkContainsKey = new double[sizes.length];

        long[] myBytes = new long[sizes.length];
        long[] jdkBytes = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];

            myPut[i] = medianNs(() -> timeMyHashMapPut(n));
            jdkPut[i] = medianNs(() -> timeJdkHashMapPut(n));

            myGet[i] = medianNs(() -> timeMyHashMapGet(n));
            jdkGet[i] = medianNs(() -> timeJdkHashMapGet(n));

            myContainsKey[i] = medianNs(() -> timeMyHashMapContainsKey(n));
            jdkContainsKey[i] = medianNs(() -> timeJdkHashMapContainsKey(n));

            myBytes[i] = measureBytesPerElement(() -> buildMyHashMap(n), n);
            jdkBytes[i] = measureBytesPerElement(() -> buildJdkHashMap(n), n);
        }

        writeRows(writer, "MyHashMap vs HashMap", sizes, "put", myPut, jdkPut, myBytes, jdkBytes);
        writeRows(writer, "MyHashMap vs HashMap", sizes, "get", myGet, jdkGet, myBytes, jdkBytes);
        writeRows(writer, "MyHashMap vs HashMap", sizes, "containsKey", myContainsKey, jdkContainsKey, myBytes, jdkBytes);
    }

    private static void writeRows(
            BufferedWriter writer,
            String collection,
            int[] sizes,
            String operation,
            double[] myNs,
            double[] jdkNs,
            long[] myBytes,
            long[] jdkBytes
    ) throws IOException {
        String myBigO = guessBigO(sizes, myNs);
        String jdkBigO = guessBigO(sizes, jdkNs);

        for (int i = 0; i < sizes.length; i++) {
            writer.write(String.format(
                    Locale.US,
                    "%s,%d,%s,%.3f,%.3f,%d,%d,%s,%s",
                    collection,
                    sizes[i],
                    operation,
                    myNs[i],
                    jdkNs[i],
                    myBytes[i],
                    jdkBytes[i],
                    myBigO,
                    jdkBigO
            ));
            writer.newLine();

            System.out.printf(
                    Locale.US,
                    "%s n=%d op=%s my=%.3f ns jdk=%.3f ns myMem=%d jdkMem=%d O=%s/%s%n",
                    collection,
                    sizes[i],
                    operation,
                    myNs[i],
                    jdkNs[i],
                    myBytes[i],
                    jdkBytes[i],
                    myBigO,
                    jdkBigO
            );
        }
    }

    private static double medianNs(TimedOperation op) {
        for (int i = 0; i < WARMUP_ROUNDS; i++) {
            op.run();
        }

        double[] results = new double[TRIALS];

        for (int i = 0; i < TRIALS; i++) {
            results[i] = op.run();
        }

        Arrays.sort(results);
        return results[results.length / 2];
    }

    private static int repetitionsForConstantTime(int n) {
        return 200_000;
    }

    private static int repetitionsForLinearTime(int n) {
        return Math.max(1, Math.min(2_000, 2_000_000 / n));
    }

    private static double timeMyArrayListAdd(int n) {
        int reps = repetitionsForConstantTime(n);
        MyArrayList<Integer> list = new MyArrayList<>(n + reps + 1);

        for (int i = 0; i < n; i++) {
            list.add(BASE + i);
        }

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            list.add(BASE + n + i);
        }

        long elapsed = System.nanoTime() - start;
        sink += list.size();

        return elapsed / (double) reps;
    }

    private static double timeJdkArrayListAdd(int n) {
        int reps = repetitionsForConstantTime(n);
        ArrayList<Integer> list = new ArrayList<>(n + reps + 1);

        for (int i = 0; i < n; i++) {
            list.add(BASE + i);
        }

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            list.add(BASE + n + i);
        }

        long elapsed = System.nanoTime() - start;
        sink += list.size();

        return elapsed / (double) reps;
    }

    private static double timeMyArrayListGet(int n) {
        int reps = repetitionsForConstantTime(n);
        MyArrayList<Integer> list = buildMyArrayList(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            int index = (i * 8191) % n;
            local += list.get(index);
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeJdkArrayListGet(int n) {
        int reps = repetitionsForConstantTime(n);
        ArrayList<Integer> list = buildJdkArrayList(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            int index = (i * 8191) % n;
            local += list.get(index);
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeMyArrayListContains(int n) {
        int reps = repetitionsForLinearTime(n);
        MyArrayList<Integer> list = buildMyArrayList(n);
        Integer target = BASE + n - 1;
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            if (list.contains(target)) {
                local++;
            }
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeJdkArrayListContains(int n) {
        int reps = repetitionsForLinearTime(n);
        ArrayList<Integer> list = buildJdkArrayList(n);
        Integer target = BASE + n - 1;
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            if (list.contains(target)) {
                local++;
            }
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeMyHashMapPut(int n) {
        int reps = repetitionsForConstantTime(n);
        MyHashMap<Integer, Integer> map = new MyHashMap<>(capacityFor(n + reps));

        for (int i = 0; i < n; i++) {
            map.put(BASE + i, BASE + i);
        }

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            map.put(BASE + n + i, BASE + n + i);
        }

        long elapsed = System.nanoTime() - start;
        sink += map.size();

        return elapsed / (double) reps;
    }

    private static double timeJdkHashMapPut(int n) {
        int reps = repetitionsForConstantTime(n);
        HashMap<Integer, Integer> map = new HashMap<>(capacityFor(n + reps));

        for (int i = 0; i < n; i++) {
            map.put(BASE + i, BASE + i);
        }

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            map.put(BASE + n + i, BASE + n + i);
        }

        long elapsed = System.nanoTime() - start;
        sink += map.size();

        return elapsed / (double) reps;
    }

    private static double timeMyHashMapGet(int n) {
        int reps = repetitionsForConstantTime(n);
        MyHashMap<Integer, Integer> map = buildMyHashMap(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            Integer target = BASE + ((i * 8191) % n);
            local += map.get(target);
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeJdkHashMapGet(int n) {
        int reps = repetitionsForConstantTime(n);
        HashMap<Integer, Integer> map = buildJdkHashMap(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            Integer target = BASE + ((i * 8191) % n);
            local += map.get(target);
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeMyHashMapContainsKey(int n) {
        int reps = repetitionsForConstantTime(n);
        MyHashMap<Integer, Integer> map = buildMyHashMap(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            Integer target = BASE + ((i * 8191) % n);
            if (map.containsKey(target)) {
                local++;
            }
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static double timeJdkHashMapContainsKey(int n) {
        int reps = repetitionsForConstantTime(n);
        HashMap<Integer, Integer> map = buildJdkHashMap(n);
        long local = 0;

        long start = System.nanoTime();

        for (int i = 0; i < reps; i++) {
            Integer target = BASE + ((i * 8191) % n);
            if (map.containsKey(target)) {
                local++;
            }
        }

        long elapsed = System.nanoTime() - start;
        sink += local;

        return elapsed / (double) reps;
    }

    private static MyArrayList<Integer> buildMyArrayList(int n) {
        MyArrayList<Integer> list = new MyArrayList<>(n);

        for (int i = 0; i < n; i++) {
            list.add(BASE + i);
        }

        return list;
    }

    private static ArrayList<Integer> buildJdkArrayList(int n) {
        ArrayList<Integer> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            list.add(BASE + i);
        }

        return list;
    }

    private static MyHashMap<Integer, Integer> buildMyHashMap(int n) {
        MyHashMap<Integer, Integer> map = new MyHashMap<>(capacityFor(n));

        for (int i = 0; i < n; i++) {
            map.put(BASE + i, BASE + i);
        }

        return map;
    }

    private static HashMap<Integer, Integer> buildJdkHashMap(int n) {
        HashMap<Integer, Integer> map = new HashMap<>(capacityFor(n));

        for (int i = 0; i < n; i++) {
            map.put(BASE + i, BASE + i);
        }

        return map;
    }

    private static int capacityFor(int expectedSize) {
        int needed = (int) Math.ceil(expectedSize / 0.75) + 1;
        int cap = 1;

        while (cap < needed && cap > 0) {
            cap <<= 1;
        }

        return cap > 0 ? cap : 1 << 30;
    }

    private static long measureBytesPerElement(Supplier<Object> builder, int n) {
        objectSink = null;
        forceGc();

        long before = usedMemory();

        objectSink = builder.get();
        forceGc();

        long after = usedMemory();
        long bytes = Math.max(0L, after - before);
        long perElement = bytes / Math.max(1, n);

        objectSink = null;
        forceGc();

        return perElement;
    }

    private static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void forceGc() {
        for (int i = 0; i < 3; i++) {
            System.gc();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private static String guessBigO(int[] sizes, double[] ns) {
        if (sizes.length < 2) {
            return "unknown";
        }

        int start = Math.max(0, sizes.length - 3);
        double slopeSum = 0.0;
        int count = 0;

        for (int i = start + 1; i < sizes.length; i++) {
            if (ns[i - 1] <= 0 || ns[i] <= 0) {
                continue;
            }

            double slope = Math.log(ns[i] / ns[i - 1]) / Math.log((double) sizes[i] / sizes[i - 1]);

            if (Double.isFinite(slope)) {
                slopeSum += slope;
                count++;
            }
        }

        if (count == 0) {
            return "unknown";
        }

        double slope = slopeSum / count;

        if (slope < 0.55) {
            return "O(1) / cache effects";
        }

        if (slope < 0.90) {
            return "O(log n) / near-flat";
        }

        if (slope < 1.35) {
            return "O(n)";
        }

        return "superlinear";
    }

    @FunctionalInterface
    private interface TimedOperation {
        double run();
    }

    public static final class MyArrayList<E> {
        private Object[] elements;
        private int size;

        public MyArrayList() {
            this(10);
        }

        public MyArrayList(int initialCapacity) {
            if (initialCapacity < 0) {
                throw new IllegalArgumentException("negative capacity");
            }

            elements = new Object[Math.max(1, initialCapacity)];
        }

        public void add(E value) {
            ensureCapacity(size + 1);
            elements[size++] = value;
        }

        @SuppressWarnings("unchecked")
        public E get(int index) {
            checkIndex(index);
            return (E) elements[index];
        }

        public boolean contains(Object value) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(elements[i], value)) {
                    return true;
                }
            }

            return false;
        }

        public int size() {
            return size;
        }

        private void ensureCapacity(int minCapacity) {
            if (minCapacity <= elements.length) {
                return;
            }

            int newCapacity = elements.length + (elements.length >> 1);

            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            elements = Arrays.copyOf(elements, newCapacity);
        }

        private void checkIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(index);
            }
        }
    }

    public static final class MyHashMap<K, V> {
        private static final float LOAD_FACTOR = 0.75f;

        private Node<?, ?>[] table;
        private int size;
        private int threshold;

        public MyHashMap() {
            this(16);
        }

        public MyHashMap(int initialCapacity) {
            int cap = 1;

            while (cap < initialCapacity && cap > 0) {
                cap <<= 1;
            }

            if (cap <= 0) {
                cap = 1 << 30;
            }

            table = new Node<?, ?>[Math.max(1, cap)];
            threshold = Math.max(1, (int) (table.length * LOAD_FACTOR));
        }

        public V put(K key, V value) {
            if (size + 1 > threshold) {
                resize();
            }

            return putIntoTable(table, key, value);
        }

        @SuppressWarnings("unchecked")
        public V get(K key) {
            int index = indexFor(key, table.length);
            Node<K, V> node = (Node<K, V>) table[index];

            while (node != null) {
                if (Objects.equals(node.key, key)) {
                    return node.value;
                }

                node = node.next;
            }

            return null;
        }

        public boolean containsKey(K key) {
            return get(key) != null;
        }

        public int size() {
            return size;
        }

        @SuppressWarnings("unchecked")
        private V putIntoTable(Node<?, ?>[] targetTable, K key, V value) {
            int index = indexFor(key, targetTable.length);
            Node<K, V> node = (Node<K, V>) targetTable[index];

            while (node != null) {
                if (Objects.equals(node.key, key)) {
                    V old = node.value;
                    node.value = value;
                    return old;
                }

                node = node.next;
            }

            Node<K, V> newNode = new Node<>(key, value, (Node<K, V>) targetTable[index]);
            targetTable[index] = newNode;
            size++;

            return null;
        }

        @SuppressWarnings("unchecked")
        private void resize() {
            Node<?, ?>[] oldTable = table;
            Node<?, ?>[] newTable = new Node<?, ?>[oldTable.length << 1];
            int oldSize = size;

            size = 0;

            for (Node<?, ?> bucket : oldTable) {
                Node<K, V> node = (Node<K, V>) bucket;

                while (node != null) {
                    putIntoTable(newTable, node.key, node.value);
                    node = node.next;
                }
            }

            table = newTable;
            threshold = Math.max(1, (int) (table.length * LOAD_FACTOR));
            size = oldSize;
        }

        private int indexFor(Object key, int length) {
            int h = key == null ? 0 : key.hashCode();
            h ^= (h >>> 16);
            return h & (length - 1);
        }

        private static final class Node<K, V> {
            final K key;
            V value;
            Node<K, V> next;

            Node(K key, V value, Node<K, V> next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }
    }
}
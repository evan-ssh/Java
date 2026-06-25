import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryBenchmark {
    private static final int[] SIZES = {1_000_000};
    private static volatile Object keepAlive;

    public static void main(String[] args) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("memoryB.csv"))) {
            writer.println("Structure,Elements held,Heap used (MB),Bytes per element");
            measureCollection(writer, "ArrayList", MemoryBenchmark::measureArrayList);
            measureCollection(writer, "LinkedList", MemoryBenchmark::measureLinkedList);
            measureCollection(writer, "HashSet", MemoryBenchmark::measureHashSet);
            measureCollection(writer, "TreeSet", MemoryBenchmark::measureTreeSet);
            measureCollection(writer, "HashMap", MemoryBenchmark::measureHashMap);
            measureCollection(writer, "TreeMap", MemoryBenchmark::measureTreeMap);
            measureCollection(writer, "PriorityQueue", MemoryBenchmark::measurePriorityQueue);
        }
    }

    private static void measureCollection(PrintWriter writer, String collectionName, Measurement measurement) {
        for (int n : SIZES) {
            long heapUsedBytes = measurement.measure(n);
            double heapUsedMb = heapUsedBytes / 1_048_576.0;
            long bytesPerElement = heapUsedBytes / (long) n;
    
            writer.printf("%s,%d,%.2f,%d%n", collectionName, n, heapUsedMb, bytesPerElement);
            writer.flush();

            keepAlive = null;
            runGc();
        }
    }

    private static long measureArrayList(int n) {
        runGc();
        long before = usedMemory();
        ArrayList<Integer> list = new ArrayList<>(n);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt());
        }
        keepAlive = list;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measureLinkedList(int n) {
        runGc();
        long before = usedMemory();
        LinkedList<Integer> list = new LinkedList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt());
        }
        keepAlive = list;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measureHashSet(int n) {
        runGc();
        long before = usedMemory();
        HashSet<Integer> set = new HashSet<>(n * 2);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (set.size() < n) {
            set.add(random.nextInt());
        }
        keepAlive = set;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measureTreeSet(int n) {
        runGc();
        long before = usedMemory();
        TreeSet<Integer> set = new TreeSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (set.size() < n) {
            set.add(random.nextInt());
        }
        keepAlive = set;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measureHashMap(int n) {
        runGc();
        long before = usedMemory();
        HashMap<Integer, Integer> map = new HashMap<>(n * 2);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (map.size() < n) {
            int key = random.nextInt();
            map.put(key, random.nextInt());
        }
        keepAlive = map;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measureTreeMap(int n) {
        runGc();
        long before = usedMemory();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (map.size() < n) {
            int key = random.nextInt();
            map.put(key, random.nextInt());
        }
        keepAlive = map;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static long measurePriorityQueue(int n) {
        runGc();
        long before = usedMemory();
        PriorityQueue<Integer> queue = new PriorityQueue<>(n);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            queue.add(random.nextInt());
        }
        keepAlive = queue;
        long after = usedMemory();
        return Math.max(0L, after - before);
    }

    private static void runGc() {
        System.gc();
        System.gc();
    }

    private static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    @FunctionalInterface
    private interface Measurement {
        long measure(int n);
    }
}

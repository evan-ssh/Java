import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class TimingBenchmark {
    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000, 10_000_000};
    private static final int TRIALS = 5;

    public static void main(String[] args) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("timeA.csv"))) {
            writer.println("Structure,n,Operation,ns/op,Growth ratio,Program's Big-O guess");
            Map<String, Double> previousAvg = new HashMap<>();

            timeCollection(writer, previousAvg, "ArrayList");
            timeCollection(writer, previousAvg, "LinkedList");
            timeCollection(writer, previousAvg, "HashSet");
            timeCollection(writer, previousAvg, "TreeSet");
            timeCollection(writer, previousAvg, "HashMap");
            timeCollection(writer, previousAvg, "TreeMap");
            timeCollection(writer, previousAvg, "PriorityQueue");
        }
    }

    private static void timeCollection(PrintWriter writer, Map<String, Double> previousAvg, String collectionName) {
        for (int n : SIZES) {
            double addSum = 0.0;
            double containsSum = 0.0;
            double removeSum = 0.0;

            for (int trial = 0; trial < TRIALS; trial++) {
                long[] timings = switch (collectionName) {
                    case "ArrayList" -> timeArrayList(n);
                    case "LinkedList" -> timeLinkedList(n);
                    case "HashSet" -> timeHashSet(n);
                    case "TreeSet" -> timeTreeSet(n);
                    case "HashMap" -> timeHashMap(n);
                    case "TreeMap" -> timeTreeMap(n);
                    case "PriorityQueue" -> timePriorityQueue(n);
                    default -> throw new IllegalArgumentException("Unknown collection: " + collectionName);
                };
                addSum += timings[0];
                containsSum += timings[1];
                removeSum += timings[2];
            }

            double addAvg = addSum / TRIALS;
            double containsAvg = containsSum / TRIALS;
            double removeAvg = removeSum / TRIALS;

            String addOperation;
            String middleOperation;
            String removeOperation;

            switch (collectionName) {
                case "HashMap", "TreeMap" -> {
                    addOperation = "put";
                    middleOperation = "get";
                    removeOperation = "containsKey";
                }
                case "PriorityQueue" -> {
                    addOperation = "offer";
                    middleOperation = "poll";
                    removeOperation = "peek";
                }
                default -> {
                    addOperation = "add";
                    middleOperation = "contains";
                    removeOperation = "remove";
                }
            }

           
            if (collectionName.equals("ArrayList")) {
                writeResult(writer, previousAvg, collectionName, n, "add(at end)", addAvg);
                writeResult(writer, previousAvg, collectionName, n, "contains", containsAvg);
            
                double getSum = 0.0;
                double addFrontSum = 0.0;
            
                for (int trial = 0; trial < TRIALS; trial++) {
                    long[] extraTimings = timeArrayListExtras(n);
                    getSum += extraTimings[0];
                    addFrontSum += extraTimings[1];
                }
            
                writeResult(writer, previousAvg, collectionName, n, "get(i)", getSum / TRIALS);
                writeResult(writer, previousAvg, collectionName, n, "add(at front)", addFrontSum / TRIALS);
            
            } else if (collectionName.equals("LinkedList")) {
                writeResult(writer, previousAvg, collectionName, n, "add(at end)", addAvg);
                writeResult(writer, previousAvg, collectionName, n, "contains", containsAvg);
            
                double getSum = 0.0;
                double addFrontSum = 0.0;
            
                for (int trial = 0; trial < TRIALS; trial++) {
                    long[] extraTimings = timeLinkedListExtras(n);
                    getSum += extraTimings[0];
                    addFrontSum += extraTimings[1];
                }
            
                writeResult(writer, previousAvg, collectionName, n, "get(i)", getSum / TRIALS);
                writeResult(writer, previousAvg, collectionName, n, "add(at front)", addFrontSum / TRIALS);
            
            } else {
                writeResult(writer, previousAvg, collectionName, n, addOperation, addAvg);
                writeResult(writer, previousAvg, collectionName, n, middleOperation, containsAvg);
                writeResult(writer, previousAvg, collectionName, n, removeOperation, removeAvg);
            }
        
        }
                    
    }

    private static void writeResult(PrintWriter writer, Map<String, Double> previousAvg, String collection, int n, String operation, double avgNs) {
        String key = collection + ":" + operation;
    
        double growthRatio = 1.0;
        if (previousAvg.containsKey(key)) {
            growthRatio = avgNs / previousAvg.get(key);
        }
    
        previousAvg.put(key, avgNs);
    
        String bigOGuess = guessBigO(n, growthRatio);
    
        writer.printf(
                "%s,%d,%s,%.2f,%.4f,%s%n",
                collection,
                n,
                operation,
                avgNs,
                growthRatio,
                bigOGuess
        );
    }

    private static String guessBigO(int n, double growthRatio) {
        if (n == SIZES[0]) {
            return "baseline";
        }
    
        if (growthRatio < 2.0) {
            return "O(1) / near-flat";
        }
    
        if (growthRatio < 6.0) {
            return "O(log n) or cache effects";
        }
    
        if (growthRatio < 15.0) {
            return "O(n)";
        }
    
        return "O(n) / noisy";
    }

    private static long[] timeArrayList(int n) {
        ArrayList<Integer> list = new ArrayList<>(n + 1);
        int sample = populateAndSampleList(list, n);
        long containsTime = measureNano(() -> list.contains(sample));
        int newValue = createUniqueValueForList(list);
        long addTime = measureNano(() -> list.add(newValue));
        long removeTime = measureNano(() -> list.remove((Integer) sample));
        return new long[]{addTime, containsTime, removeTime};
    }

    private static long[] timeLinkedList(int n) {
        LinkedList<Integer> list = new LinkedList<>();
        int sample = populateAndSampleList(list, n);
        long containsTime = measureNano(() -> list.contains(sample));
        int newValue = createUniqueValueForList(list);
        long addTime = measureNano(() -> list.add(newValue));
        long removeTime = measureNano(() -> list.remove((Integer) sample));
        return new long[]{addTime, containsTime, removeTime};
    }
    private static long[] timeArrayListExtras(int n) {
        ArrayList<Integer> list = new ArrayList<>(n + 1);
        populateAndSampleList(list, n);
    
        int middleIndex = n / 2;
    
        long getTime = measureNano(() -> list.get(middleIndex));
        long addFrontTime = measureNano(() -> list.add(0, ThreadLocalRandom.current().nextInt()));
    
        return new long[]{getTime, addFrontTime};
    }
    
    private static long[] timeLinkedListExtras(int n) {
        LinkedList<Integer> list = new LinkedList<>();
        populateAndSampleList(list, n);
    
        int middleIndex = n / 2;
    
        long getTime = measureNano(() -> list.get(middleIndex));
        long addFrontTime = measureNano(() -> list.addFirst(ThreadLocalRandom.current().nextInt()));
    
        return new long[]{getTime, addFrontTime};
    }

    private static long[] timeHashSet(int n) {
        HashSet<Integer> set = new HashSet<>(n * 2);
        int sample = populateAndSampleCollection(set, n);
        long containsTime = measureNano(() -> set.contains(sample));
        int newValue = createUniqueValueForSet(set);
        long addTime = measureNano(() -> set.add(newValue));
        long removeTime = measureNano(() -> set.remove(sample));
        return new long[]{addTime, containsTime, removeTime};
    }

    private static long[] timeTreeSet(int n) {
        TreeSet<Integer> set = new TreeSet<>();
        int sample = populateAndSampleCollection(set, n);
        long containsTime = measureNano(() -> set.contains(sample));
        int newValue = createUniqueValueForSet(set);
        long addTime = measureNano(() -> set.add(newValue));
        long removeTime = measureNano(() -> set.remove(sample));
        return new long[]{addTime, containsTime, removeTime};
    }

    private static long[] timeHashMap(int n) {
        HashMap<Integer, Integer> map = new HashMap<>(n * 2);
        Entry sample = populateAndSampleMap(map, n);
        long containsTime = measureNano(() -> map.get(sample.key));
        int newKey = createUniqueValueForMap(map);
        long addTime = measureNano(() -> map.put(newKey, sample.value + 1));
        long containsKeyTime = measureNano(() -> map.containsKey(sample.key));
        return new long[]{addTime, containsTime, containsKeyTime};  
    }

    private static long[] timeTreeMap(int n) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Entry sample = populateAndSampleMap(map, n);
        long containsTime = measureNano(() -> map.get(sample.key));
        int newKey = createUniqueValueForMap(map);
        long addTime = measureNano(() -> map.put(newKey, sample.value + 1));
        long containsKeyTime = measureNano(() -> map.containsKey(sample.key));
        return new long[]{addTime, containsTime, containsKeyTime};
    }

    private static long[] timePriorityQueue(int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(n + 1);
        populateAndSampleCollection(queue, n);
    
        int newValue = createUniqueValueForQueue(queue);
        long offerTime = measureNano(() -> queue.offer(newValue));
    
        PriorityQueue<Integer> pollQueue = new PriorityQueue<>(queue);
        long pollTime = measureNano(() -> pollQueue.poll());
    
        long peekTime = measureNano(() -> queue.peek());
    
        return new long[]{offerTime, pollTime, peekTime};
    }

    private static int populateAndSampleList(java.util.List<Integer> list, int n) {
        int sample = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i <= n; i++) {
            int value = random.nextInt();
            list.add(value);
            if (random.nextInt(i) == 0) {
                sample = value;
            }
        }
        return sample;
    }

    private static int populateAndSampleCollection(java.util.Collection<Integer> collection, int n) {
        int sample = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i <= n; i++) {
            int value = random.nextInt();
            collection.add(value);
            if (random.nextInt(i) == 0) {
                sample = value;
            }
        }
        return sample;
    }

    private static Entry populateAndSampleMap(Map<Integer, Integer> map, int n) {
        int sampleKey = 0;
        int sampleValue = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i <= n; i++) {
            int key = random.nextInt();
            int value = random.nextInt();
            map.put(key, value);
            if (random.nextInt(i) == 0) {
                sampleKey = key;
                sampleValue = value;
            }
        }
        return new Entry(sampleKey, sampleValue);
    }

    private static int createUniqueValueForList(java.util.Collection<Integer> collection) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int candidate;
        do {
            candidate = random.nextInt();
        } while (collection instanceof java.util.Set && collection.contains(candidate));
        return candidate;
    }

    private static int createUniqueValueForSet(java.util.Set<Integer> set) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int candidate;
        do {
            candidate = random.nextInt();
        } while (set.contains(candidate));
        return candidate;
    }

    private static int createUniqueValueForMap(Map<Integer, Integer> map) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int candidate;
        do {
            candidate = random.nextInt();
        } while (map.containsKey(candidate));
        return candidate;
    }

    private static int createUniqueValueForQueue(java.util.Queue<Integer> queue) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int candidate;
        do {
            candidate = random.nextInt();
        } while (queue.contains(candidate));
        return candidate;
    }

    private static long measureNano(Runnable action) {
        long start = System.nanoTime();
        action.run();
        return System.nanoTime() - start;
    }

    private static final class Entry {
        final int key;
        final int value;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

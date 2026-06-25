# Copilot Prompts Log — CP2561 Lab 2

## AI Use Statement
Our group used AI tools only to support our learning, improve our understanding of Java collections, and assist with troubleshooting errors or formatting issues. AI was not used as a replacement for our own work. All code, results, explanations, and final decisions were reviewed and understood by our group before submission.


## Part A — TimingBenchmark.java
Write a Java program called `TimingBenchmark.java` that measures how long common operations take on 7 Java collections as n grows. For each of these collections: `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `HashMap`, `TreeMap`, and `PriorityQueue`, and for each n in {1000, 10000, 100000, 1000000, 10000000}: populate the collection with n random integers, then time these operations using `System.nanoTime()`: add one element, contains/get one element, and remove one element. Repeat each timing 5 times and take the average to reduce noise. Compute the growth ratio by dividing the current ns/op by the previous n's ns/op. Write all results to `timeA.csv` with columns: `collection,n,operation,avgNs,growthRatio`. Use `ThreadLocalRandom` for random numbers. Never store all n values at once for timing — time only single operations on an already-populated collection.

## Part A Follow-up / refinement prompt:

Review TimingBenchmark.java against the lab instructions and give clear step-by-step guidance on how to update the benchmark so it matches the required operations more exactly. Explain the reasoning behind each change so the implementation is understandable while it is being fixed, not just copied in.

Update the list benchmarks so ArrayList and LinkedList include get(i), add(at end), add(at front), and contains. Update the set benchmarks so HashSet and TreeSet include add, contains, and remove. Update the map benchmarks so HashMap and TreeMap include put, get, and containsKey. Update the PriorityQueue benchmark so it includes offer, poll, and peek.

Update timeA.csv so it includes the structure name, n value, operation name, nanoseconds per operation, growth ratio, and the program’s Big-O guess. Explain how the growth ratio is being used to make a best guess. Also explain why some measured Big-O guesses may look noisy because of JVM warm-up, CPU cache, garbage collection, resizing, constant factors, and fast hardware.

The goal is to understand why each operation behaves the way it does while applying the fixes, including why ArrayList.get(i) is O(1), why LinkedList.get(i) is O(n), why tree-based operations are O(log n), why PriorityQueue.offer and poll are O(log n), and why PriorityQueue.peek is O(1).

## Part B — MemoryBenchmark.java
Write a Java program called `MemoryBenchmark.java` that measures the memory footprint in bytes per element for 7 Java collections: `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `HashMap`, `TreeMap`, and `PriorityQueue`. For each collection and for each n in {1000, 10000, 100000, 1000000}: call `System.gc()` twice before measuring, record `Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()` before and after populating the collection with n random integers using `ThreadLocalRandom`, compute bytes used as the difference, and divide by n to get bytes per element. Write results to `memoryB.csv` with columns: `collection,n,bytesPerElement`. Do not store multiple collections in memory at the same time — measure one, let it go out of scope, then move to the next.

## Part B - Follow-up / refinement prompt:

Review MemoryBenchmark.java against the report table and give clear step-by-step instructions for adjusting the output format and memory calculation. Explain the reasoning behind each change so the memory benchmark is understandable while it is being updated.

Update memoryB.csv so it matches the required report columns: Structure,Elements held,Heap used (MB),Bytes per element. Use one fixed large element count per structure so the report has one clear row for each collection. Measure one collection at a time, force garbage collection before measuring, build the collection, record total heap used in bytes, convert heap used to MB, calculate bytes per element, then clean up before measuring the next collection.

Explain why this memory test is different from only saying a structure is O(n) space. Include reasoning about why two collections can both be O(n) but still have very different bytes per element. Explain how backing arrays, linked nodes, tree nodes, hash buckets, object references, key/value entries, load factor, and unused capacity affect real memory usage.

## Part C — LobsterStream.java

Review LobsterStream.java and give clear step-by-step guidance on how to complete the LOBSTER-like stream simulation. Explain the reasoning behind the order book design while applying the changes so the solution is understandable, not just copied in.

Complete the execute() method so a marketable order consumes the opposite side of the order book using price-time priority. The method should choose the opposite side of the book, get the best price level from the TreeMap, consume orders from the front of the ArrayDeque, reduce the order size for partial fills, remove fully filled orders from the queue, remove fully filled orders from the byId lookup map, and remove empty price levels from the TreeMap.

Add measurements as the book grows so the program records submit timing, cancel timing, best-bid lookup timing, resting order count, live data in GB, events processed, events per second, bytes per resting order, garbage collection count, and garbage collection time.

Write the results to scaleC.csv with columns: Resting orders,Live data (GB),Events processed,Events / sec,Bytes per order,GC / slowdown notes,submit ns,cancel ns,best bid ns,total GC count,total GC time ms.

Add a background resource monitor thread that prints live machine information once per second while the stream runs. The monitor should print CPU load, used heap, free RAM, total RAM, live thread count, garbage collection count, and garbage collection time.

Explain how the in-memory stream works without saving a giant file to disk. Also explain why only the order book state keeps growing while each generated event is discarded after it is applied. Include guidance for testing with a smaller heap and target on a machine that does not have 50 GB of RAM, such as running java -Xms1g -Xmx1g LobsterStream 0.25.

## Part D — PartDCompare.java


Give clear step-by-step guidance on building custom collection classes and comparing them against JDK 21 collections. Explain the reasoning behind each data structure choice so the custom collections can be understood while they are being implemented.

Create a custom MyArrayList that imitates java.util.ArrayList. It should use a backing array, grow when needed, and support add, get(index), contains, and size. Explain why add at the end is usually constant time because of array growth, why get(index) is constant time because of direct index access, and why contains is linear because it scans through elements.

Create a custom MyHashMap that imitates java.util.HashMap. It should use hashing, a bucket array, separate chaining, a load factor, resizing, and support put, get, containsKey, and size. Explain how hashing chooses a bucket, how separate chaining handles collisions, and why put, get, and containsKey are average-case O(1) when the buckets are spread out well.

Write a comparison program called PartDCompare.java that benchmarks the custom collections beside the JDK versions. Compare MyArrayList against ArrayList, and compare MyHashMap against HashMap.

Measure nanoseconds per operation, bytes per element, the custom collection’s Big-O guess, and the JDK collection’s Big-O guess. Write the results to compareD.csv with columns: collection,n,operation,yourNsOp,jdkNsOp,yourBytesElem,jdkBytesElem,yourBigO,jdkBigO.

Explain where the custom collections match the JDK collections and where they fall behind. Include reasoning about backing array growth for MyArrayList, hashing and resizing for MyHashMap, and why the JDK collections are usually more optimized, more reliable, and better tested for production use.




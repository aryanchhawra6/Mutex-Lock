import java.util.ArrayList;
import java.util.List;

public class SynchronizedCounter {
    
    private long sharedCounter = 0;
    private final int NUM_THREADS = 10;
    private final int ITERATIONS_PER_THREAD = 100000;

    // The 'synchronized' keyword enforces mutual exclusion.
    public synchronized void incrementCounter() {
        // --- CRITICAL SECTION ---
        sharedCounter++;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter project = new SynchronizedCounter();
        System.out.println("Starting " + project.NUM_THREADS + " threads to increment the counter using 'synchronized'...");

        List<Thread> threads = new ArrayList<>();

        // 1. Create and start threads
        for (int i = 0; i < project.NUM_THREADS; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < project.ITERATIONS_PER_THREAD; j++) {
                    project.incrementCounter();
                }
            });
            threads.add(t);
            t.start();
        }

        // 2. Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        // 3. Check the final result
        long expectedValue = (long) project.NUM_THREADS * project.ITERATIONS_PER_THREAD;

        System.out.println("\nExpected Final Value: " + expectedValue);
        System.out.println("Actual Final Value:   " + project.sharedCounter);

        if (project.sharedCounter == expectedValue) {
            System.out.println("✅ SUCCESS: The intrinsic lock prevented data races.");
        } else {
            System.out.println("❌ FAILURE: Error in concurrency logic. (This should not happen)");
        }
    }
}
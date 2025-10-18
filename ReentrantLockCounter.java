import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter {

    // 1. The explicit Lock object (the Mutex)
    private final ReentrantLock counterLock = new ReentrantLock();
    
    private long sharedCounter = 0;
    private final int NUM_THREADS = 10;
    private final int ITERATIONS_PER_THREAD = 100000;

    public void incrementCounter() {
        // 2. Acquire the lock (blocking until it's available)
        counterLock.lock();
        
        try {
            // --- CRITICAL SECTION ---
            sharedCounter++;
        } finally {
            // 3. Release the lock in a 'finally' block to guarantee cleanup (like C++ RAII)
            counterLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCounter project = new ReentrantLockCounter();
        
        System.out.println("Starting " + project.NUM_THREADS + " threads to increment the counter using 'ReentrantLock'...");
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < project.NUM_THREADS; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < project.ITERATIONS_PER_THREAD; j++) {
                    project.incrementCounter();
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long expectedValue = (long) project.NUM_THREADS * project.ITERATIONS_PER_THREAD;

        System.out.println("\nExpected Final Value: " + expectedValue);
        System.out.println("Actual Final Value:   " + project.sharedCounter);

        if (project.sharedCounter == expectedValue) {
            System.out.println("✅ SUCCESS: The ReentrantLock prevented data races.");
        } else {
            System.out.println("❌ FAILURE: Error in concurrency logic. (This should not happen)");
        }
    }
}
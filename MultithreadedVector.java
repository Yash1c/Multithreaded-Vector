import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class MultithreadedVector {
    private static final int SIZE = 200_000_000; // Change here to increase the size of the vector
    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();
    
    public static void main(String[] args) { // Main method to run the program
        double[] vector = new double[SIZE];
        
        startvector(vector);
        System.out.println("finished initialization");
        
        int total = countvalues(vector);
        System.out.println("Values between 0.25 and 0.75: " + total);
    }

    private static void startvector(double[] vector) {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        
        int part = SIZE / N_THREADS;
        for (int t = 0; t < N_THREADS; t++) { // Loop to create N_THREADS tasks
            final int begin = t * part;
            final int end = (t == N_THREADS - 1) ? SIZE : begin + part;
            
            executor.execute(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current(); // Moved inside the task to avoid contention
                for (int i = begin; i < end; i++) {
                    vector[i] = random.nextDouble();
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES); // The time more realistic for the task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int countvalues(double[] vector) {
        AtomicInteger total = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        
        int part = SIZE / N_THREADS;
        for (int t = 0; t < N_THREADS; t++) {
            final int begin = t * part;
            final int end = (t == N_THREADS - 1) ? SIZE : begin + part;
            
            executor.execute(() -> {
                int local = 0;
                for (int i = begin; i < end; i++) {
                    if (vector[i] > 0.25 && vector[i] < 0.75) { 
                        local++;
                    }
                }
                total.addAndGet(local);
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return total.get();
    }
}
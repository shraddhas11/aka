import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class prodcons {

    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final int capacity = 5; // Maximum size of the buffer

    // Semaphores
    private final Semaphore empty = new Semaphore(capacity); // Tracks empty slots
    private final Semaphore full = new Semaphore(0);         // Tracks filled slots

    // Mutex for critical section
    private final Semaphore mutex = new Semaphore(1);

    // Number of items to produce/consume
    private final int itemsToProduce = 10;
    private final int itemsToConsume = 10;

    // Producer thread
    class Producer extends Thread {

        public void run() {
            int value = 0;
            try {
                for (int i = 0; i < itemsToProduce; i++) {
                    empty.acquire(); // Wait for an empty slot
                    mutex.acquire(); // Enter critical section

                    // Add item to buffer
                    buffer.add(value);
                    System.out.println("Producer produced: " + value);
                    value++;

                    mutex.release(); // Exit critical section
                    full.release();  // Signal that a slot is filled

                    Thread.sleep(800); // Simulate time to produce
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
    }

    // Consumer thread
    class Consumer extends Thread {

        public void run() {
            try {
                for (int i = 0; i < itemsToConsume; i++) {
                    full.acquire();  // Wait for a filled slot
                    mutex.acquire(); // Enter critical section

                    // Remove item from buffer
                    int value = buffer.removeFirst();
                    System.out.println("Consumer consumed: " + value);

                    mutex.release(); // Exit critical section
                    empty.release(); // Signal that a slot is empty

                    Thread.sleep(800); // Simulate time to consume
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }

    public void start() {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        producer.start();
        consumer.start();

        // Wait for threads to complete
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("Production and consumption completed.");
    }

    public static void main(String[] args) {
        prodcons pc = new prodcons();
        pc.start();
    }
}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class SharedResource {
    private int readerCount = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isWriting = false;

    // Reader accessing the shared resource
    public void startReading(String readerName) throws InterruptedException {
        lock.lock();
        try {
            while (isWriting) {
                condition.await(); // Wait if a writer is writing
            }
            readerCount++;
            System.out.println(readerName + " started reading. Active readers: " + readerCount);
        } finally {
            lock.unlock();
        }
    }

    public void stopReading(String readerName) {
        lock.lock();
        try {
            readerCount--;
            System.out.println(readerName + " stopped reading. Active readers: " + readerCount);
            if (readerCount == 0) {
                condition.signalAll(); // Notify writers waiting
            }
        } finally {
            lock.unlock();
        }
    }

    // Writer accessing the shared resource
    public void startWriting(String writerName) throws InterruptedException {
        lock.lock();
        try {
            while (isWriting || readerCount > 0) {
                condition.await(); // Wait if any readers or writers are active
            }
            isWriting = true;
            System.out.println(writerName + " started writing.");
        } finally {
            lock.unlock();
        }
    }

    public void stopWriting(String writerName) {
        lock.lock();
        try {
            isWriting = false;
            System.out.println(writerName + " stopped writing.");
            condition.signalAll(); // Notify readers and writers waiting
        } finally {
            lock.unlock();
        }
    }
}

// Reader thread
class Reader extends Thread {
    private final SharedResource sharedResource;
    private final String readerName;

    Reader(SharedResource sharedResource, String readerName) {
        this.sharedResource = sharedResource;
        this.readerName = readerName;
    }


    public void run() {
        try {
            sharedResource.startReading(readerName);
            Thread.sleep(1000); // Simulate reading
            sharedResource.stopReading(readerName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Writer thread
class Writer extends Thread {
    private final SharedResource sharedResource;
    private final String writerName;

    Writer(SharedResource sharedResource, String writerName) {
        this.sharedResource = sharedResource;
        this.writerName = writerName;
    }

    @Override
    public void run() {
        try {
            sharedResource.startWriting(writerName);
            Thread.sleep(2000); // Simulate writing
            sharedResource.stopWriting(writerName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ReaderWriterProblem {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Create and start readers
        Thread reader1 = new Reader(sharedResource, "Reader1");
        Thread reader2 = new Reader(sharedResource, "Reader2");

        // Create and start writers
        Thread writer1 = new Writer(sharedResource, "Writer1");
        Thread writer2 = new Writer(sharedResource, "Writer2");

        // Start the threads
        reader1.start();
        reader2.start();
        writer1.start();
        writer2.start();
    }
}


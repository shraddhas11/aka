import java.util.Arrays;

public class ProcessControlDemo {
    public static void main(String[] args) throws InterruptedException {
        int[] numbers = {5, 2, 9, 1, 6, 3};
        System.out.println("Original Array: " + Arrays.toString(numbers));

        // Simulate FORK: Create a child process using a thread
        Thread childProcess = new Thread(() -> {
            // Simulating child process sorting
            System.out.println("Child Process: Sorting the array...");
            int[] childSorted = Arrays.copyOf(numbers, numbers.length);
            bubbleSort(childSorted); // Child process sorts using Bubble Sort
            System.out.println("Child Process Sorted Array: " + Arrays.toString(childSorted));
            System.out.println("Child Process Complete. It is now in ZOMBIE state (simulated).");
        });

        // Start the child process
        childProcess.start();

        // Parent process sorts using a different algorithm
        System.out.println("Parent Process: Waiting for the child process to complete...");
        childProcess.join(); // Simulates the WAIT system call
        System.out.println("Parent Process: Child process finished. Continuing execution...");

        System.out.println("Parent Process: Sorting the array...");
        int[] parentSorted = Arrays.copyOf(numbers, numbers.length);
        selectionSort(parentSorted); // Parent process sorts using Selection Sort
        System.out.println("Parent Process Sorted Array: " + Arrays.toString(parentSorted));

        // Simulate orphan process
        System.out.println("Simulating Orphan Process: Detaching child process...");
        Thread orphanProcess = new Thread(() -> {
            System.out.println("Orphan Process: Running independently as the parent process has terminated.");
        });
        orphanProcess.start();
        Thread.sleep(2000); // Allow orphan process to run after parent exits
        System.out.println("Parent Process Exiting...");
    }

    // Bubble Sort for child process
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Selection Sort for parent process
    private static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // Swap arr[i] and arr[minIdx]
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}


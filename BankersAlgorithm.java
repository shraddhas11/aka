import java.util.Scanner;

public class BankersAlgorithm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();
        System.out.print("Enter number of resources: ");
        int numResources = scanner.nextInt();

        // Input Allocation matrix
        int[][] allocation = new int[numProcesses][numResources];
        System.out.println("\nEnter Allocation Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        // Input Maximum matrix
        int[][] max = new int[numProcesses][numResources];
        System.out.println("\nEnter Maximum Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Input Available resources
        int[] available = new int[numResources];
        System.out.println("\nEnter Available Resources:");
        for (int i = 0; i < numResources; i++) {
            available[i] = scanner.nextInt();
        }

        // Calculate Need matrix
        int[][] need = new int[numProcesses][numResources];
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // Display Need matrix
        System.out.println("\nNeed Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }

        // Check if system is in a safe state
        if (isSafeState(numProcesses, numResources, allocation, need, available)) {
            System.out.println("\nThe system is in a SAFE state.");
        } else {
            System.out.println("\nThe system is NOT in a SAFE state.");
        }
    }

    // Function to check if the system is in a safe state
    public static boolean isSafeState(int numProcesses, int numResources, int[][] allocation, int[][] need, int[] available) {
        boolean[] finished = new boolean[numProcesses];
        int[] safeSequence = new int[numProcesses];
        int[] work = available.clone();
        int count = 0;

        while (count < numProcesses) {
            boolean found = false;

            for (int i = 0; i < numProcesses; i++) {
                if (!finished[i]) {
                    boolean canAllocate = true;

                    for (int j = 0; j < numResources; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int j = 0; j < numResources; j++) {
                            work[j] += allocation[i][j];
                        }
                        safeSequence[count++] = i;
                        finished[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                return false; // System is not in a safe state
            }
        }

        // Print safe sequence
        System.out.print("\nSafe Sequence: ");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + safeSequence[i] + (i < numProcesses - 1 ? " -> " : ""));
        }
        return true;
    }
}

import java.util.*;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival Time
    int burstTime; // Burst Time
    int priority; // Priority (for Priority Scheduling)
    int finishTime; // Finish Time
    int turnaroundTime; // Turnaround Time
    int waitingTime; // Waiting Time
    int remainingTime; // Remaining Burst Time (for preemptive algorithms)

    Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}

public class SchedulingAlgorithms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time, Burst Time, and Priority (if applicable) for Process " + (i + 1) + ": ");
            int at = scanner.nextInt();
            int bt = scanner.nextInt();
            int pr = scanner.nextInt(); // Set to 0 if Priority is not applicable
            processes.add(new Process(i + 1, at, bt, pr));
        }

        System.out.println("\nChoose a scheduling algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (Non-Preemptive)");
        System.out.println("3. Shortest Job First (Preemptive)");
        System.out.println("4. Priority Scheduling (Non-Preemptive)");
        System.out.println("5. Priority Scheduling (Preemptive)");
        System.out.println("6. Round Robin");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                fcfs(processes);
                break;
            case 2:
                sjfNonPreemptive(processes);
                break;
            case 3:
                sjfPreemptive(processes);
                break;
            case 4:
                priorityNonPreemptive(processes);
                break;
            case 5:
                priorityPreemptive(processes);
                break;
            case 6:
                System.out.print("Enter the time quantum for Round Robin: ");
                int quantum = scanner.nextInt();
                roundRobin(processes, quantum);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // First Come First Serve
    public static void fcfs(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.finishTime = currentTime + p.burstTime;
            currentTime = p.finishTime;

            p.turnaroundTime = p.finishTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
        }
        printResults(processes, "First Come First Serve");
    }

    // Shortest Job First (Non-Preemptive)
    public static void sjfNonPreemptive(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));

        List<Process> scheduled = new ArrayList<>();
        while (scheduled.size() < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !scheduled.contains(p)) {
                    readyQueue.add(p);
                }
            }
            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();
                current.finishTime = currentTime + current.burstTime;
                currentTime = current.finishTime;

                current.turnaroundTime = current.finishTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                scheduled.add(current);
            } else {
                currentTime++;
            }
        }
        printResults(scheduled, "Shortest Job First (Non-Preemptive)");
    }

    // Shortest Job First (Preemptive)
    public static void sjfPreemptive(List<Process> processes) {
        // Implementation for Preemptive SJF
        System.out.println("To be implemented...");
    }

    // Priority Scheduling (Non-Preemptive)
    public static void priorityNonPreemptive(List<Process> processes) {
        // Implementation for Non-Preemptive Priority Scheduling
        System.out.println("To be implemented...");
    }

    // Priority Scheduling (Preemptive)
    public static void priorityPreemptive(List<Process> processes) {
        // Implementation for Preemptive Priority Scheduling
        System.out.println("To be implemented...");
    }

    // Round Robin
    public static void roundRobin(List<Process> processes, int quantum) {
        // Implementation for Round Robin Scheduling
        System.out.println("To be implemented...");
    }

    // Utility function to print results
    public static void printResults(List<Process> processes, String algorithm) {
        System.out.println("\nResults for " + algorithm + ":");
        System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s\n", "Process", "Arrival Time", "Burst Time", "Finish Time", "Turnaround", "Waiting Time");

        for (Process p : processes) {
            System.out.printf("%-10d%-15d%-15d%-15d%-15d%-15d\n", p.pid, p.arrivalTime, p.burstTime, p.finishTime, p.turnaroundTime, p.waitingTime);
        }
    }
}

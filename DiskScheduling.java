import java.util.*;

public class DiskScheduling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Disk requests and initial head position
        System.out.print("Enter the number of disk requests: ");
        int numRequests = scanner.nextInt();
        int[] requests = new int[numRequests];
        System.out.print("Enter the disk requests (space-separated): ");
        for (int i = 0; i < numRequests; i++) {
            requests[i] = scanner.nextInt();
        }
        System.out.print("Enter the initial head position: ");
        int head = scanner.nextInt();

        System.out.println("\nDisk Scheduling Algorithms Results:");
        System.out.println("SSTF Total Seek Time: " + sstf(requests, head));
        System.out.println("SCAN Total Seek Time: " + scan(requests, head));
        System.out.println("C-LOOK Total Seek Time: " + cLook(requests, head));
    }

    // SSTF Algorithm
    public static int sstf(int[] requests, int head) {
        int totalSeekTime = 0;
        List<Integer> pending = new ArrayList<>();
        for (int req : requests) {
            pending.add(req);
        }

        while (!pending.isEmpty()) {
            int closest = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int req : pending) {
                int distance = Math.abs(head - req);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = req;
                }
            }

            totalSeekTime += minDistance;
            head = closest;
            pending.remove(Integer.valueOf(closest));
        }

        return totalSeekTime;
    }

    // SCAN Algorithm
    public static int scan(int[] requests, int head) {
        Arrays.sort(requests);
        int totalSeekTime = 0;
        int currentIndex = findStartingIndex(requests, head);
        boolean movingRight = true;

        while (true) {
            if (movingRight) {
                for (int i = currentIndex; i < requests.length; i++) {
                    totalSeekTime += Math.abs(head - requests[i]);
                    head = requests[i];
                }
                movingRight = false; // Change direction
            } else {
                for (int i = currentIndex - 1; i >= 0; i--) {
                    totalSeekTime += Math.abs(head - requests[i]);
                    head = requests[i];
                }
                break; // Stop after servicing all requests
            }
        }

        return totalSeekTime;
    }

    // C-LOOK Algorithm
    public static int cLook(int[] requests, int head) {
        Arrays.sort(requests);
        int totalSeekTime = 0;
        int currentIndex = findStartingIndex(requests, head);

        // Service requests moving right
        for (int i = currentIndex; i < requests.length; i++) {
            totalSeekTime += Math.abs(head - requests[i]);
            head = requests[i];
        }

        // Wrap around and service requests on the left
        for (int i = 0; i < currentIndex; i++) {
            totalSeekTime += Math.abs(head - requests[i]);
            head = requests[i];
        }

        return totalSeekTime;
    }

    // Helper function to find the index of the first request greater than or equal to head
    private static int findStartingIndex(int[] requests, int head) {
        for (int i = 0; i < requests.length; i++) {
            if (requests[i] >= head) {
                return i;
            }
        }
        return requests.length; // If all requests are less than the head position
    }
}


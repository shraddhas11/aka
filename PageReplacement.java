import java.util.*;

public class PageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Input: Reference string and frame size
            System.out.print("Enter the number of pages in reference string: ");
            int numPages = scanner.nextInt();
            System.out.print("Enter the reference string (space-separated): ");
            int[] referenceString = new int[numPages];
            for (int i = 0; i < numPages; i++) {
                referenceString[i] = scanner.nextInt();
            }
            System.out.print("Enter the frame size: ");
            int frameSize = scanner.nextInt();

            // Calculate page faults for all three algorithms
            System.out.println("\nPage Faults using FIFO: " + calculateFIFO(referenceString, frameSize));
            System.out.println("Page Faults using LRU: " + calculateLRU(referenceString, frameSize));
            System.out.println("Page Faults using Optimal: " + calculateOptimal(referenceString, frameSize));
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // FIFO Page Replacement
    public static int calculateFIFO(int[] referenceString, int frameSize) {
        Queue<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    frames.poll(); // Remove the oldest page
                }
                frames.add(page);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    // LRU Page Replacement
    public static int calculateLRU(int[] referenceString, int frameSize) {
        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    frames.removeFirst(); // Remove the least recently used page
                }
                pageFaults++;
            } else {
                frames.remove((Integer) page); // Remove and re-add to make it most recently used
            }
            frames.add(page);
        }
        return pageFaults;
    }

    // Optimal Page Replacement
    public static int calculateOptimal(int[] referenceString, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    int farthest = i, replaceIndex = 0;
                    for (int j = 0; j < frames.size(); j++) {
                        int futureIndex = Integer.MAX_VALUE;
                        for (int k = i + 1; k < referenceString.length; k++) {
                            if (referenceString[k] == frames.get(j)) {
                                futureIndex = k;
                                break;
                            }
                        }
                        if (futureIndex > farthest) {
                            farthest = futureIndex;
                            replaceIndex = j;
                        }
                    }
                    frames.remove(replaceIndex); // Replace the optimal page
                }
                frames.add(page);
                pageFaults++;
            }
        }
        return pageFaults;
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MatrixOperations {

    private final int[][] matrixA;
    private final int[][] matrixB;
    private int[][] result;

    MatrixOperations(int[][] matrixA, int[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = new int[matrixA.length][matrixB[0].length];
    }

    // Worker thread for matrix addition
    class AdditionTask implements Runnable {
        private final int row;
        private final int col;

        AdditionTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            result[row][col] = matrixA[row][col] + matrixB[row][col];
            System.out.println("Computed cell [" + row + "][" + col + "] (Addition) = " + result[row][col]);
        }
    }

    // Worker thread for matrix subtraction
    class SubtractionTask implements Runnable {
        private final int row;
        private final int col;

        SubtractionTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            result[row][col] = matrixA[row][col] - matrixB[row][col];
            System.out.println("Computed cell [" + row + "][" + col + "] (Subtraction) = " + result[row][col]);
        }
    }

    // Worker thread for matrix multiplication
    class MultiplicationTask implements Runnable {
        private final int row;
        private final int col;

        MultiplicationTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            for (int k = 0; k < matrixB.length; k++) {
                result[row][col] += matrixA[row][k] * matrixB[k][col];
            }
            System.out.println("Computed cell [" + row + "][" + col + "] (Multiplication) = " + result[row][col]);
        }
    }

    // Perform the specified matrix operation
    public void performOperation(String operation) {
        ExecutorService executor = Executors.newFixedThreadPool(4); // Limit to 4 threads
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                switch (operation.toLowerCase()) {
                    case "add":
                        executor.execute(new AdditionTask(i, j));
                        break;
                    case "subtract":
                        executor.execute(new SubtractionTask(i, j));
                        break;
                    case "multiply":
                        executor.execute(new MultiplicationTask(i, j));
                        break;
                    default:
                        System.out.println("Invalid operation!");
                        executor.shutdownNow();
                        return;
                }
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to complete
        }
    }

    // Display the resulting matrix
    public void displayResult(String operation) {
        System.out.println("Resultant Matrix (" + operation + "):");
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

class MultiOperationMatrix {
    public static void main(String[] args) {
        int[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrixB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        MatrixOperations matrixOps = new MatrixOperations(matrixA, matrixB);

        // Perform addition
        System.out.println("Starting Matrix Addition...");
        matrixOps.performOperation("add");
        matrixOps.displayResult("Addition");

        // Perform subtraction
        System.out.println("\nStarting Matrix Subtraction...");
        matrixOps.performOperation("subtract");
        matrixOps.displayResult("Subtraction");

        // Perform multiplication
        System.out.println("\nStarting Matrix Multiplication...");
        matrixOps.performOperation("multiply");
        matrixOps.displayResult("Multiplication");
    }
}

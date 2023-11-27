package thread.pool;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumRowCol(matrix.length, matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int k = 0; k < matrix.length; k++) {
            futures.put(k, getTask(matrix, k));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = sumRowCol(matrix.length, matrix, i);
            return sums;
        });
    }

    public static Sums sumRowCol(int length, int[][] matrix, int i) {
        Sums sums = new Sums();
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < length; j++) {
            rowSum += matrix[i][j];
            colSum += matrix[j][i];
        }
        sums.setRowSum(rowSum);
        sums.setColSum(colSum);
        return sums;
    }

}

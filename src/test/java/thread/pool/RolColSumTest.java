package thread.pool;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.assertThat;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] matrix = new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};
        Sums[] sums = RolColSum.sum(matrix);
        Sums[] exp = new Sums[]{
                new Sums(15, 55),
                new Sums(40, 60),
                new Sums(65, 65),
                new Sums(90, 70),
                new Sums(115, 75)};
        assertThat(sums).isEqualTo(exp);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};
        Sums[] sums = RolColSum.asyncSum(matrix);
        Sums[] exp = new Sums[]{
                new Sums(15, 55),
                new Sums(40, 60),
                new Sums(65, 65),
                new Sums(90, 70),
                new Sums(115, 75)};
        assertThat(sums).isEqualTo(exp);
    }
}
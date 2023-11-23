package thread.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelSearchIndexTest {

    @Test
    public void whenDifferentDataTypes() {
        Object[] array = {1, 2, "test", 5, 88, false, 244, 3,45, 45, "temp", 78, 23, true, 76};
        int ind = ParallelSearchIndex.search(array, "test");
        assertThat(ind).isEqualTo(2);
    }

    @Test
    public void whenSmallArray() {
        Object[] array = {78, 23, 26, 76};
        int ind = ParallelSearchIndex.search(array, 26);
        assertThat(ind).isEqualTo(2);
    }

    @Test
    public void whenBigArray() {
        Object[] array = {1, 2, 3, 5, 88, 576, 244, 2, 45, 12, 78, 23, 26, 76};
        int ind = ParallelSearchIndex.search(array, 12);
        assertThat(ind).isEqualTo(9);
    }

    @Test
    public void whenElementNotFound() {
        Object[] array = {1, 2, 3, 5, 88, 576, 244, 2, 45, 12, 78, 23, 26, 76};
        int ind = ParallelSearchIndex.search(array, 55);
        assertThat(ind).isEqualTo(-1);
    }
}

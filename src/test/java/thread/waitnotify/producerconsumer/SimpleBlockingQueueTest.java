package thread.waitnotify.producerconsumer;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueueTest {

    @Test
    void whenPollThenOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        List<Integer> values = new ArrayList<>();
        Thread producer = new Thread(() -> {
            try {
                queue.offer(235);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                values.add(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(values.get(0)).isEqualTo(235);
    }

    @Test
    void whenLimitExceeded() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        List<Integer> values = new ArrayList<>();
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
                queue.offer(5);
                queue.offer(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                values.add(queue.poll());
                values.add(queue.poll());
                Thread.sleep(5000);
                values.add(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(values.get(2)).isEqualTo(3);
    }

    @Test
    void whenQueueIsEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        List<Integer> values = new ArrayList<>();
        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(5000);
                queue.offer(567);

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                values.add(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(values.get(0)).isEqualTo(567);
    }
}

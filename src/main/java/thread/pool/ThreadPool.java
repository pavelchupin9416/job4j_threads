package thread.pool;

import thread.waitnotify.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks =
            new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors());

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {

            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            ));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}

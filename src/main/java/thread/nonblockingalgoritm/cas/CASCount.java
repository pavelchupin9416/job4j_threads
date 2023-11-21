package thread.nonblockingalgoritm.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;


@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        do {
            temp = count.get();
        }
        while (!count.compareAndSet(temp, temp + 1));
    }

    public int get() {
        return count.get();
    }
}
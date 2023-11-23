package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
   private final ExecutorService pool = Executors.newCachedThreadPool();

   private void emailTo(User user) {
       String subject = String.format("Notification %s to email %s.", user.username(), user.email());
       String body = String.format("Add a new event to %s", user.username());
       pool.submit(() -> send(subject, body, user.email()));
   }

    private void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}

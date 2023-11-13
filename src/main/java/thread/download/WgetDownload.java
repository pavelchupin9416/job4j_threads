package thread.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetDownload implements Runnable {
    private final String url;
    private final int speed;
    private final String name;

    public WgetDownload(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        var file = new File(name);
        System.out.println(name);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {

            var dataBuffer = new byte[512];
            int bytesRead;
            int totalBytes = 0;
            var startAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                if (totalBytes >= speed) {
                    long pause = 1000 - (System.currentTimeMillis() - startAt);
                    if (pause > 0) {
                        Thread.sleep(pause);
                        System.out.println("sleep " + pause);
                    }
                    totalBytes = 0;
                    startAt = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (!args[0].startsWith("https://") && !args[0].isBlank()) {
            throw new  IllegalArgumentException("Не верная ссылка");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new  IllegalArgumentException("Не верно указана скорость");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String name = args[2];
        Thread wget = new Thread(new WgetDownload(url, speed, name));
        wget.start();
        wget.join();
    }
}


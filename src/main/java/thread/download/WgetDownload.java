package thread.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetDownload implements Runnable {
    private final String url;
    private final int speed;

    public WgetDownload(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp2.xml");
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                double nano = (System.nanoTime() - downloadAt);
                System.out.println("Read 512 bytes : " + nano + " nano.");
                double sleep = 512 / nano * 1000000 / speed;
                if (sleep > 1) {
                System.out.println("sleep " + sleep);
                Thread.sleep((int) sleep);
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
        Thread wget = new Thread(new WgetDownload(url, speed));
        wget.start();
        wget.join();
    }
}

package thread.synchronization.io;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(content.getBytes());
        }
    }
}

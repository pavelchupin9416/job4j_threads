package thread.synchronization.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(c -> c < 0x80);
    }
}

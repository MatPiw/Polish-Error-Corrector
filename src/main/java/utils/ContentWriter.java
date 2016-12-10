package utils;

import java.io.*;
import java.util.List;

public class ContentWriter {
    protected BufferedWriter writer;
    protected String filename;
    protected File sourceFile;

    public ContentWriter(String filename) throws FileNotFoundException {
        sourceFile = new File(filename);
        writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(sourceFile)));
        this.filename = filename;
    }

    public ContentWriter(File file) throws FileNotFoundException {
        sourceFile = file;
        writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(sourceFile)));
        this.filename = file.getName();
    }

    public void writeLine(String text) throws IOException {
        writer.write(text);
        writer.newLine();
    }

    public void close() throws IOException {
        writer.flush();
        writer.close();
    }

    public void writeContentToFile(List<String>content) throws IOException {
        for (String str : content)
            writeLine(str);
    }
}

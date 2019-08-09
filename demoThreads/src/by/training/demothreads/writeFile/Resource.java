package by.training.demothreads.writeFile;

import java.io.FileWriter;
import java.io.IOException;

public class Resource {
    /**
     * FileWriter instance.
     */
    private FileWriter fileWriter;

    /**
     * Constructor initialises FileWriter instance.
     *
     * @param newFile file name
     * @throws IOException if file can not be found
     */
    public Resource(final String newFile) throws IOException {
        fileWriter = new FileWriter(newFile, false);
    }

    /**
     * write to file.
     *
     * @param str content
     * @param i   content
     */
    public synchronized void writing(final String str, final int i) {
        try {
            fileWriter.append(str + i);
            System.out.print(str + i);
            Thread.sleep((long) (Math.random() * 50));
            fileWriter.append("->" + i + " ");
            System.out.print("->" + i + " ");
        } catch (IOException e) {
            System.err.print("stream exception: " + e);
        } catch (InterruptedException e) {
            System.err.print("thread exception: " + e);
        }
    }

    /**
     * close file writer.
     */
    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.err.print("file close exception: " + e);
        }
    }
}

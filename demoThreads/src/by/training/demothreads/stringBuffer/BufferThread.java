package by.training.demothreads.stringBuffer;

import java.util.concurrent.TimeUnit;

public final class BufferThread {
    /**
     * counter.
     */
    private static int counter = 0;
    /**
     * StringBuffer instance.
     */
    private static StringBuffer s = new StringBuffer();
    private BufferThread() {
    }
    /**
     * main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        new Thread() {
            public void run() {
                synchronized (s) {
                    while (BufferThread.counter++ < 3) {
                        s.append("A");
                        System.out.print("> " + counter + " ");
                        System.out.println(s);
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException newE) {
                            newE.printStackTrace();
                        }
                    }
                }
            }

        }.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException newE) {
            newE.printStackTrace();
        }
        while (BufferThread.counter++ < 6) {
            System.out.print("< " + counter + " ");
            s.append("B");
            System.out.println(s);
        }
    }
}

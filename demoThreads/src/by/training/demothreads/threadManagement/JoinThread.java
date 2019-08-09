package by.training.demothreads.threadManagement;

import java.util.concurrent.TimeUnit;

public class JoinThread extends Thread {
    /**
     * Constructor.
     *
     * @param name thread name
     */
    public JoinThread(final String name) {
        super(name);
    }

    /**
     * overridden run method.
     */
    @Override
    public void run() {
        String nameT = getName();
        long timeout = 0;
        System.out.println("Thread started " + nameT);
        try {
            switch (nameT) {
                case "First":
                    timeout = 5_000;
                    break;
                case "Second":
                    timeout = 1_000;
                    break;
            }
            TimeUnit.MILLISECONDS.sleep(timeout);
            System.out.println("Thread finished " + nameT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

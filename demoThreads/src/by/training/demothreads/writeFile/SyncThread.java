package by.training.demothreads.writeFile;

public class SyncThread extends Thread {
    /**
     * Resource instance.
     */
    private Resource rs;

    /**
     * Constructor.
     * @param newName thread name
     * @param newRs Resource instance
     */
    public SyncThread(final String newName, final Resource newRs) {
        super(newName);
        this.rs = newRs;
    }

    /**
     * overridden run method.
     */
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            rs.writing(getName(), i);
        }
    }
}

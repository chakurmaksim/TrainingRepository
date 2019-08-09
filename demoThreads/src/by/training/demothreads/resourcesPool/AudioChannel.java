package by.training.demothreads.resourcesPool;

import java.util.Random;

public class AudioChannel {
    /**
     * сhanel Id number.
     */
    private int сhannellId;

    /**
     * Constructor.
     *
     * @param id new Id number
     */
    public AudioChannel(final int id) {
        super();
        this.сhannellId = id;
    }

    /**
     * Get method.
     * @return сhanel Id number
     */
    public int getСhannellId() {
        return сhannellId;
    }

    /**
     * set method.
     * @param newId new Id number
     */
    public void setСhannellId(final int newId) {
        this.сhannellId = newId;
    }

    /**
     * using chanel.
     */
    public void using() {
        try {
            Thread.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

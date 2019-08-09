package by.training.demothreads.resourcesPool;

public class Client extends Thread {
    /**
     * flag variable.
     */
    private boolean reading = false;
    /**
     * ChannelPool variable.
     */
    private ChannelPool<AudioChannel> pool;

    /**
     * Constructor.
     * @param newPool ChannelPool object
     */
    public Client(final ChannelPool<AudioChannel> newPool) {
        this.pool = newPool;
    }

    /**
     * Overridden run method.
     */
    @Override
    public void run() {
        AudioChannel channel = null;
        try {
            channel = pool.getResource(500);
            reading = true;
            System.out.println("Channel Client #" + this.getId()
                    + " took channel #" + channel.getСhannellId());
            channel.using();
        } catch (ResourсeException e) {
            System.out.println("Client #" + this.getId() + " lost ->"
                    + e.getMessage());
        } finally {
            if (channel != null) {
                reading = false;
                System.out.println("Channel Client #" + this.getId() + " : "
                        + channel.getСhannellId() + " channel released");
                pool.returnResource(channel);
            }

        }
    }

    /**
     * get method.
     *
     * @return reading variable
     */
    public boolean isReading() {
        return reading;
    }
}

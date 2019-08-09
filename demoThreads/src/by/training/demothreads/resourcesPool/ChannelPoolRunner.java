package by.training.demothreads.resourcesPool;

import java.util.LinkedList;

public final class ChannelPoolRunner {
    private ChannelPoolRunner() {
    }

    /**
     * main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        LinkedList<AudioChannel> list = new LinkedList<AudioChannel>() {
            {
                this.add(new AudioChannel(771));
                this.add(new AudioChannel(883));
                this.add(new AudioChannel(550));
                this.add(new AudioChannel(337));
                this.add(new AudioChannel(442));
            }
        };

        ChannelPool<AudioChannel> pool = new ChannelPool<>(list);
        for (int i = 0; i < 20; i++) {
            new Client(pool).start();
        }
    }
}

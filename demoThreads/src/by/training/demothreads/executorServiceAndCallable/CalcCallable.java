package by.training.demothreads.executorServiceAndCallable;

import java.util.Random;
import java.util.concurrent.Callable;

public class CalcCallable implements Callable<Number> {
    /**
     * Imitation of the evaluating.
     * @return number
     * @throws Exception exception
     */
    @Override
    public Number call() throws Exception {
        Number res = new Random().nextGaussian();
        return res;
    }
}

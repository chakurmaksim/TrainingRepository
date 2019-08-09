package by.training.demothreads.executorServiceAndCallable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class CalcRunner {
    private CalcRunner() {
    }

    /**
     * main method.
     * @param args arguments
     */
    public static void main(final String[] args) throws Exception {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Number> future = es.submit(new CalcCallable());
        es.shutdown();
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Future<String>> list = new ArrayList<Future<String>>();
        ExecutorService esMult = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 6; i++) {
            list.add(esMult.submit(new BaseCallable()));
        }
        esMult.shutdown();
        for (Future<String> futureString : list) {
            System.out.println(futureString.get() + " result fixed");
        }
    }
}

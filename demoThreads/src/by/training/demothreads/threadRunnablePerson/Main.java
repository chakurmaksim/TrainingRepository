package by.training.demothreads.threadRunnablePerson;

public final class Main {
    private Main() {
    }

    /**
     * main method to start the program.
     *
     * @param args arguments.
     */
    public static void main(final String[] args) {
        RunnablePerson person1 = new RunnablePerson("Alice", 10);
        Thread thread1 = new Thread(person1);

        RunnablePerson person2 = new RunnablePerson("Tom", 10);
        Thread thread2 = new Thread(person2);

        thread1.start();
        thread2.start();
    }
}

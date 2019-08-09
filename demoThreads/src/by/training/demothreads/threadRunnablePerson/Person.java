package by.training.demothreads.threadRunnablePerson;

public class Person {
    /**
     * Person name.
     */
    private String name;

    /**
     * Constructor with one argument.
     *
     * @param newName name of the threadRunnablePerson.
     */
    public Person(final String newName) {
        name = newName;
    }

    /**
     * Get method.
     *
     * @return threadRunnablePerson name.
     */
    public String getName() {
        return name;
    }
}

package by.training.demothreads.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Student extends Thread {
    /**
     * student id.
     */
    private Integer idStudent;
    /**
     * list of the student tasks.
     */
    private List<Task> taskList;
    /**
     * CountDownLatch variable.
     */
    private CountDownLatch countDown;

    /**
     * Constructor with two arguments.
     *
     * @param newIdStudent student id number
     * @param numberTasks  quantity of the tasks
     */
    public Student(final Integer newIdStudent, final int numberTasks) {
        this.idStudent = newIdStudent;
        this.countDown = new CountDownLatch(numberTasks);
        this.taskList = new ArrayList<>(numberTasks);
    }

    /**
     * Getter.
     *
     * @return student id number
     */
    public Integer getIdStudent() {
        return idStudent;
    }

    /**
     * Setter.
     *
     * @param newIdStudent set new student id number
     */
    public void setIdStudent(final Integer newIdStudent) {
        this.idStudent = newIdStudent;
    }

    /**
     * Getter.
     *
     * @return CountDownLatch instance
     */
    public CountDownLatch getCountDownLatch() {
        return countDown;
    }

    /**
     * Getter.
     *
     * @return list of the student tasks
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Add the particular task to the tasks list.
     * @param task Task object
     */
    public void addTask(final Task task) {
        taskList.add(task);
    }

    /**
     * Overriden run method, where student do his tasks and
     * calculate average mark.
     */
    @Override
    public void run() {
        int i = 0;
        for (Task inWork : taskList) {
            // Required some time to manage with task
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Send the solution
            inWork.setAnswer("Answer #" + ++i);
            System.out.println("Answer #" + i + " from " + idStudent);
        }
        try {
            // Here is waiting for the checking tasks
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // calculate average mark
        float averageMark = 0;
        for (Task inWork : taskList) {
            averageMark += inWork.getMark();
        }
        averageMark /= taskList.size();
        System.out.println("Student " + idStudent + ": Average mark = "
                + averageMark);

    }
}

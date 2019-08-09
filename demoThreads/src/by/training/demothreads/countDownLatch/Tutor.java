package by.training.demothreads.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tutor extends Thread {
    /**
     * tutor id.
     */
    private Integer idTutor;
    /**
     * list of the students.
     */
    private List<Student> list;

    /**
     * Constructor where initialize list of students.
     */
    public Tutor() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructor with one argument.
     *
     * @param newList student list
     */
    public Tutor(final List<Student> newList) {
        this.list = newList;
    }

    /**
     * Getter.
     *
     * @return tutor id number
     */
    public Integer getIdTutor() {
        return idTutor;
    }

    /**
     * Setter.
     *
     * @param id set new tutor id
     */
    public void setIdTutor(final Integer id) {
        this.idTutor = id;
    }

    /**
     * Overridden run method, where is conducted checking students tasks.
     */
    @Override
    public void run() {
        for (Student st : list) {
            List<Task> tasks = st.getTaskList();
            for (Task current : tasks) {
                int mark = 3 + new Random().nextInt(7);
                current.setMark(mark);
                System.out.println(mark + " for student N "
                        + st.getIdStudent());
                st.getCountDownLatch().countDown();
            }
            System.out.println("All estimates made for " + st.getIdStudent());
        }
    }
}

package by.training.demothreads.countDownLatch;

public class Task {
    /**
     * Content of a task.
     */
    private String content;
    /**
     * students answer.
     */
    private String answer;
    /**
     * tutor mark.
     */
    private int mark;

    /**
     * Constructor to create a new instance of the Task.
     *
     * @param newContent of a task
     */
    public Task(final String newContent) {
        this.content = newContent;
    }

    /**
     * Getter.
     *
     * @return task content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter.
     *
     * @param newContent new content
     */
    public void setContent(final String newContent) {
        this.content = newContent;
    }

    /**
     * Getter.
     *
     * @return student answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Setter.
     *
     * @param newAnswer new student answer
     */
    public void setAnswer(final String newAnswer) {
        this.answer = newAnswer;
    }

    /**
     * Getter.
     *
     * @return mark
     */
    public int getMark() {
        return mark;
    }

    /**
     * Setter.
     * @param newMark new mark
     */
    public void setMark(final int newMark) {
        this.mark = newMark;
    }
}

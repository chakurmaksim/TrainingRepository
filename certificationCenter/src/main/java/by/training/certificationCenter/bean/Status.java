package by.training.certificationCenter.bean;

public enum Status {
    REGISTRATION("На регистрации", 0),
    CONSIDERATION("Рассмотрение и принятие решения", 1),
    REJECTED("Отклонена",2),
    APPROVED("Работы завершены",3);

    private String status;
    private int index;

    Status(final String newStatus, final int ind) {
        this.status = newStatus;
        this.index = ind;
    }

    public String getStatus() {
        return status;
    }

    public int getIndex() {
        return index;
    }

    public static Status getByIdentity(int identity) {
        return Status.values()[identity];
    }
}

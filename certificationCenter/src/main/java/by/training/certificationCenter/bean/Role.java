package by.training.certificationCenter.bean;

public enum Role {
    GUEST("Гость", 0),
    SUPER_CLIENT("Супер клиент", 1),
    CLIENT("Клиент",2),
    ADMIN("Администратор",3),
    EXPERT("Эксперт",4);

    private String role;
    private int index;

    Role(final String newRole, final int ind) {
        this.role = newRole;
        this.index = ind;
    }

    public String getRole() {
        return role;
    }

    public int getIndex() {
        return index;
    }
}

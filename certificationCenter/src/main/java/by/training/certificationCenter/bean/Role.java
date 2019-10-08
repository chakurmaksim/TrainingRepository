package by.training.certificationCenter.bean;

import java.io.Serializable;

public enum Role implements Serializable {
    GUEST("Guest", 0),
    ADMIN("Administrator",1),
    EXPERT("Expert",2),
    CLIENT("Client",3);

    private String roleName;
    private int index;

    Role(final String newRole, final int ind) {
        this.roleName = newRole;
        this.index = ind;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getIndex() {
        return index;
    }

    public static Role getByIdentity(int identity) {
        return Role.values()[identity];
    }
}

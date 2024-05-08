import java.util.ArrayList;

public class Administrator extends User{
    private boolean isSuperAdmin;
    private ArrayList<Permissions> permissions = new ArrayList<>();

    public Administrator(Profile profile, String userName, String password, boolean isSuperAdmin) {
        super(profile, userName, password);
        this.isSuperAdmin = isSuperAdmin;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public ArrayList<Permissions> getPermissions() {
        return permissions;
    }

    public void setDelete() {
        this.permissions.add(Permissions.DELETE);
    }
    public void setWrite() {
        this.permissions.add(Permissions.WRITE);
    }
    public void setRead() {
        this.permissions.add(Permissions.READ);
    }
}

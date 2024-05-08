abstract public class User {
    private Profile profile;
    private String userName;
    private String password;

    public User(Profile profile, String userName, String password) {
        this.profile = profile;
        this.userName = userName;
        this.password = Auxiliary.hashString(password);
    }

    public Profile getProfile() {
        return profile;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public static boolean checkPassword(String password, User user) {
        boolean ret = false;
        if (Auxiliary.hashString(password).equals(user.getPassword())) {
            ret = true;
        }
        return ret;
    }
}

package data.userHandling;

public class SystemAdministrator extends User {
    private static final UserManager USERMANAGER = UserManager.getInstance();
    public SystemAdministrator(int ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }
}

package data.userHandling;

import Interfaces.IAdministrator;

public class SystemAdministrator extends User implements IAdministrator {
    private static final UserManager USERMANAGER = UserManager.getInstance();

    public SystemAdministrator(String username) {
        super(username);
    }
    public SystemAdministrator(String username, String password) {
        super(username, password);
    }

    public SystemAdministrator(int ID, String username, String password) {
        super(ID, username, password);
    }

}

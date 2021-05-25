package presentation.userManage;

import Interfaces.IAdministrator;

public class Systemadministrator extends User implements IAdministrator {
    public Systemadministrator(String username, String password) {
        super(username, password);
    }

    public Systemadministrator(int ID, String username, String password) {
        super(ID, username, password);
    }
}

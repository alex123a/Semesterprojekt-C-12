package domain.session;

import Interfaces.ISession;
import Interfaces.IUser;

public class CurrentSession implements ISession {
    private static final CurrentSession current = new CurrentSession();
    private IUser currentUser = null;

    @Override
    public IUser getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(IUser user) {
        this.currentUser = user;
    }

    public static CurrentSession getInstance() {
        return current;
    }

}

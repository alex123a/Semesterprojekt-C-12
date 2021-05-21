package data.userHandling;

import Interfaces.IProducer;

public class Producer extends User implements IProducer {

    public Producer(String username) {
        super(username);
    }
    public Producer(String username, String password) {
        super(username, password);
    }

    public Producer(int ID, String username, String password) {
        super(ID, username, password);
    }

}

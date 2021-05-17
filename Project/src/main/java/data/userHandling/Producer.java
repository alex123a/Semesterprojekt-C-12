package data.userHandling;

public class Producer extends User {

    public Producer(int ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.username = username;
    }
}

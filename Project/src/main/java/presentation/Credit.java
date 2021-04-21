package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class Credit {

    private IRightsholder rightsholder;
    private List<String> roles;

    public Credit(IRightsholder rightsholder, List<String> roles) {
        this.rightsholder = rightsholder;
        this.roles = roles;
    }

    public IRightsholder getRightsholder() {
        return rightsholder;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return rightsholder.toString() + ": " + roles.toString();
    }
}

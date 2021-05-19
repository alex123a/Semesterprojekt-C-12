package presentation;

import Interfaces.IRightsholder;

import java.util.List;

public class CreditWrapper {
    IRightsholder rightsholder;
    List<String> roles;

    public CreditWrapper(IRightsholder rightsholder, List<String> roles) {
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
        return rightsholder.getFirstName()+" "+rightsholder.getLastName()+": "+roles.toString();
    }
}

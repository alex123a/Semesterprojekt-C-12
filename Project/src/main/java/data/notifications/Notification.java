package data.notifications;

import Interfaces.INotification;
import Interfaces.IProducer;
import Interfaces.IProduction;

public class Notification implements INotification {

    private int ID;
    private String text;
    private IProduction production;

    public Notification() {

    }

    public Notification(String text) {
        this.text = text;
    }

    public Notification(String text, IProduction production) {
        this(text);
        this.production = production;
    }

    public Notification(int ID, String text, IProduction production) {
        this(text, production);
        this.ID = ID;
    }



    @Override
    public void setViewed(boolean status) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean getViewed() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setApproval(int approval) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int getApproval() {
        throw new UnsupportedOperationException("Not supported");
    }

    public int getID() {
        return this.ID;
    }

    @Override
    public IProducer getProducer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProducer(IProducer producer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public IProduction getProduction() {
        return production;
    }

    @Override
    public void setProduction(IProduction production) {
        this.production = production;
    }
}

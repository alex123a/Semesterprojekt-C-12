package data.notifications;

import Interfaces.INotification;

public class Notification implements INotification {

    private int ID;
    private String text;
    private int productionID;

    public Notification() {

    }

    public Notification(String text, int productionID) {
        this.text = text;
        this.productionID = productionID;
    }

    public Notification(int ID, String text, int productionID) {
        this(text, productionID);
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
    public int getProductionId() {
        return this.productionID;
    }

    @Override
    public void setApproval(int approval) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int getApproval() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public int getProducerID() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setProducerID(int producerID) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}

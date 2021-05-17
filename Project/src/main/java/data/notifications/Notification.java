package data.notifications;

import Interfaces.INotification;

public class Notification implements INotification {

    private int ID;
    private String text;
    private int productionID;
    private boolean viewed;

    public Notification() {

    }

    public Notification(int ID, String text, int productionID, boolean viewed) {
        this.ID = ID;
        this.text = text;
        this.productionID = productionID;
        this.viewed = viewed;
    }

    @Override
    public void setViewed(boolean status) {
        this.viewed = status;
    }

    @Override
    public boolean getViewed() {
        return this.viewed;
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
        return 0;
    }
}

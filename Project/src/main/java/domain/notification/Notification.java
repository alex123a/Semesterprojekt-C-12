package domain.notification;

import Interfaces.INotification;

public class Notification implements INotification {

    private String text;
    private String productionID;
    private String productionName;

    public Notification() {

    }

    public Notification(String text) {
        this.text = text;
    }

    public Notification(String text, String productionID, String productionName) {
        this(text);
        this.productionID = productionID;
        this.productionName = productionName;
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
    public String getProductionId() {
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
    public int getProducerID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProducerID(int producerID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getProductionName() {
        return productionName;
    }

    @Override
    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

}

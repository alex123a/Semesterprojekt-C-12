package data.notifications;

import Interfaces.INotification;

public class Notification implements INotification {

    private int ID;
    private String text;
    private String productionID;
    private String productionName;
    private int db_id_production;

    public Notification() {

    }

    public Notification(String text) {
        this.text = text;
    }

    public Notification(String text, String productionID, String productionName, int db_id_production) {
        this(text);
        this.productionID = productionID;
        this.productionName = productionName;
        this.db_id_production = db_id_production;
    }

    public Notification(int ID, String text, String productionID, String productionName, int db_id_production) {
        this(text, productionID, productionName, db_id_production);
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

    public int getID() {
        return this.ID;
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

    public int getDb_id_production() {
        return db_id_production;
    }

    public void setDb_id_production(int db_id_production) {
        this.db_id_production = db_id_production;
    }
}

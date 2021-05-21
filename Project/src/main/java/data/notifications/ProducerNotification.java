package data.notifications;

public class ProducerNotification extends Notification {
    private int producerID;
    private boolean viewed;

    public ProducerNotification(String productionID, String text, boolean viewed, int producerID, String productionName, int db_id_production) {
        super(text, productionID, productionName, db_id_production);
        this.viewed = viewed;
        this.producerID = producerID;
    }

    public ProducerNotification(int ID, String productionID, String text, boolean viewed, int producerID, String productionName, int db_id_production) {
        super(ID, text, productionID, productionName, db_id_production);
        this.viewed = viewed;
        this.producerID = producerID;
    }

    @Override
    public int getProducerID() {
        return producerID;
    }

    @Override
    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    @Override
    public void setViewed(boolean status) {
        this.viewed = status;
    }

    @Override
    public boolean getViewed() {
        return this.viewed;
    }
}

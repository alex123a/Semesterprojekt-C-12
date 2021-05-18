package data.notifications;

public class ProducerNotification extends Notification {
    private int producerID;
    private boolean viewed;

    public ProducerNotification(int productionID, String text, boolean viewed, int producerID) {
        super(text, productionID);
        this.viewed = viewed;
        this.producerID = producerID;
    }

    public ProducerNotification(int ID, int productionID, String text, boolean viewed, int producerID) {
        super(ID, text, productionID);
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

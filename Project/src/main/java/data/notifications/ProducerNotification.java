package data.notifications;

public class ProducerNotification extends Notification {
    private int producerID;

    public ProducerNotification(int ID, String text, int productionID, boolean viewed, int producerID) {
        super(ID, text, productionID, viewed);
        this.producerID = producerID;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }
}

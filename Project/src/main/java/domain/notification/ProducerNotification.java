package domain.notification;

public class ProducerNotification extends Notification {
    private int producerID;
    private boolean viewed;

    public ProducerNotification(String productionID, String text, boolean viewed, int producerID, String productionName) {
        super(text, productionID, productionName);
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

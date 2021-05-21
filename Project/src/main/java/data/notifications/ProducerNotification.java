package data.notifications;

import Interfaces.IProduction;

public class ProducerNotification extends Notification {
    private int producerID;
    private boolean viewed;

    public ProducerNotification(IProduction production, String text, boolean viewed, int producerID) {
        super(text, production);
        this.viewed = viewed;
        this.producerID = producerID;
    }

    public ProducerNotification(int ID, int producerID, String text, boolean viewed, IProduction production) {
        super(ID, text, production);
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

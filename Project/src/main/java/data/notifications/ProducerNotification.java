package data.notifications;

import Interfaces.IProducer;
import Interfaces.IProduction;

public class ProducerNotification extends Notification {
    private IProducer producer;
    private boolean viewed;

    public ProducerNotification(IProduction production, String text, boolean viewed, IProducer producer) {
        super(text, production);
        this.viewed = viewed;
        this.producer = producer;
    }

    public ProducerNotification(int ID, IProducer producer, String text, boolean viewed, IProduction production) {
        super(ID, text, production);
        this.viewed = viewed;
        this.producer = producer;
    }

    @Override
    public IProducer getProducer() {
        return producer;
    }

    @Override
    public void setProducer(IProducer producer) {
        this.producer = producer;
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

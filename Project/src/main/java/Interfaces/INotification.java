package Interfaces;

public interface INotification {

    void setViewed(boolean status);

    boolean getViewed();

    String getText();

    int getProductionId();

    void setApproval(int approval);

    int getApproval();

    int getID();

    int getProducerID();

    void setProducerID(int producerID);
}

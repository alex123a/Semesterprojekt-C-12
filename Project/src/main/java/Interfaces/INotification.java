package Interfaces;

public interface INotification {

    void setViewed(boolean status);

    boolean getViewed();

    String getText();

    String getProductionId();

    void setApproval(int approval);

    int getApproval();

    int getProducerID();

    void setProducerID(int producerID);

    void setText(String text);

    String getProductionName();

    void setProductionName(String productionName);
}

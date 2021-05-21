package Interfaces;

public interface INotification {

    void setViewed(boolean status);

    boolean getViewed();

    String getText();

    void setApproval(int approval);

    int getApproval();

    int getProducerID();

    void setProducerID(int producerID);

    void setText(String text);

    IProduction getProudction();

    void setProduction(IProduction production);

}

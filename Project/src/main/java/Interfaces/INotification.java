package Interfaces;

public interface INotification {

    void setViewed(boolean status);

    boolean getViewed();

    String getText();

    void setApproval(int approval);

    int getApproval();

    IProducer getProducer();

    void setProducer(IProducer producer);

    void setText(String text);

    IProduction getProduction();

    void setProduction(IProduction production);
}

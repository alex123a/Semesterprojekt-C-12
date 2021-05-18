package Interfaces;

public interface INotification {

    void setViewed(boolean status);

    String getViewed();

    void setApproval(String approval);

    String getApproval();

    String getText();

    int getProductionId();

}

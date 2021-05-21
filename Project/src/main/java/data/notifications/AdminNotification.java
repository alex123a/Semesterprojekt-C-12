package data.notifications;

public class AdminNotification extends Notification {
    private int approvalStatus;

    public AdminNotification(String text, int approvalStatus) {
        super(text);
        this.approvalStatus = approvalStatus;
    }

    public AdminNotification(String text, String productionID, String productionName, int approvalStatus, int db_id_production) {
        super(text, productionID, productionName, db_id_production);
        this.approvalStatus = approvalStatus;
    }

    public AdminNotification(int ID, String text, String productionID, String productionName, int approvalStatus, int db_id_production) {
        super(ID, text, productionID, productionName, db_id_production);
        this.approvalStatus = approvalStatus;
    }

    @Override
    public void setApproval(int approval) {
        this.approvalStatus = approval;
    }

    @Override
    public int getApproval() {
        return approvalStatus;
    }
}

package domain.notification;

public class AdminNotification extends Notification {
    private int approvalStatus;

    public AdminNotification(String text, int approvalStatus) {
        super(text);
        this.approvalStatus = approvalStatus;
    }

    public AdminNotification(String text, String productionID, String productionName, int approvalStatus) {
        super(text, productionID, productionName);
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

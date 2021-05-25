package data.notifications;

public class AdminNotifcation extends Notification {
    private int approvalStatus;

    public AdminNotifcation(String text, int productionID, int approvalStatus) {
        super(text, productionID);
        this.approvalStatus = approvalStatus;
    }

    public AdminNotifcation(int ID, String text, int productionID, int approvalStatus) {
        super(ID, text, productionID);
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

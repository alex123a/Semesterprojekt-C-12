package domain.notification;

import Interfaces.IProduction;

public class AdminNotification extends Notification {
    private int approvalStatus;

    public AdminNotification(String text, int approvalStatus) {
        super(text);
        this.approvalStatus = approvalStatus;
    }

    public AdminNotification(String text, IProduction production, int approvalStatus) {
        super(text, production);
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

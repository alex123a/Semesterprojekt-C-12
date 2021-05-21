package Interfaces;

public interface IDomainFacade extends
        ISearchCredits,
        IUserAuthentication,
        IAuthenticationHandler,
        IAuthenticator,
        ISeeCredits,
        ICreditManagement,
        IUserHandling,
        ISession,
        INotificationHandler,
        INotificationProvider {
    IProduction getProductionFromNotification(INotification notification);
}

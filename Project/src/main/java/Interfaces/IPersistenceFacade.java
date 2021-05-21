package Interfaces;


public interface IPersistenceFacade extends IReporting, IUserFacade, IFacadeData, INotificationHandler, INotificationProvider {
     IProduction getProductionFromNotification(INotification notification);
}

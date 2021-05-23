package Interfaces;


public interface IPersistenceFacade extends IReporting, IUserFacade, IFacadeData, INotificationHandler, INotificationProvider {
     IProduction getProductionFromID(IProduction production);
     void logAction(String text, IUser user);
}

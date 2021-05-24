package Interfaces;


public interface IPersistenceFacade extends IReporting, IUserFacade, IFacadeData, INotificationFacade {
     IProduction getProductionFromID(IProduction production);
     void logAction(String text, IUser user);
}

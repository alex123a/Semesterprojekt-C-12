package Interfaces;

public interface IReporting {

    int getTotalCreditCount();

    int generateProductionCreditsCount(IProduction production, String title);

    void generateCreditTypeCount();

    void generate10MostCredited();

    void generateCreditsReport();
}

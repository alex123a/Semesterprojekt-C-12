package Interfaces;

import data.Production;

public interface IReporting {

    int getTotalCreditCount();

    int generateProductionCreditsCount(Production production, String title);

    void generateCreditTypeCount();

    void generate10MostCredited();

    void generateCreditsReport();
}

package Interfaces;

import data.Production;
import enumerations.Roles;

public interface IReporting {

    int getTotalCreditCount();

    void generateProductionCreditsCount(Production production, Roles role)

    void generateCreditTypeCount();

    void generate10MostCredited();

    void generateCreditsReport();
}

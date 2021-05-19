package Interfaces;

import java.util.Map;

public interface IReporting {

    int getTotalCreditCount();

    Map<String, Integer> generateProductionCreditsCount(IProduction production);

    int generateCreditTypeCount(String type);

    Map<Integer, Integer> generate10MostCredited();

    void generateCreditsReport();
}

package Interfaces;

import java.util.List;
import java.util.Map;

public interface IReporting {

    int getTotalCreditCount();

    int generateProductionCreditsCount(IProduction production, String title);

    int generateCreditTypeCount(String type);

    Map<Integer, Integer> generate10MostCredited();

    void generateCreditsReport();
}

package Interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface IReportHandler {

    JSONObject getTotalCreditCount();

    JSONObject generateProductionCreditsCount(IProduction production);

    List<JSONObject> generateCreditTypeCount();

    List<JSONObject> generate10MostCredited();

    JSONObject generateCreditsReport();

}

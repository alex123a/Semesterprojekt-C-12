package Interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface IReportHandler {

    JSONObject getTotalCreditCount();

    List<JSONObject> generateProductionCreditsCount(IProduction production);

    List<JSONObject> generateCreditTypeCount(String type);

    List<JSONObject> generate10MostCredited();

    List<JSONObject> generateCreditsReport();

}

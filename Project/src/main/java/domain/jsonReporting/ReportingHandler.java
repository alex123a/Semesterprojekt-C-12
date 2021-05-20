package domain.jsonReporting;

import Interfaces.IProduction;
import Interfaces.IReportHandler;
import data.PersistenceFacade;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingHandler implements IReportHandler {
    private static final ReportingHandler report = new ReportingHandler();

    private ReportingHandler() {}

    @Override
    public JSONObject getTotalCreditCount() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalCreditCount",PersistenceFacade.getInstance().getTotalCreditCount());

        return jsonObject;
    }

    @Override
    public JSONObject generateProductionCreditsCount(IProduction production) {
        Map<String, Integer> creditMap = new HashMap<>(PersistenceFacade.getInstance().generateProductionCreditsCount(production));
        JSONObject jsonReturn = new JSONObject();
        JSONArray jsonObjectList = new JSONArray();
        int totalNumOfCredits = 0;
        for (String i : creditMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", i);
            jsonObject.put("TitleCount",creditMap.get(i));
            jsonObjectList.add(jsonObject);
            totalNumOfCredits += creditMap.get(i);
        }
        jsonReturn.put("TotalNumberOfCredits",totalNumOfCredits);
        jsonReturn.put("Credit types",jsonObjectList);
        return jsonReturn;
    }

    @Override
    public List<JSONObject> generateCreditTypeCount() {
        Map<String, Integer> creditMap = new HashMap<>(PersistenceFacade.getInstance().generateCreditTypeCount());
        JSONArray jsonObjectList = new JSONArray();
        for (String i : creditMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreditName",i);
            jsonObject.put("CreditCount",creditMap.get(i));
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> generate10MostCredited() {
        Map<String, Integer> creditMap = new HashMap<>(PersistenceFacade.getInstance().generate10MostCredited());
        JSONArray jsonObjectList = new JSONArray();
        for (String i : creditMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CreditName",i);
            jsonObject.put("CreditCount",creditMap.get(i));
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }

    @Override
    public JSONObject generateCreditsReport() {
        return PersistenceFacade.getInstance().generateCreditsReport();
    }

    public static IReportHandler getInstance() {
        return report;
    }
}

package domain.jsonReporting;

import Interfaces.IProduction;
import Interfaces.IReportHandler;
import data.PersistenceFacade;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportHandler implements IReportHandler {
    private static final ReportHandler report = new ReportHandler();

    private ReportHandler() {}

    @Override
    public JSONObject getTotalCreditCount() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalCreditCount",PersistenceFacade.getInstance().getTotalCreditCount());

        return jsonObject;
    }

    @Override
    public List<JSONObject> generateProductionCreditsCount(IProduction production) {
        Map<String, Integer> creditMap = new HashMap<>(PersistenceFacade.getInstance().generateProductionCreditsCount(production));
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (String i : creditMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(i,creditMap.get(i));
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> generateCreditTypeCount(String type) {
        return null;
    }

    @Override
    public List<JSONObject> generate10MostCredited() {
        return null;
    }

    @Override
    public List<JSONObject> generateCreditsReport() {
        return null;
    }

    public IReportHandler getInstance() {
        return report;
    }
}

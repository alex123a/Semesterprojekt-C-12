package domain.jsonReporting;

import Interfaces.IProduction;
import Interfaces.IReportHandler;
import data.PersistenceFacade;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    public JSONArray generateCreditsReport() {
        List<String> strings = PersistenceFacade.getInstance().generateCreditsReport();
        JSONArray show = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < strings.size()-2; i += 2) {
            JSONObject participants = new JSONObject();
            if (strings.get(i).equals("New Production")) {
                JSONObject jsonObject1 = new JSONObject();
                if(!(i == 0)) {
                    jsonObject1.put("Participants",jsonArray);
                    jsonArray = new JSONArray();
                }
                strings.remove(i);
                jsonObject1.put("ID",strings.get(i));
                jsonObject1.put("ProgramName",strings.get(i+1));
                show.add(jsonObject1);
            } else {
                participants.put("id", strings.get(i));
                participants.put("Name", strings.get(i+1));
                jsonArray.add(participants);
            }
        }
        show.add(jsonArray);
        return show;
    }


    public static IReportHandler getInstance() {
        return report;
    }
}

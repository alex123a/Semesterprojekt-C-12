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
            jsonObject.put("title", i);
            jsonObject.put("titleCount",creditMap.get(i));
            jsonObjectList.add(jsonObject);
            totalNumOfCredits += creditMap.get(i);
        }
        jsonReturn.put("totalNumberOfCredits",totalNumOfCredits);
        jsonReturn.put("creditTypes",jsonObjectList);
        return jsonReturn;
    }

    @Override
    public List<JSONObject> generateCreditTypeCount() {
        Map<String, Integer> creditMap = new HashMap<>(PersistenceFacade.getInstance().generateCreditTypeCount());
        JSONArray jsonObjectList = new JSONArray();
        for (String i : creditMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creditName",i);
            jsonObject.put("creditCount",creditMap.get(i));
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
            jsonObject.put("creditName",i);
            jsonObject.put("creditCount",creditMap.get(i));
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }

    @Override
    public JSONArray generateCreditsReport() {
        List<String> strings = PersistenceFacade.getInstance().generateCreditsReport();
        JSONArray show = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < strings.size()-2; i += 2) {
            JSONObject participants = new JSONObject();
            if (strings.get(i).equals("New Production")) {
                if(!(i == 0)) {
                    jsonObject1.put("participants",jsonArray);
                    jsonArray = new JSONArray();
                    show.add(jsonObject1);
                }
                jsonObject1 = new JSONObject();
                strings.remove(i);
                jsonObject1.put("id",strings.get(i));
                jsonObject1.put("programName",strings.get(i+1));
            } else {
                participants.put("id", strings.get(i));
                participants.put("name", strings.get(i+1));
                participants.put("title",strings.get(i+2));
                jsonArray.add(participants);
                i++;
            }
        }
        jsonObject1.put("participants",jsonArray);
        show.add(jsonObject1);
        return show;
    }


    public static IReportHandler getInstance() {
        return report;
    }
}

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

    //Creates and returns a Json Object with the total credit count for the system
    @Override
    public JSONObject getTotalCreditCount() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalCreditCount",PersistenceFacade.getInstance().getTotalCreditCount());
        return jsonObject;
    }

    //Creates a JsonObject with the total number of credits for at specific production, and a JsonArray with a count for every title
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

    //Creates a full list of all counts for every title
    @Override
    public JSONArray generateCreditTypeCount() {
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

    //Creates the 10 credits which is credited the most
    @Override
    public JSONArray generate10MostCredited() {
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

    //Creates a JsonArray with all productions and their participants
    @Override
    public JSONArray generateCreditsReport() {
        List<String> strings = PersistenceFacade.getInstance().generateCreditsReport();
        JSONArray show = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < strings.size()-2; i += 2) {
            JSONObject participants = new JSONObject();
            //New Production is added as a holder for when there comes a new production, because it is unknown how long the participants list is
            if (strings.get(i).equals("New Production")) {
                if(!(i == 0)) {
                    jsonObject1.put("participants",jsonArray);
                    //A new jsonArray is created so there can be created a new production
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

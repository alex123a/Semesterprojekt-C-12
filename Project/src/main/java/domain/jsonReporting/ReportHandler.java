package domain.jsonReporting;

import Interfaces.IProduction;
import Interfaces.IReportHandler;
import data.PersistenceFacade;
import org.json.simple.JSONObject;

import java.util.List;

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
    public JSONObject generateProductionCreditsCount(IProduction production, String title) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wd",PersistenceFacade.getInstance().generateProductionCreditsCount(production,title));

        return jsonObject;
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

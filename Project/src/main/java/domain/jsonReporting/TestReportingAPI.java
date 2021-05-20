package domain.jsonReporting;

import Interfaces.IReportHandler;
import data.credits.Production;

public class TestReportingAPI {
    public static void main(String[] args) {
        IReportHandler reportHandler = ReportingHandler.getInstance();

        // Total number of credits in the Database
        System.out.println(reportHandler.getTotalCreditCount());

        // Total number of credits for the production parsed (this should be implemented where you chose an excisting production)
        Production production = new Production(1,null,null,null,0,null,null,null);
        System.out.println(reportHandler.generateProductionCreditsCount(production));


    }
}

package domain.jsonReporting;

import Interfaces.IPersistenceFacade;
import Interfaces.IReportHandler;
import data.PersistenceFacade;
import data.credits.Production;

public class TestReportingAPI {
    public static void main(String[] args) {
        IReportHandler reportHandler = ReportingHandler.getInstance();

        System.out.println("------------------------------------------------");
        System.out.println("Total number of credits in the Database");
        System.out.println(reportHandler.getTotalCreditCount());
        System.out.println("------------------------------------------------\n");

        System.out.println("------------------------------------------------");
        System.out.println("Total number of credits for the production parsed (this should be implemented where you chose an excisting production)");
        Production production = new Production(1,null,"badehotellet",null,0,null, null,null,null);
        System.out.println(reportHandler.generateProductionCreditsCount(production));
        System.out.println("------------------------------------------------\n");

        System.out.println("------------------------------------------------");
        System.out.println("Total number of credits for each category");
        System.out.println(reportHandler.generateCreditTypeCount());
        System.out.println("------------------------------------------------\n");

        System.out.println("------------------------------------------------");
        System.out.println("The 10 most credited people in the system");
        System.out.println(reportHandler.generate10MostCredited());
        System.out.println("------------------------------------------------\n");

        System.out.println("------------------------------------------------");
        System.out.println("All programs and all their credits");
        System.out.println(reportHandler.generateCreditsReport());
        System.out.println("------------------------------------------------");

    }
}

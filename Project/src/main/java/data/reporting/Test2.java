package data.reporting;

import data.credits.Production;

public class Test2 {
    public static void main(String[] args) {
        ReportHandler reportHandler = new ReportHandler();
        Production production = new Production(1,null,null,null,0,null,null,null);
        System.out.println(reportHandler.generateProductionCreditsCount(production));
    }
}

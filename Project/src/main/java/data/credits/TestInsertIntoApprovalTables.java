package data.credits;

import Interfaces.IProducer;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.DatabaseConnection;
import data.userHandling.Producer;
import data.userHandling.SystemAdministrator;
import data.userHandling.UserFacade;
import domain.DomainFacade;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import presentation.NewProduction;
import presentation.userManage.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestInsertIntoApprovalTables {
    public static void main(String[] args) {

        Connection connection = DatabaseConnection.getConnection();
        try {
            connection.setAutoCommit(false);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Add a production (to approval)

        /*
        Map<IRightsholder, List<String>> map = new HashMap<>();
        List<String> roles1 = new ArrayList<>();
        List<String> roles2 = new ArrayList<>();
        List<String> roles3 = new ArrayList<>();
        roles1.add("Medvirkende: Hovedroller");
        map.put(RightsHolderHandler.getInstance().getRightsholder(10), roles1);
        roles2.add("Medvirkende: Birollen");
        map.put(RightsHolderHandler.getInstance().getRightsholder(15), roles2);
        roles3.add("Casting");
        map.put(RightsHolderHandler.getInstance().getRightsholder(20), roles3);
        Producer producer = (Producer) DomainFacade.getInstance().getUser(new User("badehotellet", "badehotellet123"));
        NewProduction production = new NewProduction(
                "IDTESTADD",
                "This is a newly added production",
                "This tests adding a new production",
                2021,
                ProductionGenre.BØRNEFILM,
                ProductionType.LIVSSTIL,
                producer,
                map);
        FacadeData.getInstance().saveProduction(production);

        printTest();
        */

        printTest();

        //Change a production
        IProduction prod = FacadeData.getInstance().getProductions().get(0);
        prod.setProductionID("IDCHANGEDTEST");
        prod.setName("This is now a changed production");
        prod.setDescription("This production has been changed");
        ProductionHandler.getInstance().saveProduction(prod);

        //Approve a change

        IProduction toApprove = FacadeData.getInstance().getMyProductions(DomainFacade.getInstance().getUser(new User("badehotellet", "badehotellet123"))).get(2);
        ProductionHandler.getInstance().approveChangesToProduction(toApprove);


        printTest();




        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private static void printTest() {
        System.out.println("-----NORMAL PRODUCTIONS-----");
        System.out.println(FacadeData.getInstance().getProductions());
        System.out.println("-----APPROVAL PRODUCTION-----");
        System.out.println(FacadeData.getInstance().getMyProductions(DomainFacade.getInstance().getUser(new User("badehotellet", "badehotellet123"))));

    }
}

package data.credits;

import Interfaces.IProducer;
import Interfaces.IRightsholder;
import data.userHandling.Producer;
import data.userHandling.UserFacade;
import domain.DomainFacade;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import presentation.NewProduction;
import presentation.userManage.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestInsertIntoApprovalTables {
    public static void main(String[] args) {
        Map<IRightsholder, List<String>> map = new HashMap<>();
        List<String> roles1 = new ArrayList<>();
        List<String> roles2 = new ArrayList<>();
        List<String> roles3 = new ArrayList<>();
        roles1.add("Medvirkende: Moses");
        map.put(RightsHolderHandler.getInstance().getRightsholder(4), roles1);
        roles2.add("Medvirkende: Jesus");
        map.put(RightsHolderHandler.getInstance().getRightsholder(6), roles2);
        roles3.add("Casting");
        map.put(RightsHolderHandler.getInstance().getRightsholder(8), roles3);
        Producer producer = (Producer) DomainFacade.getInstance().getUser(new User("badehotellet", "badehotellet123"));
        NewProduction production = new NewProduction(
                "ID1234",
                "ApprovalTest3",
                "Tests for testing the getProduction for approval",
                2021,
                ProductionGenre.DRAMA,
                ProductionType.COMEDY,
                producer,
                map);
        FacadeData.getInstance().saveProduction(production);
    }
}

package data.credits;

import Interfaces.IRightsholder;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import presentation.NewProduction;

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
        map.put(RightsHolderHandler.getInstance().getRightsholder(3), roles1);
        roles2.add("Medvirkende: Jesus");
        map.put(RightsHolderHandler.getInstance().getRightsholder(5), roles2);
        roles3.add("Casting");
        map.put(RightsHolderHandler.getInstance().getRightsholder(7), roles3);
        NewProduction production = new NewProduction("ID5432", "ApprovalTest", "Tests approvals", 2021, ProductionGenre.DRAMA, ProductionType.COMEDY, map);
        FacadeData.getInstance().saveProduction(production);
    }
}

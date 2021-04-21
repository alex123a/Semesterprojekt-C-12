package data;

import Interfaces.IRightsholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        // Test of ProductionHandler
        RightsHolderHandler rh = RightsHolderHandler.getInstance();
        Map<IRightsholder, List<String>> rightsholder;
        rightsholder = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("Kamera");
        String[] productions = {"Star wars", "Sejt"};
        rightsholder.put(new Rightsholder(1,"Simon","jonnn",productions),list);
        ProductionHandler ph = ProductionHandler.getInstance();
        ph.saveProduction(new Production("122r22t3","Janisdwa",rightsholder));
        System.out.println(ph.readProduction("122r22t3").toString());



        // Test of RightsHolderHandler

        String[] s = {"Skuespiller","Filmstjerne"};
        rh.saveRightsholder(new Rightsholder(3,"Sim","Jeg er 24 år gammel og meget sej",s));
        System.out.println(rh.readRightsholder(3));

    }
}

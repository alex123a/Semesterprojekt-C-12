package data;

import Interfaces.IRightsholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<IRightsholder, List<String>> rightsholder;
        rightsholder = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("Kamera");
        String[] productions = {"Star wars", "Sejt"};
        rightsholder.put(new Rightsholder(1,"bo","adwwa", productions),list);
        ProductionHandler ph = ProductionHandler.getInstance();
        ph.saveProduction(new Production("122r22t3","Janisdwa",rightsholder));
    }
}

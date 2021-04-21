package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private String productionID;
    private String name;
    private Map<IRightsholder, List<String>> rightsholder;
    private static int idCounter = 1;


    public Production() {

    }

    public Production(String productionID, String name, Map<IRightsholder,List<String>> rightsholder) {
        this.productionID = productionID;
        this.name = name;
        this.rightsholder = rightsholder;
    }

    @Override
    public String getProductionID() {
        return this.productionID;
    }

    public void setProductionID(String productionID) {
        this.productionID = productionID;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<IRightsholder, List<String>> getRightsholders() {
        return rightsholder;
    }

    @Override
    public List<String> getRightsholderRole(IRightsholder rightsholder) {
        return this.rightsholder.get(rightsholder);
    }

    public void setRightsholder(Map<IRightsholder, List<String>> rightsholder) {
        this.rightsholder = rightsholder;
    }

    // Creating a map with ID integer as key instead of the whole rightholder object.
    public Map<Integer, List<String>> convertRHToIDs() {
        Map<Integer, List<String>> newMap = new HashMap<>();
        for (IRightsholder rh : rightsholder.keySet()) {
            // If it comes from presentationlayer it does not own an ID, so we need to create a new object with an ID, so it match over database.
            IRightsholder dataRh;
            if (!(rh instanceof Rightsholder)) {
                dataRh = giveIdRightsholder(rh);
            } else {
                dataRh = rh;
            }
            int theId = ((Rightsholder) dataRh).getId();
            newMap.put(theId, rightsholder.get(rh));
        }
        return newMap;
    }

    IRightsholder giveIdRightsholder(IRightsholder rh) {
        IRightsholder rightsholder = new Rightsholder(idCounter++, rh.getName(), rh.getDescription());
        RightsHolderHandler rhandler = RightsHolderHandler.getInstance();
        rhandler.saveRightsholder(rightsholder);
        return rightsholder;
    }

    public String mapToString() {
        Map<Integer, List<String>> map = convertRHToIDs();
        String rightholderString = "";
        for (Integer rh: map.keySet()) {
            rightholderString = rightholderString + "" + rh + ":[";

            for (int i = 0; i < map.get(rh).size(); i++) {
                rightholderString = rightholderString + map.get(rh).get(i);
                if (i < map.get(rh).size() - 1) {
                    rightholderString = rightholderString + ",";
                }
            }

            rightholderString = rightholderString + "]";
        }
        return rightholderString;
    }

    @Override
    public String toString() {
        return productionID + ": " + name;
    }


}

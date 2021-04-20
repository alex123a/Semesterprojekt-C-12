package data;

import domain.CreditsManagement.Production;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProductionHandler implements IProductionHandler {
    @Override
    public ArrayList<Production> readPFile() {
        return null;
    }

    @Override
    public Production readPFile(String id) {
        return null;
    }

    @Override
    public void writeToPFile(Production production) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            fileWriter.write(production.getProductionID() + "," + production.getName() + "," + production.getRightsholderRole + ",");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

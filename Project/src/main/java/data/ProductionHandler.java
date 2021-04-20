package data;

import Interfaces.IProduction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ProductionHandler implements IProductionHandler {
    private File file;

    public ProductionHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".txt").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<IProduction> readPFile() {
        return null;
    }

    @Override
    public IProduction readProduction(String id) {
        return null;
    }

    @Override
    public void saveProduction(IProduction production) {
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

package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductionHandler {
    private File file;

    public ProductionHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".txt").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IProduction> readPFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            ArrayList<IProduction> productions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                productions.add(new Production(line[0], line[1], line[2], line[3]));
            }
            return productions;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return null;
    }

    public IProduction readProduction(String id) {
        ArrayList<IProduction> rightsholders = readPFile();
        for (IProduction rh: rightsholders) {
            if (((Production) rh).getId() == id) {
                return rh;
            }
        }
        return null;
    }

    public void saveProduction(IProduction production) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            fileWriter.write(production.getProductionID() + ";" + production.getName() + ";" + production.getRightsholders() + ";" + production.getRightsholdersRoles() + "\n");
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

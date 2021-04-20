package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductionHandler {
    private File file;

    private ProductionHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".txt").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<IProduction> readPFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            ArrayList<IProduction> productions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                productions.add(new Production(line[0], line[1], convertToMap(line[2])));
            }
            return productions;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return null;
    }

    private Map<IRightsholder, List<String>> convertToMap(String rhRoles) {

        return null;
    }

    private IProduction readProduction(String id) {
        ArrayList<IProduction> productions = readPFile();
        for (IProduction p: productions) {
            if (((Production) p).getProductionID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private void saveProduction(IProduction production) {
        Scanner scanner = null;
        FileWriter fileWriter = null;
        try {
            scanner = new Scanner(this.file);
            fileWriter = new FileWriter(this.file);
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains(production.getName())) {
                    fileWriter.append("");
                } else {
                    fileWriter.append(scanner.nextLine());
                }
            }
            fileWriter.append(production.getProductionID() + ";" + production.getName() + ";" + production + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

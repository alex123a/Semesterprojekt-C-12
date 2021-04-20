package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class ProductionHandler {
    private static File file;
    private final static ProductionHandler prHandler = new ProductionHandler("productionData");

    private ProductionHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".dat").toURI().getPath());
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
                productions.add(new Production(line[0], line[1], convertToMap(line[2])));
            }
            return productions;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return null;
    }

    public Map<IRightsholder, List<String>> convertToMap(String rhRoles) {
        String[] rightholdersWithRoles = rhRoles.split("¤");
        Map<IRightsholder, List<String>> map = new HashMap<>();
        RightsHolderHandler rhandler = RightsHolderHandler.getInstance();
        for (String rhRole : rightholdersWithRoles) {
            String[] splitted = rhRole.split(":");
            List<String> roles = new ArrayList<>(Arrays.asList(splitted[1].replace("[", "").replace("]", "").split(",")));
            map.put(rhandler.readRightsholder(Integer.parseInt(splitted[0])), roles);
        }
        return map;
    }

    public IProduction readProduction(String id) {
        ArrayList<IProduction> productions = readPFile();
        for (IProduction p : productions) {
            if (((Production) p).getProductionID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void saveProduction(IProduction production) {
        ArrayList<IProduction> readings = readPFile();
        boolean contains = false;
        for (int i = 0; i < readings.size(); i++) {
            if (readings.get(i).getProductionID().equals(production.getProductionID())) {
                contains = true;
                readings.remove(i);
            }
        }
        if (contains) {
            readings.add(production);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(this.file);
                fileWriter.append(readings.get(0).getProductionID() + ";" + readings.get(0).getName() + ";" + ((Production) readings.get(0)).mapToString());
                for (int i = 1; i < readings.size(); i++) {
                    fileWriter.append("\n" + readings.get(i).getProductionID() + ";" + readings.get(i).getName() + ";" + ((Production) readings.get(i)).mapToString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(this.file, true);
                if (readings.size() == 0) {
                    fileWriter.write(production.getProductionID() + ";" + production.getName() + ";" + ((Production) production).mapToString());
                } else {
                    fileWriter.write("\n" + production.getProductionID() + ";" + production.getName() + ";" + ((Production) production).mapToString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ProductionHandler getInstance() {
        return prHandler;
    }

    public String read() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            System.out.println(this.file.getAbsolutePath());
            return scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

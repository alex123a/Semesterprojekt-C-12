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
            scanner.close();
        }
        return null;
    }

    public Map<IRightsholder, List<String>> convertToMap(String rhRoles) {
        String[] rightholdersWithRoles = rhRoles.split("¤");
        Map<IRightsholder, List<String>> map = new HashMap<>();
        RightsHolderHandler rhandler = RightsHolderHandler.getInstance();
        for (String rhRole: rightholdersWithRoles) {
            String[] splitted = rhRole.split(":");
            List<String> roles = new ArrayList<>(Arrays.asList(splitted[2].split(",")));
            map.put(rhandler.readRightsholder(Integer.parseInt(splitted[0])), roles);
        }
        return map;
    }

    public IProduction readProduction(String id) {
        ArrayList<IProduction> productions = readPFile();
        for (IProduction p: productions) {
            if (((Production) p).getProductionID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void saveProduction(IProduction production) {
        Scanner scanner = null;
        FileWriter fileWriter = null;
        boolean exists = false;
        try {
            fileWriter = new FileWriter(this.file,true);
            scanner = new Scanner(this.file);
            while(scanner.hasNextLine()) {
                if (scanner.nextLine().contains(production.getProductionID())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                fileWriter.write(production.getProductionID() + ";" + production.getName() + ";" + ((Production) production).mapToString() + "\n");
            }
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
        if (exists) {
            try {
                List<String> list = new ArrayList<>();
                scanner = new Scanner(this.file);
                fileWriter = new FileWriter(this.file);
                fileWriter.write("");
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                for (String s: list) {
                    if (s.contains(production.getProductionID())) {
                        list.remove(s);
                    } else {
                        try {
                            fileWriter.append(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scanner.close();
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

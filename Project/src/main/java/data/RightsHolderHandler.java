package data;

import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RightsHolderHandler {
    private File file;
    // Singleton
    private static final RightsHolderHandler rhHandler = new RightsHolderHandler("rightsHolderData");

    private RightsHolderHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".dat").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Return all rightholders
    List<IRightsholder> readRHFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            List<IRightsholder> rightsholder = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                rightsholder.add(new Rightsholder(Integer.parseInt(line[0]), line[1], line[2], convertToProduction(line[2])));
            }
            return rightsholder;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return null;
    }

    // Return one rightholder with the use of id
    IRightsholder readRightsholder(int id) {
        List<IRightsholder> rightsholders = readRHFile();
        for (IRightsholder rh: rightsholders) {
            if (((Rightsholder) rh).getId() == id) {
                return rh;
            }
        }
        return null;
    }

    // Insert rightsholder or edit rightsholder
    void saveRightsholder(IRightsholder rightsholder) {
        List<IRightsholder> readings = readRHFile();
        int oldID = 0;
        boolean contains = false;
        for (int i = 0; i < readings.size(); i++) {
            if (((Rightsholder) readings.get(i)).getId() == ((Rightsholder) rightsholder).getId()) {
                contains = true;
                oldID = ((Rightsholder) readings.get(i)).getId();
                readings.remove(i);
                break;
            }
        }
        if (contains) {
            ((Rightsholder) rightsholder).setId(oldID);
            readings.add(rightsholder);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(this.file);
                fileWriter.append(((Rightsholder) readings.get(0)).getId() + ";" + readings.get(0).getName() + ";" + readings.get(0).getDescription() + ";" + ((Rightsholder) readings.get(0)).listToString());
                for (int i = 1; i < readings.size(); i++) {
                    fileWriter.append("\n" + ((Rightsholder)readings.get(i)).getId() + ";" + readings.get(i).getName() + ";" + readings.get(i).getDescription() + ";" + ((Rightsholder) readings.get(i)).listToString());
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
                    fileWriter.write(((Rightsholder) rightsholder).getId() + ";" + rightsholder.getName() + ";" + rightsholder.getDescription() + ";" + ((Rightsholder) rightsholder).listToString());
                } else {
                    fileWriter.write("\n" + ((Rightsholder)rightsholder).getId() + ";" + rightsholder.getName() + ";" + rightsholder.getDescription() + ";" + ((Rightsholder) rightsholder).listToString());
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

    // Converts string from file to a string away with the productions.
    String[] convertToProduction(String stringProduction) {
        String[] productionArray = stringProduction.replace("[","").replace("]", "").split(",");
        return productionArray;
    }

    static RightsHolderHandler getInstance() {
        return rhHandler;
    }

    @Override
     public String toString() {
        return "The name of the file is: " + this.file.getName();
    }
}

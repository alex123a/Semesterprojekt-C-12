package data;

import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class RightsHolderHandler {
    private File file;
    public static final RightsHolderHandler rhHandler = new RightsHolderHandler("rightsHolderData");

    private RightsHolderHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".txt").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<IRightsholder> readRHFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            ArrayList<IRightsholder> rightsholder = new ArrayList<>();
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

    private IRightsholder readRightsholder(int id) {
        ArrayList<IRightsholder> rightsholders = readRHFile();
        for (IRightsholder rh: rightsholders) {
            if (((Rightsholder) rh).getId() == id) {
                return rh;
            }
        }
        return null;
    }

    private void saveRightsholder(IRightsholder rightsholder) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            fileWriter.write(rightsholder.getName() + ";" + rightsholder.getDescription() + ";" + rightsholder.getRightsholderFor() + "\n");
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

    private String[] convertToProduction(String stringProduction) {
        String[] productionArray = stringProduction.replace("[","").replace("]", "").split(",");
        return productionArray;

    }

    @Override
    public String toString() {
        return "The name of the file is: " + this.file.getName();
    }
}

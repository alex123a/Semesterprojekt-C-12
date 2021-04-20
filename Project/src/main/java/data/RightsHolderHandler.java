package data;

import domain.CreditsManagement.Production;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class RightsHolderHandler implements IRightsHolderHandler {
    private File file;

    public RightsHolderHandler(String fileName) {
        try {
            this.file = new File(getClass().getResource("/" + fileName + ".txt").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<RightsHolder> readRHFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.file);
            ArrayList<RightsHolder> rightsholder = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                rightsholder.add(new RightsHolder(line[0], line[1], line[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return null;
    }
    @Override
    public RightsHolder readRHFile(String id) {
        return null;
    }

    @Override
    public void writeToRHFile(RightsHolder rightsHolder) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            fileWriter.write(rightsHolder.getName() + "," + rightsHolder.getDescription() + "," + rightsHolder.getRightsHolderFor.toString() + ",");
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

    public ArrayList<Production> convertToProduction(String stringProduction) {
        String[] productionArray = stringProduction.replace("[","").replace("]", "").split(",");

    }

    @Override
    public String toString() {
        return "The name of the file is: " + this.file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

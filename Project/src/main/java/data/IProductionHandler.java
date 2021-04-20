package data;

import domain.CreditsManagement.Production;

import java.util.ArrayList;

public interface IProductionHandler {
    ArrayList<Production> readPFile();
    Production readPFile(String id);
    void writeToPFile(Production production);
}

package data;

import Interfaces.IProduction;

import java.util.ArrayList;

public interface IProductionHandler {
    ArrayList<IProduction> readPFile();
    IProduction readProduction(String id);
    void saveProduction(IProduction production);
}

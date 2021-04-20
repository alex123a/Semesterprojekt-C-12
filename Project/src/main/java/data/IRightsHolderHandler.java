package data;

import Interfaces.IRightsholder;

import java.util.ArrayList;

public interface IRightsHolderHandler {
    ArrayList<IRightsholder> readRHFile();
    IRightsholder readRightsholder(String id);
    void saveRightsholder(IRightsholder rightsholder);
}

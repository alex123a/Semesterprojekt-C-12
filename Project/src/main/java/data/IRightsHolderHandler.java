package data;

import java.util.ArrayList;

public interface IRightsHolderHandler {
    ArrayList<RightsHolder> readRHFile();
    RightsHolder readRHFile(String id);
    void writeToRHFile(RightsHolder rightsholder);
}

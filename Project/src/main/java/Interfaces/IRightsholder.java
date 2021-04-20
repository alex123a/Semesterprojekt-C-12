package Interfaces;

import java.util.List;

public interface IRightsholder {
    int getId();
    String getName();
    String getDescription();
    List<IProduction> getRightsholderFor();
}

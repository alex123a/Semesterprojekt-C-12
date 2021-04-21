package Interfaces;

import java.util.List;

public interface IRightsholder {
    String getName();
    String getDescription();
    List<IProduction> getRightsholderFor();
}

package Interfaces;

import java.util.List;

public interface IRightsholder {
    String getFirstName();
    String getLastName();
    String getDescription();
    List<IProduction> getRightsholderFor();
}

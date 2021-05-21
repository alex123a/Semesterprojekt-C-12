package Interfaces;

import java.util.List;

public interface IRightsholder extends ISearchable {
    String getFirstName();
    String getLastName();
    String getDescription();
    List<IProduction> getRightsholderFor();
}

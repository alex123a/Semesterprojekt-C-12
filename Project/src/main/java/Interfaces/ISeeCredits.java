package Interfaces;

import java.util.List;

public interface ISeeCredits {
    List<IProduction> getProductions();
    IProduction getProduction(String id);
}

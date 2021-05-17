package Interfaces;

import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;

import java.util.List;

public interface ISearchCredits {

    List<?> findMatch(List<ISearchable> list,String target);

    List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type);

    List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target);

    List<?> filterProduction(List<?> list, int[] yearInterval, ProductionGenre genre, ProductionType type);

}

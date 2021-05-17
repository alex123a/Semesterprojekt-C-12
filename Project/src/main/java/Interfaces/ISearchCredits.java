package Interfaces;

import java.util.List;

public interface ISearchCredits {

    List<?> findMatch(List<?> list,String target);

    List<IRightsholder> sortPersonBy(List<IRightsholder> list, CreditSorting type);

    List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target);

    List<?> filterProduction(List<?> list, int[2] year, ProductionGenre genre, ProductionType type);

}

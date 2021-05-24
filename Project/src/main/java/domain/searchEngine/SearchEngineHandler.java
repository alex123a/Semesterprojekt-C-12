package domain.searchEngine;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISearchCredits;
import Interfaces.ISearchable;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;

import java.util.*;

public class SearchEngineHandler implements ISearchCredits {
    private static final SearchEngineHandler instance = new SearchEngineHandler();

    private SearchEngineHandler() {

    }

    public static SearchEngineHandler getInstance() {
        return instance;
    }

    @Override
    public List<ISearchable> findMatch(List<ISearchable> list, String target) {

        List<ISearchable> results = new ArrayList<>();
        for (ISearchable ob : list){
            if (ob.getName().toLowerCase().contains(target.toLowerCase())){
                results.add(ob);
            }
        }
        //finder en liste af ting der overholder target regex %target%
        return results;
    }

    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {
        //navn og efternavn

        if(type == RightholderSorting.FIRST_NAME){
        list.sort((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
        return list;
        }

        if(type == RightholderSorting.FIRST_NAME_REVERSE){
            list.sort((o1, o2) -> o2.getFirstName().compareTo(o1.getFirstName()));
            return list;
        }

        if(type == RightholderSorting.LAST_NAME){
          list.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
            return list;
        }

        if(type == RightholderSorting.LAST_NAME_REVERSE){
            list.sort((o1, o2) -> o2.getLastName().compareTo(o1.getLastName()));
            return list;
        }

        return null;
    }

    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        //navn og årstal
        if(target == ProductionSorting.NAME){
            list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
            return list;
        }

        if(target == ProductionSorting.NAME_REVERSE){
            list.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            return list;

        }

        if(target == ProductionSorting.YEAR){
            list.sort((o1, o2) -> o2.getYear() - o1.getYear());
            return list;

        }

        if(target == ProductionSorting.YEAR_REVERSE){
            list.sort((o1, o2) -> o1.getYear() - o2.getYear());
            return list;
        }

        return null;
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        //årstal, genre og type

        if(yearInterval != null){

            //Checks which given year is the lowest, and assigns it to a corresponding value
            int lowestYr, highestYr;
            if (yearInterval[0] < yearInterval[1]){
                lowestYr = yearInterval[0];
                highestYr = yearInterval[1];
            } else {
                lowestYr = yearInterval[1];
                highestYr = yearInterval[0];
            }

            //for every production, check if its year is inside the range
            Iterator<IProduction> iter = list.iterator();

            while (iter.hasNext()) {
                IProduction prod = iter.next();
                int year = prod.getYear();

                    if (year < lowestYr || year > highestYr) {
                        iter.remove();
                    }
                }
            }


        if(genre != null){
           list.removeIf(prod -> prod.getGenre() != genre);
        }
        if(type != null){
            list.removeIf(prod -> prod.getType() != type);
        }

        return list;
    }
}

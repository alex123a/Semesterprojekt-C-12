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

    //singleton
    private static final SearchEngineHandler instance = new SearchEngineHandler();

    private SearchEngineHandler() {

    }

    public static SearchEngineHandler getInstance() {
        return instance;
    }

    //Method for searching a list of searchable, by finding where a name contains searchword.
    @Override
    public List<ISearchable> findMatch(List<ISearchable> list, String target) {

        List<ISearchable> results = new ArrayList<>();
        for (ISearchable ob : list){
            if (ob.getName().toLowerCase().contains(target.toLowerCase())){
                results.add(ob);
            }
        }
        return results;
    }

    //uses comparators to sort rightsholders by firstname or lastname,
    // either in reverse or not
    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {
        //Name and lastname
        //sets strings to lowercase and trims them to get a homogenous result from sorting
        if(type == RightholderSorting.FIRST_NAME){
        list.sort((o1, o2) -> o1.getFirstName().toLowerCase().trim().compareTo(o2.getFirstName().toLowerCase().trim()));
        return list;
        }

        if(type == RightholderSorting.FIRST_NAME_REVERSE){
            list.sort((o1, o2) -> o2.getFirstName().toLowerCase().trim().compareTo(o1.getFirstName().toLowerCase().trim()));
            return list;
        }

        if(type == RightholderSorting.LAST_NAME){
          list.sort((o1, o2) -> o1.getLastName().toLowerCase().trim().compareTo(o2.getLastName().toLowerCase().trim()));
            return list;
        }

        if(type == RightholderSorting.LAST_NAME_REVERSE){
            list.sort((o1, o2) -> o2.getLastName().toLowerCase().trim().compareTo(o1.getLastName().toLowerCase().trim()));
            return list;
        }

        return null;
    }

    //uses comparators to sort productions by year or name,
    // either in reverse or not
    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        //name and year
        if(target == ProductionSorting.NAME){
            list.sort((o1, o2) -> o1.getName().toLowerCase().trim().compareTo(o2.getName().toLowerCase().trim()));
            return list;
        }

        if(target == ProductionSorting.NAME_REVERSE){
            list.sort((o1, o2) -> o2.getName().toLowerCase().trim().compareTo(o1.getName().toLowerCase().trim()));
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

    //filters productions, to remove items not corresponding to constraints
    // can filter by range of years, genre and type
    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {

        //if yearinterval is filled
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
            //do it through and iterator, as this is the only way that works lol
            Iterator<IProduction> iter = list.iterator();
            //for every item in list
            while (iter.hasNext()) {
                IProduction prod = iter.next();
                int year = prod.getYear();
                    //if year is outside range, remove item
                    if (year < lowestYr || year > highestYr) {
                        iter.remove();
                    }
                }
            }

        //if genre is set
        if(genre != null){
            //remove item if it does not correspond to chosen genre
           list.removeIf(prod -> prod.getGenre() != genre);
        }
        //if type is set
        if(type != null){
            //remove item if it does not correspond to chosen type
            list.removeIf(prod -> prod.getType() != type);
        }

        return list;
    }
}

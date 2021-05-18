package domain.searchEngine;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISearchable;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchEngineHandler implements Interfaces.ISearchCredits {
    @Override
    public List<ISearchable> findMatch(List<ISearchable> list, String target) {

        List<ISearchable> results = new ArrayList<>();
        for (ISearchable ob : list){
            if (ob.getName().contains(target)){
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
        list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        return list;
        }

        if(type == RightholderSorting.FIRST_NAME_REVERSE){
            list.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            return list;
        }

        if(type == RightholderSorting.LAST_NAME){
        //list.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
            return list;
        }
//todo rightsholder get lastname
        if(type == RightholderSorting.LAST_NAME_REVERSE){
          //  list.sort((o1, o2) -> o2.getLastName().compareTo(o1.getLastName()));
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
//todo getyear
        if(target == ProductionSorting.YEAR){
           // list.sort((o1, o2) -> o2.getYear().compareTo(o1.getName()));
            return list;

        }

        if(target == ProductionSorting.YEAR_REVERSE){
//todo implement reverse sort
        }

        return null;
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        //årstal, genre og type
        if(yearInterval != null){
            for (IProduction prod : list){
                int year = 9999; //prod.getYear(); //todo get actual year
                if (year < yearInterval[0] || year > yearInterval[1]){
                    list.remove(prod);
                }
            }
        }

        //todo implement when prod is implemented
        if(genre != null){
        //    list.removeIf(prod -> prod.getGenre != genre);
        }
        if(type != null){
        //    list.removeIf(prod -> prod.getType != type);
        }

        return list;
    }
}

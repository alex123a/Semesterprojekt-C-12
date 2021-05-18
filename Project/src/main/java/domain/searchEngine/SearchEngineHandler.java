package domain.searchEngine;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISearchable;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchEngineHandler implements Interfaces.ISearchCredits {
    @Override
    public List<?> findMatch(List<ISearchable> list, String target) {
        //finder en liste af ting der overholder target regex %target%
        return null;
    }

    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {



        //if(type == NAME){
        list.sort(new Comparator<IRightsholder>() {
            @Override
            public int compare(IRightsholder o1, IRightsholder o2) {
                return o2.getName().compareTo(o2.getName());
            }
        });
        return list;
        //}if(type == LASTNAME){
        list.sort(new Comparator<IRightsholder>() {
            @Override
            public int compare(IRightsholder o1, IRightsholder o2) {
                return o2.getLastName().compareTo(o2.getName());
            }
        });
        //}

        //navn og efternavn
        return null;
    }

    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        //navn og årstal
        return null;
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        //årstal, genre og type
        if(yearInterval != null){
            for (IProduction prod : list){
                int year = 9999; //prod.getYear();
                if (year < yearInterval[0] || year > yearInterval[1]){
                    list.remove(prod);
                }
            }
        }
        if(genre != null){
            list.removeIf(prod -> prod.getGenre != genre);
        }
        return null;
    }
}

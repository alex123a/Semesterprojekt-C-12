package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.credits.Production;
import data.credits.ProductionHandler;
import data.credits.RightsHolderHandler;
import data.credits.Rightsholder;
import presentation.userManage.Producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Test {

    public static void main(String[] args) {
/*
        List<IProduction> productionList = ProductionHandler.getInstance().getProductions();
        System.out.println(productionList);

        IProduction production = ProductionHandler.getInstance().getProduction(1);
        System.out.println(production);

        List<IRightsholder> rightholderList = RightsHolderHandler.getInstance().getRightsholders();
        System.out.println(rightholderList);

        IRightsholder rightsholder = RightsHolderHandler.getInstance().getRightsholder(2);
        System.out.println(rightsholder.toString());

        List<Integer> f = new ArrayList<>();
        f.add(1);
       // RightsHolderHandler.getInstance().saveRightsholder(new Rightsholder(7,"Hans","Hansen","Yoghurt", f));

        RightsHolderHandler.getInstance().approveChangesToRightsholder(new Rightsholder(7,"Hans","Hansen","jj", f));


 */
        System.out.println(ProductionHandler.getInstance().getProductionsChanged(new Producer("john","john123")));
        List<Production> production = ProductionHandler.getInstance().getProductionsChanged(new Producer("john","john123"));
        System.out.println(production.get(0).getRightsholders());
    }

}

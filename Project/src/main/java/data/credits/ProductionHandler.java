package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.DatabaseConnection;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import jdk.jshell.spi.ExecutionControl;

import java.awt.color.ICC_ProfileRGB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

class ProductionHandler {
    private Connection connection = null;

    // Singleton
    private final static ProductionHandler prHandler = new ProductionHandler();

    private ProductionHandler() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Reads the whole productionData file and returns all productions in an arraylist;
    List<IProduction> getProductions() {
        List<IProduction> productionList = new ArrayList<>();
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id " +
                    "FROM production");
            ResultSet productionsResult = productionsStatement.executeQuery();

            //For each production get the rightsholders and their roles
            while (productionsResult.next()) {
                //Statement to get all rightsholders on each production
                Production p = getProductionFromResultset(productionsResult);
                productionList.add(p);
            }
            return productionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Reads specific production with productionID
    IProduction getProduction(int id) {
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id " +
                    "FROM production " +
                    "WHERE id = ?;");
            productionsStatement.setInt(1, id);
            ResultSet productionsResult = productionsStatement.executeQuery();

            productionsResult.next();
            //For each production get the rightsholders and their roles
            Production p = getProductionFromResultset(productionsResult);

            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    IProduction saveProduction(IProduction production){
        //TODO insert Producer id
        //TODO All the statement.set() calls could be refactored

        PreparedStatement insertStatement = null;

        if (production instanceof Production) {
            Production p = (Production) production;

            //Run this if the production is already in the database
            try {
                //Check whether the production is already in production_approval
                PreparedStatement getProductionStatement = connection.prepareStatement("SELECT * FROM production_approval WHERE id = ?");
                getProductionStatement.setInt(1, p.getID());
                ResultSet result = getProductionStatement.executeQuery();

                if (result.next()) {
                    //TODO hasn't been tested
                    //Run this if the production is already in the approval table
                    insertStatement = connection.prepareStatement("" +
                            "UPDATE production_approval " +
                            "SET own_production_id=?, production_name=?, year=?, genre_id=?, category_id=?" +
                            "WHERE id = ?" +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                    insertStatement.setString(1, p.getProductionID());
                    insertStatement.setString(2, p.getName());
                    insertStatement.setInt(3, p.getYear());
                    insertStatement.setInt(4, p.getGenre().getId());
                    insertStatement.setInt(5, p.getType().getId());
                    insertStatement.setInt(6, p.getID());
                } else {

                    insertStatement = connection.prepareStatement("" +
                            "INSERT INTO production_approval (id, own_production_id, production_name, year, genre_id, category_id, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                    insertStatement.setInt(1, p.getID());
                    insertStatement.setString(2, p.getProductionID());
                    insertStatement.setString(3, p.getName());
                    insertStatement.setInt(4, p.getYear());
                    insertStatement.setInt(5, p.getGenre().getId());
                    insertStatement.setInt(6, p.getType().getId());
                    insertStatement.setString(7, p.getDescription());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            //Run this if the production is a new production
            try {

                //TODO THE HARDCODED VALUE FOR PRODUCER_ID IS FOR TESTING PURPOSE AND SHOULD BE CHANGED
                insertStatement = connection.prepareStatement("" +
                        "INSERT INTO production_approval (own_production_id, production_name, year, genre_id, category_id, producer_id, description) " +
                        "VALUES (?, ?, ?, ?, ?, 2, ?) " +
                        "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                insertStatement.setString(1, production.getProductionID());
                insertStatement.setString(2, production.getName());
                insertStatement.setInt(3, production.getYear());
                insertStatement.setInt(4,production.getGenre().getId());
                insertStatement.setInt(5, production.getType().getId());
                insertStatement.setString(6, production.getDescription());

                Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
                //TODO SAVE RIGHTSHOLDERS
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



        Production toReturn = null;

        try {
            ResultSet resultProduction = insertStatement.executeQuery();
            resultProduction.next();
            toReturn = getProductionFromResultset(resultProduction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        int productionID = toReturn.getID();

        // Following block adds appears_in if they are in the production but not in the DB
        Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
        for (IRightsholder rightsholder: rightsholders.keySet()) {
            //This line saves the rightsholder and gets the rightsholder with the ID back
            Rightsholder r = (Rightsholder) RightsHolderHandler.getInstance().saveRightsholder(rightsholder);

            try {
                int rightsholderID = r.getId();
                //Checks if the appears_in is already in the table
                PreparedStatement checkExistsStatement = connection.prepareStatement("" +
                        "SELECT * FROM appears_in " +
                        "WHERE rightsholder_id=?" +
                        "AND production_id=?");
                checkExistsStatement.setInt(1, rightsholderID);
                checkExistsStatement.setInt(2, productionID);
                ResultSet existing = checkExistsStatement.executeQuery();
                if (!existing.next()) {// the appears_in is not in the table and thus should be inserted
                    PreparedStatement insertAppears_inStatement = connection.prepareStatement("" +
                            "INSERT INTO appears_in_approval (production_id, rightsholder_id) " +
                            "VALUES (?, ?)" +
                            "RETURNING id");
                    insertAppears_inStatement.setInt(1, productionID);
                    insertAppears_inStatement.setInt(2, rightsholderID);
                    ResultSet appears_in_id_result = insertAppears_inStatement.executeQuery();
                    appears_in_id_result.next();
                    int appearsinID = appears_in_id_result.getInt(1);

                    //Inserts into roleapproval

                    for (String role: rightsholders.get(rightsholder)){

                        //The role string might consist of e.g. "medvirkende: darth vader" therefore has to be split
                        String title;
                        if (role.contains(": ")) {
                            title = role.split(": ")[0];
                        }else {
                            title = role;
                        }

                        PreparedStatement getTitleIdStatement = connection.prepareStatement("" +
                                "SELECT id " +
                                "FROM title " +
                                "WHERE title.title = ?");
                        getTitleIdStatement.setString(1, title);
                        ResultSet titleIdResultSet = getTitleIdStatement.executeQuery();
                        titleIdResultSet.next();

                        PreparedStatement insertRoleStatement = connection.prepareStatement("" +
                                "INSERT INTO role_approval (appears_in_id, title_id) " +
                                "VALUES (?, ?)" +
                                "RETURNING id");

                        insertRoleStatement.setInt(1, appearsinID);
                        insertRoleStatement.setInt(2, titleIdResultSet.getInt(1));
                        ResultSet roleIdResult = insertRoleStatement.executeQuery();
                        roleIdResult.next();
                        if (role.contains(": ")) {
                            PreparedStatement insertRoleNameStatement = connection.prepareStatement("" +
                                    "INSERT INTO rolename_approval (role_id, rolename)" +
                                    "VALUES (?, ?)");
                            insertRoleNameStatement.setInt(1, roleIdResult.getInt(1));
                            insertRoleNameStatement.setString(2, role.split(": ")[1]);
                            insertRoleNameStatement.execute();
                        }

                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // the following block removes the appears_in if they are in the DB but not in the production
        // meaning it creates a row in appears_in_approval with the id from appears_in row an null values
        try {
            PreparedStatement getAllAppearsinStatement = connection.prepareStatement("" +
                    "SELECT rightsholder_id, id " +
                    "FROM appears_in " +
                    "WHERE production_id=?");
            getAllAppearsinStatement.setInt(1, productionID);
            ResultSet allAppearsinResult = getAllAppearsinStatement.executeQuery();

            //gets all the ids of the rightsholders in the production
            //so they can be compared to the ids in the database
            List<Integer> rightsholderIDs = new ArrayList<>();
            for (IRightsholder r: rightsholders.keySet()) {
                if (r instanceof Rightsholder) {
                    rightsholderIDs.add(((Rightsholder)r).getId());
                }
            }
            while (allAppearsinResult.next()){
                if (!rightsholderIDs.contains(allAppearsinResult.getInt(1))){
                    PreparedStatement insertToBeDeleted = connection.prepareStatement("" +
                            "INSERT INTO appears_in_approval (id)" +
                            "VALUES (?)");
                    insertToBeDeleted.setInt(1, allAppearsinResult.getInt(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    //Inserts a row with id and a lot of null-values, which then waits for approval
    boolean deleteProduction(IProduction production) {
        if (production instanceof Production) {
            Production p = (Production) production;
            int id = p.getID();
            try {
                PreparedStatement statement = connection.prepareStatement("" +
                        "INSERT INTO production_approval (id) VALUES (?)");
                statement.setInt(1, id);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return true;
        } else {
            throw new IllegalArgumentException("Can't delete an IProduction, that is not a Production");
        }
    }

    private Production getProductionFromResultset(ResultSet productionsResult) throws SQLException {
        PreparedStatement RightsholdersStatement = connection.prepareStatement("" +
                "SELECT DISTINCT rightsholder_id " +
                "FROM appears_in " +
                "WHERE production_id = ?");
        RightsholdersStatement.setInt(1, productionsResult.getInt(1));
        ResultSet rightsholderIDs = RightsholdersStatement.executeQuery();
        Map<Integer, List<String>> roleMap = new HashMap<>();
        while (rightsholderIDs.next()) {
            //for each rightsholder, get all the roles that rightsholder has in the production
            int id = rightsholderIDs.getInt(1);
            PreparedStatement rolesStatement = connection.prepareStatement("" +
                    "SELECT title, rolename.rolename FROM " +
                    "appears_in LEFT JOIN role ON appears_in.id = role.appears_in_id " +
                    "LEFT JOIN title ON role.title_id = title.id " +
                    "LEFT JOIN rolename ON role.id = rolename.role_id " +
                    "WHERE appears_in.production_id = ?" +
                    "AND appears_in.rightsholder_id = ?");
            rolesStatement.setInt(1, productionsResult.getInt(1));
            rolesStatement.setInt(2, id);
            ResultSet rolesResult = rolesStatement.executeQuery();
            List<String> rolesList = new ArrayList<>();
            while (rolesResult.next()) {
                String title = rolesResult.getString(1);
                if (title == null) {
                    rolesList.add("MISSING TITLE");
                }else if (title.equalsIgnoreCase("medvirkende")){
                    rolesList.add(title+": "+rolesResult.getString(2));
                }else {
                    rolesList.add(title);
                }
            }
            roleMap.put(id, rolesList);
        }
        //Gets the genre and type enum based on the genre and type
        ProductionGenre genre = ProductionGenre.getFromID(productionsResult.getInt(6));
        ProductionType type = ProductionType.getFromID(productionsResult.getInt(7));

        Production p = new Production(productionsResult.getInt(1), productionsResult.getString(2), productionsResult.getString(3), productionsResult.getString(4), productionsResult.getInt(5), genre, type, roleMap);
        return p;
    }

    public void approveChangesToProduction(IProduction production) {
        //todo finish implementation
        //production is always persistence production. Therefore instant typecast.
        //is used for id. Pull information out of approved table, put it into table, then delete
        Production prod = (Production) production;
        int prodID = prod.getID();
        try{
            //if id exists in table, overwrite
            //if not, create new
            //if full null, delete
            PreparedStatement prodStatement = connection.prepareStatement("SELECT 1 FROM production WHERE id = ?");
            prodStatement.setInt(1, prodID);
            ResultSet res = prodStatement.executeQuery();

            PreparedStatement approvalDeleteStatement = null;
            //if its id exists in production table
            if(res.next()){
                //overwrite
                //delete

                //if production has all values equal to null
                if (prod.equals(new Production(prodID,null,null,null,0,null,null,null))){
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM production WHERE id = ?");
                    deleteStatement.setInt(1, prodID);
                    deleteStatement.execute();
                } //if production values does not equal null, it must overwrite
                else {
                    PreparedStatement overwriteStatement = connection.prepareStatement("UPDATE production " +
                            "SET own_production_id = ?, production_name = ?, year = ?, genre_id = ?, category_id = ?, producer_id = ?, description = ? WHERE id = ?");
                    overwriteStatement.setString(1,prod.getProductionID());
                    overwriteStatement.setString(2,prod.getName());
                    overwriteStatement.setInt(3,prod.getYear());
                    overwriteStatement.setInt(4,prod.getGenre().getId());
                    overwriteStatement.setInt(5,prod.getType().getId());
                    //TODO implement get producer id in statement
                    //overwriteStatement.setInt(6,prod.getProducerId());
                    overwriteStatement.setString(7,prod.getDescription());
                    overwriteStatement.setInt(8,prodID);

                    overwriteStatement.execute();

                }

            } else{
                //insert new
                PreparedStatement newStatement = connection.prepareStatement("INSERT INTO production " +
                        "(own_production_id, production_name , year , genre_id , category_id , producer_id , description, id) " +
                        "VALUES (?, ?, ? , ? , ? , ? , ? , ?)");
                newStatement.setString(1,prod.getProductionID());
                newStatement.setString(2,prod.getName());
                newStatement.setInt(3,prod.getYear());
                newStatement.setInt(4,prod.getGenre().getId());
                newStatement.setInt(5,prod.getType().getId());
                //TODO implement get producer id in statement
                //newStatement.setInt(6,prod.getProducerId());
                newStatement.setString(7,prod.getDescription());
                newStatement.setInt(8,prodID);
            }
            //delete the production from approval table
            approvalDeleteStatement = connection.prepareStatement("DELETE FROM production_approval WHERE id = ?");
            approvalDeleteStatement.setInt(1, prodID);
            approvalDeleteStatement.execute();


            //appears in table

            Set<IRightsholder> prodRightsholdersSet = prod.getRightsholders().keySet();
            Rightsholder[] appearsR = (Rightsholder[]) prodRightsholdersSet.toArray();

            //gets rightsholder id (Where id is not null) and appears_in tuple id from appears_in table
            PreparedStatement appearsStatement = connection.prepareStatement("SELECT rightsholder_id, id FROM appears_in WHERE production_id = ?");
            appearsStatement.setInt(1, prodID);
            ResultSet appearsInProd = appearsStatement.executeQuery();

            //gets only appears_in tuple id from approval table WHERE prod and rights id are null
            PreparedStatement toBeDeletedApproveIdStatement = connection.prepareStatement("SELECT id FROM appears_in_approval WHERE production_id = ? AND production_id = null AND rightsholder_id = null ");
            toBeDeletedApproveIdStatement.setInt(1,prodID);
            ResultSet appearsInProdApproval = toBeDeletedApproveIdStatement.executeQuery();


            //maps all ids which need to be deleted to an array
            List<Integer> appearsInToBeDeleted = new ArrayList<>();
            while(appearsInProdApproval.next()){
                appearsInToBeDeleted.add(appearsInProdApproval.getInt(1));
            }

            //
            List<Integer> appearsRighstholderIDArray = new ArrayList<>();
            //array for tuple id's in regular table
            List<Integer> appearsIDArray = new ArrayList<>();
            while(appearsInProd.next()){
                appearsRighstholderIDArray.add(appearsInProd.getInt(1));
                appearsIDArray.add(appearsInProd.getInt(2));
            }

            //deletes tuples in both tables, where id is equal to an id that needs to be removed
            PreparedStatement toBeDeletedStatement = connection.prepareStatement("DELETE FROM appears_in WHERE id = ?");
            PreparedStatement toBeDeletedApproveStatement = connection.prepareStatement("DELETE FROM appears_in_approval WHERE id = ?");
            for (Integer i : appearsInToBeDeleted){
                if (appearsIDArray.contains(i)){
                    toBeDeletedStatement.setInt(1,i);
                    toBeDeletedApproveStatement.setInt(1,i);
                    //removes deleted id from id array
                    appearsIDArray.remove(i);

                    toBeDeletedStatement.addBatch();
                    toBeDeletedApproveStatement.addBatch();
                }
            }
            toBeDeletedStatement.executeBatch();
            toBeDeletedApproveStatement.executeBatch();

            //create map to get approval id from rightsholder id
            PreparedStatement appearsInApprovalIdStatment = connection.prepareStatement("SELECT rightsholder_id , id FROM appears_in_approval WHERE production_id = ?");
            appearsInApprovalIdStatment.setInt(1, prodID);
            ResultSet appearsInApprovalIdResult = appearsInApprovalIdStatment.executeQuery();
            //Map <RightsholderID, ApprovalID>
            Map<Integer, Integer> rightsholderIdMap = new HashMap<>();

            while(appearsInApprovalIdResult.next()){
                rightsholderIdMap.put(appearsInApprovalIdResult.getInt(1),appearsInApprovalIdResult.getInt(2));
            }


            //adds rightsholder id with prod id to appears in table
            PreparedStatement newAppearsStatement = connection.prepareStatement("INSERT INTO appears_in (id, production_id, rightsholder_id) VALUES (?,?,?)");
            for(Rightsholder r : appearsR){
                if (!appearsRighstholderIDArray.contains(r.getId())){
                    //add to id list

                    newAppearsStatement.setInt(1,rightsholderIdMap.get(r.getId()));
                    newAppearsStatement.setInt(2,prodID);
                    newAppearsStatement.setInt(3,r.getId());
                    newAppearsStatement.addBatch();
                }

            }
            newAppearsStatement.executeBatch();

            //deletes all data from associated with the production ID from the table
            PreparedStatement deleteFromAppearsApprovalStatment = connection.prepareStatement("DELETE FROM appears_in_approval WHERE production_id = ?");
            deleteFromAppearsApprovalStatment.setInt(1,prodID);
            deleteFromAppearsApprovalStatment.execute();

            //#######
            //role table
            //#######

            //want all tuples from role_approval from this production
            //TODO this might be unused code
            PreparedStatement roleTableStatement = connection.prepareStatement("SELECT role_approval.id, appears_in_id, title_id FROM role_approval " +
                    "RIGHT JOIN appears_in " +
                    "ON role_approval.appears_in_id = appears_in.id " +
                    "WHERE appears_in.production_id=?");
            roleTableStatement.setInt(1, prodID);
            ResultSet roleTableResult = roleTableStatement.executeQuery();

            //Delete happens through automatic cascade
            //Need to check whether approval id already exists, then overwrite, and make new if it doesn't

            //create a view with the prodRoles in the given production
            PreparedStatement roleViewStatement = connection.prepareStatement(
                    "CREATE OR REPLACE VIEW prodRoleView AS " +
                            "SELECT role_approval.id, appears_in_id, title_id FROM role_approval " +
                            "RIGHT JOIN appears_in " +
                            "ON role_approval.appears_in_id = appears_in.id " +
                            "WHERE appears_in.production_id=?");
            roleViewStatement.setInt(1, prodID);
            roleViewStatement.execute();

            ResultSet roleOverwriteResults = connection.prepareStatement(
                    "SELECT prodRoleView.id, prodRoleView.appears_in_id, prodRoleView.title_id " +
                            "FROM role " +
                            "RIGHT JOIN prodRoleView " +
                            "ON prodRoleView.id = role.id" ).executeQuery();
            /* //TODO IMPLEMENT QUERY SEEN TO WORK BELOW
            SUPPOSED TO DO THE OPPOSITE OF THE QUERY ABOVE, BUT DOES NOT WORK AS INTENTED. RETURNS 500 ROWS INSTEAD
            ResultSet roleNewResults = connection.prepareStatement(
                    "SELECT prodRoleView.id, prodRoleView.appears_in_id, prodRoleView.title_id " +
                            "FROM role " +
                            "RIGHT JOIN prodRoleView " +
                            "ON prodRoleView.id != role.id" ).executeQuery(); */

            //statements for updating the role table based on results from role_approval.
            //Then deleting the tuples used from role_approval
            PreparedStatement overwriteOnRolesStatement = connection.prepareStatement("UPDATE role SET appears_in_id = ?, title_id = ? WHERE id = ?");
            PreparedStatement deleteOverwritesFromRoleApprovalStatment = connection.prepareStatement("DELETE FROM role_approval WHERE id = ?");
            while (roleOverwriteResults.next()){
                overwriteOnRolesStatement.setInt(1, roleOverwriteResults.getInt(2));
                overwriteOnRolesStatement.setInt(2, roleOverwriteResults.getInt(3));
                overwriteOnRolesStatement.setInt(3, roleOverwriteResults.getInt(1));
                //Deleting tuple with ID just used
                deleteOverwritesFromRoleApprovalStatment.setInt(1,roleOverwriteResults.getInt(1));
                overwriteOnRolesStatement.addBatch();
                deleteOverwritesFromRoleApprovalStatment.addBatch();
            }
            overwriteOnRolesStatement.executeBatch();
            deleteOverwritesFromRoleApprovalStatment.executeBatch();

            //todo implement statement for inserting tuples from role_approval into role where id does not exist

            //rolename table
            //get rolename from approval table to table
            //Am certain that the ID's corresponding to the role table are ones that need to be moved.
            //If roleName does not contain the id, insert, else update


        } catch (SQLException e){
            e.printStackTrace();
        }
    }



    static ProductionHandler getInstance() {
        return prHandler;
    }
}

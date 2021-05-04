package data;

import Interfaces.IProduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

class ProductionHandler {
    private static Connection connection = null;

    // Singleton
    private final static ProductionHandler prHandler = new ProductionHandler();

    public ProductionHandler() {
        try {
            connection = getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Establish a connection to the Database
     * @return Connection
     */
    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection(
                "jdbc:postgresql://ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                "pjmqbdledqjucs",
                "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
    }


    static ProductionHandler getInstance() {
        return prHandler;
    }

    public IProduction readProduction(String id) {
        return null;
    }

    public List<IProduction> readPFile() {
        return null;
    }

    public void saveProduction(IProduction production) {
    }

    public void deleteProduction(IProduction production) {
    }
}

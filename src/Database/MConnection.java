package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MConnection {
//    private final String DBNAM = "Tutorial";
    private final String USER = "admin";
    private final String PASS = "admin";
    private final String MURL = "jdbc:sqlserver://localhost:1433;databaseName=ThongKe";


    private static MConnection instance = new MConnection();
    public MConnection(){}

    public static MConnection getInstance(){
        return instance;
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(MURL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
}

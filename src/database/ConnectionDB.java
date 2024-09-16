package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    public Connection getConnection(){
        Connection conn;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String url="jdbc:sqlserver://localhost:1433;databaseName=webbanhang;encrypt=false";
            String username="sa";
            String password="123";
            conn=DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    public void closeConnection(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

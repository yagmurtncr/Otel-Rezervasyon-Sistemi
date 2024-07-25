
/*package com.example.otelrezervasyonsistemi;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;

public class Methods {

    public static Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "otel_rezervasyon";
        String databaseUser = "root";
        String databasePassword = "12345";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }


}*/
package com.example.otelrezervasyonsistemi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Methods {

    public static Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "otel_rezervasyon";
        String databaseUser = "root";
        String databasePassword = "12345";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
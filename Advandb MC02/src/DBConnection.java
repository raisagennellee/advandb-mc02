import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private Connection connect;

    public DBConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/db_hpq"; //insert schema name
        String user = "root";
        String pass = "1234";
        connect = null;

        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url, user, pass);
            System.out.println("SUCCESSFULLY CONNECTED");
        } catch (Exception ex) {
            System.out.println("ERROR CONNECT");
        }

    }

    public Connection getConnection() {
        return connect;
    }
}

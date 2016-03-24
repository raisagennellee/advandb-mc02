package Model;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connect;

    public static void initialize() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mc02"; //insert schema name
        String user = "root";
        String pass = "abc123";
        connect = null;

        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url, user, pass);
            System.out.println("SUCCESSFULLY CONNECTED");
        } catch (Exception ex) {
            System.out.println("ERROR CONNECT");
        }

    }

    public static Connection getConnection() {
    	if (connect == null){
    		initialize();
    	}
        return connect;
    }
}

package ConnnecionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MiguelCastellanos
 */
public class ConneccionForControl {

    private static Connection conn;
    private String url;
    private String passdb;
    private String userdb;
    private static ConneccionForControl connexion = null;

    public ConneccionForControl(String url, String passdb, String userdb) {
        this.url = url;
        this.passdb = passdb;
        this.userdb = userdb;
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            System.out.println("Registrando el Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se registro el Driver " + e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar " + e.getMessage());
        }
    }
    
    public static ConneccionForControl getConneccionForControl(String url, String passdb, String userdb){
        if (connexion == null){
            connexion = new ConneccionForControl(url, passdb, userdb);
        }
        return connexion;
    }
    
    public Connection getConnecion() {
        return conn;
    }

}

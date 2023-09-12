package ConnnecionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MiguelCastellanos
 */
public class ConnecionForAspelCaja {

    private static Connection conn;
    private String url;
    private String passdb;
    private String userdb;
    private static ConnecionForAspelCaja Connecion = null;

    public ConnecionForAspelCaja(String url, String passdb, String userdb) {
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
    
    
    public static ConnecionForAspelCaja getConnecion(String url, String passdb, String userdb){
        if (Connecion == null){
            Connecion = new ConnecionForAspelCaja(url, passdb, userdb);
        }
        return Connecion;
    }
    
    public void CloseConnection() throws SQLException{
        conn.close();
    }
    public Connection getConnecion() {
        return conn;
    }
    
}

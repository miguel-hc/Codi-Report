
package Controller;

import ConnnecionDB.ConnecionPerfilesSae;
import Model.PerfilesSaeModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MiguelCastellanos
 */
public class PerfilesSaeController {
    
    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConnecionPerfilesSae controller = ConnecionPerfilesSae.getConnecion(System.getProperty("url2"), System.getProperty("passdb"), System.getProperty("userdb"));
    PerfilesSaeModel model;
    static PerfilesSaeController perfil = null;
    
    public static PerfilesSaeController getPerfilesSaeController(){
        if (perfil == null){
            perfil = new PerfilesSaeController();
        }
        return perfil;
    }
    
    public Connection getConnexion(){
        return (Connection) controller.getConnecionSae();
    }
    
    public ArrayList<PerfilesSaeModel> getUsuarios() throws SQLException {
        ArrayList<PerfilesSaeModel> usuarios = new ArrayList();
        s = controller.getConnecionSae().createStatement();
        rs = s.executeQuery("select USUARIOS.idusr, USUARIOS.nombre, USUARIOS.usuario, USREMP.idusremp from USUARIOS cross join USREMP where USREMP.idusr =  USUARIOS.idusr;");
        while (rs.next()) {
            model = new PerfilesSaeModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            usuarios.add(model);
        }
        return usuarios;
    }
    
    public void closeConnection() throws SQLException{
        controller.CloseConnection();
    }
}

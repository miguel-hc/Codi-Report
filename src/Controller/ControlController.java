package Controller;

import ConnnecionDB.ConneccionForControl;
import Model.ProductosModel;
import Model.UsuariosControlModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 *
 * @author MiguelCastellanos
 */
public class ControlController {

    static ControlController control = null;
    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConneccionForControl conn = ConneccionForControl.getConneccionForControl(System.getProperty("url4"), System.getProperty("passdb"), System.getProperty("userdb"));
    UsuariosControlModel usercontrollermodel;
    ProductosModel producto;

    public ControlController() {
    }
    
    public Connection getConnexion(){
        return (Connection) conn.getConnecion();
    }

    public static ControlController getControlController() {
        if (control == null) {
            control = new ControlController();
        }
        return control;
    }

    public String getData(String param) throws SQLException {
        String data = "";
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select " + param + " from DAT_TIENDA;");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public String getDatoCaja(String param, int idusremp) throws SQLException {
        String valor = "";
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select C_USER." + param + " from C_USER where C_USER.idusremp = '" + idusremp + "';");
        while (rs.next()) {
            valor = rs.getString(1);
        }
        return valor;
    }

    public ArrayList<UsuariosControlModel> getUser() throws SQLException {
        ArrayList<UsuariosControlModel> user = new ArrayList();
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select * from C_USER");
        while (rs.next()) {
            usercontrollermodel = new UsuariosControlModel(rs.getInt(1), rs.getString(2), rs.getInt(3));
            user.add(usercontrollermodel);
        }
        return user;
    }

    public void addUser(int idusremo, String name) throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "insert into C_USER values (" + idusremo + ",'" + name + "',0)";
        try {
            rs = s.executeQuery("select C_USER.idusremp from C_USER where C_USER.idusremp = '" + idusremo + "'");
            if (!rs.next()) {
                PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
                pstm.execute();
                pstm.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setNumCorte(int id, int numz) throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "update C_USER set C_USER.numz = '" + numz + "' where C_USER.idusremp = '" + id + "';";
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertDataForCorte(String tabla, int numz, String nombre_caja, String tienda, String fecha, double l1, double l2, double l3, double moneda, double ncheque, double voucher, int iduseremp, String z, String fs, int ncanceladas, double montocanceladas, double recarga, String token) throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "insert into " + tabla + " values ('" + numz + "', '" + nombre_caja + "','" + tienda + "','" + fecha + "','" + l1 + "', '" + l2 + "', '" + l3 + "','" + moneda + "', '" + ncheque + "','" + voucher + "', '" + iduseremp + "', '" + z + "', '" + fs + "', '" + ncanceladas + "', '" + montocanceladas + "', '" + recarga + "', '" + token + "')";
        System.out.println(query);
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean ValidarRegistro(int numz, int iduseremp) throws SQLException {
        boolean bandera = false;
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select DAT_CORTE.numz from DAT_CORTE where DAT_CORTE.numz = '" + numz + "' and DAT_CORTE.idcaja = '" + iduseremp + "'");
        if (rs.next()) {
            bandera = true;
        }
        return bandera;
    }

    public void insertDataForBoucher() throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "";
        System.out.println(query);
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Insert_Par_Faltante(String codigo, String descripcion, String fac_entrega, double factor, double existencia, double venta, int clasif) throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "insert into PAR_FALTANTE values ('" + codigo + "', '" + descripcion + "', '" + fac_entrega + "', " + factor + ", " + existencia + ", " + venta + ", " + clasif + ");";
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<ProductosModel> getPar_Faltante() throws SQLException {
        ArrayList<ProductosModel> productos = new ArrayList<>();
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select PAR_FALTANTE.CODIGO, PAR_FALTANTE.DESCRIP , PAR_FALTANTE.FAC_ENTRA , PAR_FALTANTE.FACTOR , PAR_FALTANTE.EXISTENCIA , PAR_FALTANTE.VENTA  from PAR_FALTANTE order by PAR_FALTANTE.clasif;");
        while (rs.next()) {
            producto = new ProductosModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6));
            productos.add(producto);
        }
        return productos;
    }

    public void TruncarTabla() throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "DELETE FROM PAR_FALTANTE;";
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void Update_boucher(int num) throws SQLException {
        s = conn.getConnecion().createStatement();
        String query = "update BOUCHER set BOUCHER.NUMBOUCHER = "+num+" where BOUCHER.IDREG = 1";
        try {
            PreparedStatement pstm = conn.getConnecion().prepareStatement(query);
            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public int getBoucher() throws SQLException {
        int numboucher = 0;
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select BOUCHER.NUMBOUCHER from BOUCHER");
        while (rs.next()) {
            numboucher = rs.getInt(1);
        }
        return numboucher;
    }
    
}

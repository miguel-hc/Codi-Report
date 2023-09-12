package Controller;

import ConnnecionDB.ConnecionForAspelCaja;
import Model.CorteCajasAspelCajaModel;
import Model.PerfilesSaeModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteCajasAspelCajaController {

    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConnecionForAspelCaja conn = ConnecionForAspelCaja.getConnecion(System.getProperty("url3"), System.getProperty("passdb"), System.getProperty("userdb"));
    CorteCajasAspelCajaModel cortemodel;
    PerfilesSaeModel model;
    static CorteCajasAspelCajaController cortecaja = null;

    public static CorteCajasAspelCajaController getCorteCajasAspelCajaController() {
        if (cortecaja == null) {
            cortecaja = new CorteCajasAspelCajaController();
        }
        return cortecaja;
    }

    public Connection getConnexion(){
        return conn.getConnecion();
    }
    
    public List<CorteCajasAspelCajaModel> getCorteCajaAspelCaja(String FechaInicio, String FechaFinal) throws SQLException, ParseException {
        List<CorteCajasAspelCajaModel> Cortes = new ArrayList<>();
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select VVENTAS.vfecha, cajas.descripcio, sum(VVENTAS.totdoc) from VVENTAS cross join cajas where VVENTAS.fecha >= '" + FechaInicio + "' and VVENTAS.fecha <= '" + FechaFinal + "' and VVENTAS.caja = cajas.caja and VVENTAS.status = 'N' group by VVENTAS.vfecha, cajas.descripcio order by VVENTAS.vfecha;");
        while (rs.next()) {
            cortemodel = new CorteCajasAspelCajaModel(rs.getString(2), rs.getString(1), rs.getDouble(3));
            Cortes.add(cortemodel);
        }
        return Cortes;
    }

    public ArrayList<PerfilesSaeModel> getCajas() throws SQLException {
        ArrayList<PerfilesSaeModel> cajas = new ArrayList();
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select CAJAS.caja, CAJAS.descripcio from CAJAS");
        while (rs.next()) {
            model = new PerfilesSaeModel(rs.getString(1), rs.getString(2));
            cajas.add(model);
        }
        return cajas;
    }

    public ArrayList<PerfilesSaeModel> getUsuarios() throws SQLException {
        ArrayList<PerfilesSaeModel> usuarios = new ArrayList();
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select usuarios.usuario, usuarios.nick from usuarios;");
        while (rs.next()) {
            model = new PerfilesSaeModel(rs.getString(1), rs.getString(2));
            usuarios.add(model);
        }
        return usuarios;
    }
    
    public double getCorte(String dniCaja, String dniusuario, String Fecha) throws SQLException {
        double corte = 0;
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("SELECT CORTEXZ" + dniCaja + ".TOTALACT FROM CORTEXZ" + dniCaja + " WHERE CORTEXZ" + dniCaja + ".FECHA = '" + Fecha + "' AND CORTEXZ" + dniCaja + ".CAJERO = '" + dniusuario + "'");
        while (rs.next()) {
            corte = rs.getDouble(1);
        }
        return corte;
    }

    public int getNumeroCorte(String dniCaja, String dniusuario, String Fecha) throws SQLException {
        int numz = 0;
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("SELECT CORTEXZ" + dniCaja + ".FOLIOXZ FROM CORTEXZ" + dniCaja + " WHERE CORTEXZ" + dniCaja + ".FECHA = '" + Fecha + "' AND CORTEXZ" + dniCaja + ".CAJERO = '" + dniusuario + "'");
        while (rs.next()) {
            numz = rs.getInt(1);
        }
        return numz;
    }

    public String getNombreCaja(String idcaja) throws SQLException {
        String nombrecaja = "";
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select CAJAS.descripcio from cajas where cajas.caja= '" + idcaja + "'");
        while (rs.next()) {
            nombrecaja = rs.getString(1);
        }
        return nombrecaja;

    }

    public double getNotasCanceladas(String idCaja, String Fecha, String param) throws SQLException {
        double ncanceladas = 0;
        s = conn.getConnecion().createStatement();
        //COUNT(VVENTAS.nota), SUM(VVENTAS.totdoc)
        rs = s.executeQuery("select "+param+" from VVENTAS where VVENTAS.status = 'C' AND VVENTAS.vfecha = '" + Fecha + "' and VVENTAS.cajero = '0" + idCaja + "';");
        while (rs.next()) {
            ncanceladas = rs.getInt(1);
        }
        return ncanceladas;
    }

    public double getRecarga(String Fecha) throws SQLException{
        double monto = 0;
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select sum(DVENTAS.cantidad) from DVENTAS where DVENTAS.producto = 'RECARGA' and DVENTAS.fecha = '"+Fecha+"';");
        while (rs.next()) {
            monto = rs.getDouble(1);
        }
        return monto;
    }
    
}

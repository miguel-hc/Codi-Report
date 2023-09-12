package Controller;

import ConnnecionDB.ConneccionForSae;
import Model.CorteCajasModel;
import Model.ProductosModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteProveedoresController {

    static CorteProveedoresController proveedorescontroller = null;
    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConneccionForSae conn = ConneccionForSae.getConnecion(System.getProperty("url"), System.getProperty("passdb"), System.getProperty("userdb"));
    CorteCajasModel cortemodel;
    Hashtable<String, String> Proveedores = new Hashtable<String, String>();
    ProductosModel producto;

    public static CorteProveedoresController getCorteProveedoresController() {
        if (proveedorescontroller == null) {
            proveedorescontroller = new CorteProveedoresController();
        }
        return proveedorescontroller;
    }

    public Connection getConnexion(){
        return conn.getConnecionSae();
    }
    
    public void ListProveedores() throws SQLException {
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select PROV01.clave, PROV01.nombre from PROV01;");
        while (rs.next()) {
            Proveedores.put(rs.getString(1), rs.getString(2));
        }
    }

    public double getMontoCompra(String folio) throws SQLException {
        double monto = 0;
        rs = s.executeQuery("select sum(COMPC01.importe) as total from compc01 where compc01.status = 'E' AND compc01.CVE_DOC = '" + folio + "' or (compc01.status = 'O' AND compc01.CVE_DOC = '" + folio + "' );");
        while (rs.next()) {
            monto = rs.getDouble(1);
        }
        return monto;
    }

    public ArrayList<String> getFolios(String DNI, String fecha) throws SQLException {
        ArrayList folios = new ArrayList();
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("SELECT COMPC01.CVE_DOC FROM COMPC01 WHERE COMPC01.CVE_CLPV = '" + DNI + "' AND COMPC01.FECHA_DOC = '" + fecha + "';");
        while (rs.next()) {
            folios.add(rs.getString(1));
        }
        return folios;
    }

    public Hashtable<String, String> getProveedores() {
        return Proveedores;
    }

    public double getCorte(String Fecha, int iduseremp) throws SQLException {
        double corte = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(CUEN_DET01.importe) as abono from CUEN_DET01 where CUEN_DET01.fecha_apli = '" + Fecha + "' and CUEN_DET01.usuario = '" + iduseremp + "';");
        while (rs.next()) {
            corte = rs.getDouble(1);
        }
        return corte;
    }

    public double getNotasCanceladasMontos(String Fecha) throws SQLException {
        double monto = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(FACTV01.can_tot) from FACTV01 where FACTV01.status = 'C' and FACTV01.fecha_doc = '"+Fecha+"';");
        while (rs.next()) {
            monto = rs.getDouble(1);
        }
        return monto;
    }
    
    public int getNotasCanceladasCount(String Fecha) throws SQLException {
        int monto = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select count(FACTV01.IMPORTE) from FACTV01 where FACTV01.status = 'C' and FACTV01.fecha_doc = '"+Fecha+"';");
        while (rs.next()) {
            monto = rs.getInt(1);
        }
        return monto;
    }
    
    public double getMontoRecarga(String Fecha) throws SQLException{
        double monto = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(MINVE01.cant) from MINVE01 where MINVE01.cve_art = 'RECARGA' and MINVE01.fecha_docu = cast('"+Fecha+"' as date) and MINVE01.tipo_doc = 'V';");
        while (rs.next()) {
            monto = rs.getDouble(1);
        }
        return monto;
    }
    
    public ArrayList<ProductosModel> getProductos(String FechaInicio, String FechaFinal) throws SQLException{
        ArrayList<ProductosModel> productos = new ArrayList<>();
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select MINVE01.cve_art, inve01.descr, inve01.uni_med, inve01.fac_conv ,inve01.exist, sum(MINVE01.cant / inve01.fac_conv) from MINVE01 cross join inve01 where MINVE01.cve_art = inve01.cve_art and MINVE01.fecha_docu >= cast('"+FechaInicio+"' as TIMESTAMP) and MINVE01.fecha_docu <= cast('"+FechaFinal+"' as TIMESTAMP) and MINVE01.cve_cpto = '51' group by MINVE01.cve_art, inve01.descr,inve01.uni_med, inve01.fac_conv, inve01.exist;");
        while (rs.next()) {
            producto = new ProductosModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6));
            productos.add(producto);
        }
        return productos;
    }

}

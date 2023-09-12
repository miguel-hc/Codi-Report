package Controller;

import ConnnecionDB.ConneccionForSae;
import java.sql.SQLException;

/**
 *
 * @author MiguelCastellanos
 */
public class NotaVentaProcesadasSaeController {

    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    String FechaInicio, FecheFinal;
    static ConneccionForSae conn = ConneccionForSae.getConnecion(System.getProperty("url"), System.getProperty("passdb"), System.getProperty("userdb"));
    static NotaVentaProcesadasSaeController notaventas = null;
    
    public NotaVentaProcesadasSaeController(String FechaInicio, String FecheFinal) {
        this.FechaInicio = FechaInicio;
        this.FecheFinal = FecheFinal;
    }

    public static NotaVentaProcesadasSaeController getNotaVentaProcesadasSaeController(String FechaInicio, String FecheFinal) {
        if (notaventas == null) {
            notaventas = new NotaVentaProcesadasSaeController(FechaInicio, FecheFinal);
        }
        return notaventas;
    }

    public double getTotNotasProcesadas() throws SQLException{
        double tot  = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(CUEN_M01.importe) from CUEN_M01 where CUEN_M01.fecha_apli between '"+FechaInicio+"' and '"+FecheFinal+"' and CUEN_M01.num_cpto = '2';");
        while (rs.next()) {
            tot = rs.getDouble(1);
        }
        return tot;
    }
    
    public double getNotasPagadasAbonadas() throws SQLException{
        double tot = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(CUEN_DET01.importe) from CUEN_DET01 where CUEN_DET01.fecha_apli between '"+FechaInicio+"' and '"+FecheFinal+"' and CUEN_DET01.id_mov = '2' and CUEN_DET01.num_cpto = '10';");
        while (rs.next()) {
            tot = rs.getDouble(1);
        }
        return tot;
    }
    
    public double getVentaDia() throws SQLException{
        double tot = 0;
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(CUEN_DET01.importe) from CUEN_M01 cross join CUEN_DET01 where CUEN_M01.refer = CUEN_DET01.refer and CUEN_DET01.fecha_apli between '"+FechaInicio+"' and '"+FecheFinal+"' and CUEN_M01.fecha_apli between '"+FechaInicio+"' and '"+FecheFinal+"';");
        while (rs.next()) {
            tot = rs.getDouble(1);
        }
        return tot;
    }
    
    public double getVentaCredito() throws SQLException{
        double tot = 0;
        tot = getTotNotasProcesadas() - getVentaDia();
        return tot;
    }
    
    public double getAbonadosOtrosDias() throws SQLException{
        double tot = 0;
        tot = getNotasPagadasAbonadas() - getTotNotasProcesadas() + getVentaCredito();
        return tot;
    }
    
}

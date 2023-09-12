package Controller;

import ConnnecionDB.ConnecionForAspelCaja;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MiguelCastellanos
 */
public class VentasKitsAspelController {

    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConnecionForAspelCaja conn = ConnecionForAspelCaja.getConnecion(System.getProperty("url3"), System.getProperty("passdb"), System.getProperty("userdb"));
    Map kits;
    
//    public double getVentaKits(String FechaInicioAspel, String FechaFinalAspel, String Siglaskit) throws ParseException, SQLException{
//        double tot = 0;
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//        Date startDate = formatter.parse(FechaInicioAspel);
//        Date endDate = formatter.parse(FechaFinalAspel);
//        Calendar start = Calendar.getInstance();
//        start.setTime(startDate);
//        Calendar end = Calendar.getInstance();
//        end.setTime(endDate);
//        String FechaInicio = formatter.format(start.getTime());
//        String FechaFinal = formatter.format(end.getTime());
//        s = conn.getConnecion().createStatement();
//        rs = s.executeQuery("SELECT SUM(DVENTAS.totpartida) FROM DVENTAS CROSS JOIN VVENTAS CROSS JOIN CATINVEN WHERE DVENTAS.nota = VVENTAS.nota AND CATINVEN.producto = DVENTAS.producto AND CATINVEN.usalida = '"+Siglaskit+"' AND VVENTAS.fecvenc1 >= '"+FechaInicio+"' AND VVENTAS.fecvenc1 <= '"+FechaFinal+"' AND DVENTAS.fecha =  VVENTAS.vfecha;");
//        while (rs.next()) {
//            tot = rs.getDouble(1);
//        }
//        return tot;
//    }
    public double getVentaKits(String FechaInicioAspel, String FechaFinalAspel, String SiglasKits) throws ParseException, SQLException {
        double tot = 0;
        for (int i = 0; i < kits.size(); i++) {
            if (kits.get(SiglasKits) != null) {
                tot = (double) kits.get(SiglasKits);
            }
        }
        return tot;
    }

    public HashMap<String, String> getKits(String FechaInicioAspel, String FechaFinalAspel) throws ParseException, SQLException {
        kits = new HashMap<String, String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(FechaInicioAspel);
        Date endDate = formatter.parse(FechaFinalAspel);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("SELECT CATINVEN.usalida, sum(DVENTAS.totpartida) FROM DVENTAS CROSS JOIN VVENTAS CROSS JOIN CATINVEN WHERE DVENTAS.nota = VVENTAS.nota AND CATINVEN.producto = DVENTAS.producto AND VVENTAS.fecvenc1 >= '" + FechaInicio + "' AND VVENTAS.fecvenc1 <= '" + FechaFinal + "' AND DVENTAS.fecha =  VVENTAS.vfecha group by CATINVEN.usalida;");
        while (rs.next()) {
            kits.put(rs.getString(1), rs.getDouble(2));
        }
        return (HashMap<String, String>) kits;
    }

    public double getVentaTotalAspel(String FechaInicioAspel, String FechaFinalAspel) throws SQLException, ParseException {
        double tot = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(FechaInicioAspel);
        Date endDate = formatter.parse(FechaFinalAspel);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());
        s = conn.getConnecion().createStatement();
        rs = s.executeQuery("select sum(VVENTAS.totdoc) from VVENTAS where VVENTAS.fecha_modificacion >= '" + FechaInicio + "' and VVENTAS.fecha_modificacion <= '" + FechaFinal + "' AND VVENTAS.status = 'N';");
        while (rs.next()) {
            tot = rs.getDouble(1);
        }

        return tot;
    }

}

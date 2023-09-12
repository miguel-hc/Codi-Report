package Controller;

import ConnnecionDB.ConneccionForSae;
import Model.CorteCajasModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author MiguelCastellanos
 */
public class VentasKitsController {

    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConneccionForSae conn = ConneccionForSae.getConnecion(System.getProperty("url"), System.getProperty("passdb"), System.getProperty("userdb"));
    CorteCajasModel cortemodel;
    Map kits;

    //backup
//    public double getVnetasKits(String FechaInicioSae, String FechaFinalSae, String SiglasKits) throws SQLException, ParseException {
//        double tot = 0;
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//        Date startDate = formatter.parse(FechaInicioSae);
//        Date endDate = formatter.parse(FechaFinalSae);
//        Calendar start = Calendar.getInstance();
//        start.setTime(startDate);
//        Calendar end = Calendar.getInstance();
//        end.setTime(endDate);
//        String FechaInicio = formatter.format(start.getTime());
//        String FechaFinal = formatter.format(end.getTime());
//        s = conn.getConnecionSae().createStatement();
//        rs = s.executeQuery("select sum(PAR_FACTV01.tot_partida) from PAR_FACTV01 where PAR_FACTV01.uni_venta = '" + SiglasKits + "' AND PAR_FACTV01.tipo_prod = 'K' AND PAR_FACTV01.version_sinc >= cast('" + FechaInicio + "' as TIMESTAMP) and PAR_FACTV01.version_sinc <= cast('" + FechaFinal + "' as TIMESTAMP) and PAR_FACTV01.cve_esq >= '1';");
//        while (rs.next()) {
//            System.out.println(rs.getDouble(1));
//            tot = rs.getDouble(1);
//        }
//        return tot;
//    }
    //fin
    public double getVnetasKits(String FechaInicioSae, String FechaFinalSae, String SiglasKits) throws SQLException, ParseException {
        double tot = 0;
        for (int i = 0; i < kits.size(); i++) {
            if (kits.get(SiglasKits) != null) {
                tot = (double) kits.get(SiglasKits);
            }
        }
        return tot;
    }

    public HashMap<String, String> getKits(String FechaInicioSae, String FechaFinalSae) throws SQLException, ParseException {
        kits = new HashMap<String, String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(FechaInicioSae);
        Date endDate = formatter.parse(FechaFinalSae);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());
        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select PAR_FACTV01.uni_venta,sum(PAR_FACTV01.tot_partida) from PAR_FACTV01 where PAR_FACTV01.version_sinc >= cast('" + FechaInicio + "' as TIMESTAMP) and PAR_FACTV01.version_sinc <= cast('" + FechaFinal + "' as TIMESTAMP) and PAR_FACTV01.cve_esq >= '1' group by PAR_FACTV01.uni_venta;");
        while (rs.next()) {
            kits.put(rs.getString(1), rs.getDouble(2));
        }
        return (HashMap<String, String>) kits;
    }

    public double getTotalVenta(String FechaInicioSae, String FechaFinalSae) throws SQLException, ParseException {
        double tot = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(FechaInicioSae);
        Date endDate = formatter.parse(FechaFinalSae);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());

        s = conn.getConnecionSae().createStatement();
        rs = s.executeQuery("select sum(CUEN_DET01.importe) from CUEN_DET01 where CUEN_DET01.fecha_apli >= '" + FechaInicio + "' and CUEN_DET01.fecha_apli <= '" + FechaFinal + "';");
        while (rs.next()) {
            tot = rs.getDouble(1);
        }
        return tot;
    }

}

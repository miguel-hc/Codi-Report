package Controller;

import ConnnecionDB.ConneccionForSae;
import Model.CorteCajasModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteCajasController {

    java.sql.Statement s = null;
    java.sql.ResultSet rs = null;
    ConneccionForSae conn = ConneccionForSae.getConnecion(System.getProperty("url"), System.getProperty("passdb"), System.getProperty("userdb"));
    CorteCajasModel cortemodel;

    public List<CorteCajasModel> getCorteCajasSae(PerfilesSaeController Perfil, String FechaInicioSae, String FechaFinalSae) throws SQLException, ParseException {
        List<CorteCajasModel> Cortes = new ArrayList<>();
        s = conn.getConnecionSae().createStatement();
        int size = Perfil.getUsuarios().size();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(FechaInicioSae);
        Date endDate = formatter.parse(FechaFinalSae);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());
        System.out.println(endDate);
        for (int i = 0; i < size; i++) {
            rs = s.executeQuery("select CUEN_DET01.fecha_apli, SUM(CUEN_DET01.importe) as importe from CUEN_DET01 where CUEN_DET01.fecha_apli >= cast('" + FechaInicio + "' as TIMESTAMP) and CUEN_DET01.fecha_apli <= cast('" + FechaFinal + "' as TIMESTAMP) and CUEN_DET01.usuario = '" + Perfil.getUsuarios().get(i).getIdusremp() + "' group by CUEN_DET01.fecha_apli;");
            while (rs.next()) {
                cortemodel = new CorteCajasModel(Perfil.getUsuarios().get(i).getUsuario(), rs.getString(1), rs.getDouble(2));
                Cortes.add(cortemodel);
            }
        }
        return Cortes;
    }
}

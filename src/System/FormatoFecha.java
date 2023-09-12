package System;

import View.CorteCajas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author MiguelCastellanos
 */
public class FormatoFecha {

    CorteCajas view;
    String fecha;

    public FormatoFecha(CorteCajas view) {
        this.view = view;
    }

    public String getFechaInicioSae() {
        Calendar fechainicio = this.view.jfechainicio.getCalendar();
        int datevar = fechainicio.get(Calendar.DATE);
        int monthvar = fechainicio.get(Calendar.MONTH);
        int yearvar = fechainicio.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        datevar = datevar + 1;
        fecha = datevar + "." + monthvar + "." + yearvar;
        return fecha;
    }

    public String getFechaFinalSae() {
        Calendar fechainicio = this.view.jfechafinal.getCalendar();
        int datevar = fechainicio.get(Calendar.DATE);
        int monthvar = fechainicio.get(Calendar.MONTH);
        int yearvar = fechainicio.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        datevar = datevar + 1;
        fecha = datevar + "." + monthvar + "." + yearvar;
        return fecha;
    }

    public String FormatDate(String date, String tipo) throws ParseException {
        String Fecha = "";
        Calendar start = Calendar.getInstance();
        if ("sistema".equals(tipo)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(date);
            Fecha = formatter.format(startDate);
        } else if ("sae".equals(tipo)){
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date startDate = formatter.parse(date);
            start.setTime(startDate);
            Fecha = formatter.format(startDate);
        }
        System.out.println(Fecha);
        return Fecha;
    }

}

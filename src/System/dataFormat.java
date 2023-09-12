package System;

import com.toedter.calendar.JDateChooser;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author MiguelCastellanos
 */
public class dataFormat {

    static dataFormat dateformat = null;

    public dataFormat() {
    }

    public static dataFormat getdataFormat() {
        if (dateformat == null) {
            dateformat = new dataFormat();
        }
        return dateformat;
    }

    public String getFecha(JDateChooser jfecha) {
        String fecha = "";
        Calendar cal = jfecha.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        fecha = datevar + "." + monthvar + "." + yearvar;
        return fecha;
    }
    
    public String getFechaAspelCaja(JDateChooser jfecha) {
        String fecha = "", datevar2 = "", monthvar2 = "";
        Calendar cal = jfecha.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        if (datevar < 10){
            datevar2 = "0"+datevar;
        }else{
            datevar2 = ""+datevar;
        }
        if (monthvar < 10){
            monthvar2 = "0"+monthvar;
        }else{
            monthvar2 = ""+monthvar;
        }
        fecha = datevar2 + "/" + monthvar2 + "/" + yearvar;
        return fecha;
    }
    
    public String formatoMoneda(double Format) {
        Locale usa = new Locale("en", "US");
        Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(Format);
    }

}

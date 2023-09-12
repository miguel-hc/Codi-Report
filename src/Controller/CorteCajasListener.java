package Controller;

import Model.CorteCajasAspelCajaModel;
import Model.CorteCajasModel;
import ReporteController.repCortesPorDias;
import System.FormatoFecha;
import View.CorteCajas;
import com.itextpdf.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteCajasListener implements ActionListener {

    CorteCajas view;
    static CorteCajasListener cortelistener = null;
    CorteCajasController cortecontroller;
    PerfilesSaeController perfiles;
    FormatoFecha dateformat;
    CorteCajasAspelCajaController corteAspel;
    repCortesPorDias rep = new repCortesPorDias();
    double monto;

    public CorteCajasListener(CorteCajas view) {
        this.view = view;
        this.view.bntvalidar.addActionListener(this);
        cortecontroller = new CorteCajasController();
        perfiles = new PerfilesSaeController();
        dateformat = new FormatoFecha(view);
        corteAspel = new CorteCajasAspelCajaController();
    }

    public static CorteCajasListener getCorteListener(CorteCajas view) {
        if (cortelistener == null) {
            cortelistener = new CorteCajasListener(view);
        }
        return cortelistener;
    }
    
    public String formatoMoneda(double Format) {
        Locale usa = new Locale("en", "US");
        Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(Format);
    }

    public String getFechaInicioSae() {
        String datevar2 = "";
        String monthvar2 = "";
        Calendar cal = this.view.jfechainicio.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        if (datevar < 10) {
            datevar2 = datevar + "";
            datevar2 = "0" + datevar2;
        } else {
            datevar2 = datevar + "";
        }
        if (monthvar < 10) {
            monthvar2 = monthvar + "";
            monthvar2 = "0" + monthvar2;
        } else {
            monthvar2 = monthvar + "";
        }
        String fecha = datevar2 + "." + monthvar2 + "." + yearvar;
        return fecha;
    }

    public String getFechaFinalSae(int incremento) {
        String datevar2 = "";
        String monthvar2 = "";
        Calendar cal = this.view.jfechafinal.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        datevar = datevar + incremento;
        monthvar = monthvar + 1;
        if (datevar < 10) {
            datevar2 = datevar + "";
            datevar2 = "0" + datevar2;
        } else {
            datevar2 = datevar + "";
        }
        if (monthvar < 10) {
            monthvar2 = monthvar + "";
            monthvar2 = "0" + monthvar2;
        } else {
            monthvar2 = monthvar + "";
        }
        String fecha = datevar2 + "." + monthvar2 + "." + yearvar;
        return fecha;
    }

    public void llenarTablaCorteSae() throws SQLException, ParseException, IOException, DocumentException {
        DefaultTableModel tablaModel = new DefaultTableModel();
        this.view.jtablecortesaspelsae.setModel(tablaModel);
        monto = 0;
        tablaModel.addColumn("FECHA");
        tablaModel.addColumn("CAJA");
        tablaModel.addColumn("MONTO");
        Object[] columna = new Object[3];
        for (CorteCajasModel str : cortecontroller.getCorteCajasSae(perfiles, getFechaInicioSae(), getFechaFinalSae(0))) {
            columna[0] = dateformat.FormatDate(str.getFecha(), "sistema");
            columna[1] = str.getCaja();
            columna[2] = formatoMoneda(str.getMonto());
            tablaModel.addRow(columna);
            monto = monto + str.getMonto();
        }
        String path = System.getProperty("user.dir") + "/Reportes/";
        rep.utilJTableToPdf(tablaModel, new File(path, "SAE-Reporte DEL " + getFechaInicioSae() + " AL " + getFechaFinalSae(0) + ".pdf"), "SAE-Reporte DEL " + getFechaInicioSae() + " AL " + getFechaFinalSae(0), monto);
    }

    public void llenarTablaCorteAspel() throws SQLException, ParseException, IOException, DocumentException {
        DefaultTableModel tablaModel = new DefaultTableModel();
        this.view.jtablecortesaspelcaja.setModel(tablaModel);
        monto = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(getFechaInicioSae());
        Date endDate = formatter.parse(getFechaFinalSae(1));
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        String FechaInicio = formatter.format(start.getTime());
        String FechaFinal = formatter.format(end.getTime());
        tablaModel.addColumn("FECHA");
        tablaModel.addColumn("CAJA");
        tablaModel.addColumn("MONTO");
        Object[] columna = new Object[3];
        for (CorteCajasAspelCajaModel str : corteAspel.getCorteCajaAspelCaja(FechaInicio, FechaFinal)) {
            columna[0] = str.getFecha();
            columna[1] = str.getCaja();
            columna[2] = formatoMoneda(str.getMonto());
            tablaModel.addRow(columna);
            monto = monto + str.getMonto();
        }
        String path = System.getProperty("user.dir") + "/Reportes/";
        rep.utilJTableToPdf(tablaModel, new File(path, "AspelCaja-Reporte DEL " + getFechaInicioSae() + " AL " + getFechaFinalSae(0) + ".pdf"), "AspelCaja-Reporte DEL " + getFechaInicioSae() + " AL " + getFechaFinalSae(0), monto);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.bntvalidar) {
            try {
                llenarTablaCorteSae();
                llenarTablaCorteAspel();
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(CorteCajasListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CorteCajasListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(CorteCajasListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}

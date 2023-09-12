
package Controller;
import View.VentasKits;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MiguelCastellanos
 */
public class VentasKitsListener implements ActionListener{

    VentasKits view;
    VentasKitsController kitsae;
    VentasKitsAspelController kitaspel;
    static VentasKitsListener ventakits = null;

    public VentasKitsListener(VentasKits view) {
        this.view = view;
        this.view.btnvalidar.addActionListener(this);
        this.view.btnguardar.addActionListener(this);
        kitsae = new VentasKitsController();
        kitaspel = new VentasKitsAspelController();
    }
    
    public static VentasKitsListener getVentasKitsListener(VentasKits view) {
        if (ventakits == null){
            ventakits = new VentasKitsListener(view);
        }
        return ventakits;
    }
    
    public String getFechaInicioSae() {
        Calendar cal = this.view.jfechainicio.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        String fecha = datevar + "." + monthvar + "." + yearvar;
        return fecha;
    }
    
    public String getFechaFinalSae(int incremento) {
        Calendar cal = this.view.jfechafinal.getCalendar();
        int datevar = cal.get(Calendar.DATE);
        int monthvar = cal.get(Calendar.MONTH);
        int yearvar = cal.get(Calendar.YEAR);
        monthvar = monthvar + 1;
        datevar = datevar + incremento;
        String fecha = datevar + "." + monthvar + "." + yearvar;
        return fecha;
    }

    public void llebarDatosKits() throws SQLException, ParseException{
        kitsae.getKits(getFechaInicioSae(), getFechaFinalSae(1));
        this.view.jtotcj.setValue(kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "CJ") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "BTO") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "AJA") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "BL")+ kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "BT") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "MBT") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "PAQ") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "BULTITO") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "CAJ") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "MBTO") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "PQT") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "CAJA"));
        this.view.jtotdc.setValue(kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "DC") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "DOC"));
        this.view.jtot6pzs.setValue(kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "MDOC") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "MD") + kitsae.getVnetasKits(getFechaInicioSae(), getFechaFinalSae(1), "MDC"));
        this.view.jtotunitario.setValue(kitsae.getTotalVenta(getFechaInicioSae(), getFechaFinalSae(0)) - (double) this.view.jtotcj.getValue() - (double) this.view.jtotdc.getValue() - (double) this.view.jtot6pzs.getValue());
        //aspel caja
        kitaspel.getKits(getFechaInicioSae(), getFechaFinalSae(0));
        this.view.jtotcjaspel.setValue(kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "CJ") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "BTO")+ kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "AJA") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "CAJA") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "BL") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "BULTITO") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "BT") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "CAJ") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "MBT") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "MBTO") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "PAQ") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "PQT"));
        this.view.jtotdcaspel.setValue(kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "DC") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "DOC"));
        this.view.jtot6pzsaspel.setValue(kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "MDOC") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "MD") + kitaspel.getVentaKits(getFechaInicioSae(), getFechaFinalSae(0), "MDC"));
        this.view.jtotunitarioaspel.setValue(kitaspel.getVentaTotalAspel(getFechaInicioSae(), getFechaFinalSae(1)) - (double) this.view.jtotcjaspel.getValue() - (double) this.view.jtotdcaspel.getValue() - (double) this.view.jtot6pzsaspel.getValue());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.btnvalidar){
            try {
                llebarDatosKits();
            } catch (SQLException ex) {
                Logger.getLogger(VentasKitsListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(VentasKitsListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource() == this.view.btnguardar){
            try {
                ReporteVentaPorKits.getReporteVentaPorKits(this.view).getReporte();
            } catch (SQLException ex) {
                Logger.getLogger(VentasKitsListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}



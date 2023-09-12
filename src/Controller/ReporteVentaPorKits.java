package Controller;
import System.dataFormat;
import View.VentasKits;
import java.sql.SQLException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author MiguelCastellanos
 */
public class ReporteVentaPorKits {

    VentasKits venta;
    static ReporteVentaPorKits reporte = null; 

    public static ReporteVentaPorKits getReporteVentaPorKits(VentasKits venta){
        if (reporte == null){
            reporte = new ReporteVentaPorKits(venta);
        }
        return reporte;
    }
    
    public ReporteVentaPorKits(VentasKits venta) {
        this.venta = venta;
    }
    
    public void getReporte() throws SQLException{
        try {
            String path = System.getProperty("user.dir") + "/Reportes/VentaKits/ReportVentaKit.jasper";
            HashMap parametros = new HashMap();
            parametros.put("tienda", ControlController.getControlController().getData("NOM_SUCURSAL"));
            parametros.put("periodo", dataFormat.getdataFormat().getFecha(this.venta.jfechainicio) +" AL "+ dataFormat.getdataFormat().getFecha(this.venta.jfechafinal));
            //aspel sae
            parametros.put("tcajasae", dataFormat.getdataFormat().formatoMoneda((double)this.venta.jtotcj.getValue()));
            parametros.put("t12nasae", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotdc.getValue()));
            parametros.put("t6pzasae", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtot6pzs.getValue()));
            parametros.put("totunitariosae", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotunitario.getValue()));
            parametros.put("totalsae", dataFormat.getdataFormat().formatoMoneda((double)this.venta.jtotcj.getValue() + (double) this.venta.jtotdc.getValue() + (double) this.venta.jtot6pzs.getValue() + (double) this.venta.jtotunitario.getValue()));
            //aspel caja
            parametros.put("tcaja", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotcjaspel.getValue()));
            parametros.put("t12na", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotdcaspel.getValue()));
            parametros.put("t6pz", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtot6pzsaspel.getValue()));
            parametros.put("totunitariocaja", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotunitarioaspel.getValue()));
            parametros.put("total", dataFormat.getdataFormat().formatoMoneda((double) this.venta.jtotcjaspel.getValue() + (double) this.venta.jtotdcaspel.getValue() + (double) this.venta.jtot6pzsaspel.getValue() + (double) this.venta.jtotunitarioaspel.getValue()));
            //Montos
            //aspel sae
            double saepor1 = (double) this.venta.jtotdc.getValue() * 0.06;
            double saepor2 = (double) this.venta.jtot6pzs.getValue() * 0.03;
            double saepor3 = (double) this.venta.jtotunitario.getValue() * 0.02;
            parametros.put("saepor1", dataFormat.getdataFormat().formatoMoneda(saepor1));
            parametros.put("saepor2", dataFormat.getdataFormat().formatoMoneda(saepor2));
            parametros.put("saepor3", dataFormat.getdataFormat().formatoMoneda(saepor3));
            parametros.put("totsaepor", dataFormat.getdataFormat().formatoMoneda(saepor1 + saepor2 + saepor3));
            //aspel caja
            double por1 = (double) this.venta.jtotdcaspel.getValue() * 0.06;
            double por2 = (double) this.venta.jtot6pzsaspel.getValue() * 0.03;
            double por3 = (double) this.venta.jtotunitarioaspel.getValue() * 0.02;
            parametros.put("por1", dataFormat.getdataFormat().formatoMoneda(por1));
            parametros.put("por2", dataFormat.getdataFormat().formatoMoneda(por2));
            parametros.put("por3", dataFormat.getdataFormat().formatoMoneda(por3));
            parametros.put("totpor", dataFormat.getdataFormat().formatoMoneda(por1 + por2 + por3));
            
            parametros.put("totalventa", dataFormat.getdataFormat().formatoMoneda((double)NotaVentaProcesadasSaeController.getNotaVentaProcesadasSaeController(dataFormat.getdataFormat().getFecha(this.venta.jfechainicio), dataFormat.getdataFormat().getFecha(this.venta.jfechafinal)).getTotNotasProcesadas()));
            parametros.put("totcaja", dataFormat.getdataFormat().formatoMoneda((double)NotaVentaProcesadasSaeController.getNotaVentaProcesadasSaeController(dataFormat.getdataFormat().getFecha(this.venta.jfechainicio), dataFormat.getdataFormat().getFecha(this.venta.jfechafinal)).getNotasPagadasAbonadas()));
            parametros.put("montocredito", dataFormat.getdataFormat().formatoMoneda((double)NotaVentaProcesadasSaeController.getNotaVentaProcesadasSaeController(dataFormat.getdataFormat().getFecha(this.venta.jfechainicio), dataFormat.getdataFormat().getFecha(this.venta.jfechafinal)).getVentaCredito()));
            parametros.put("abonocredito",dataFormat.getdataFormat().formatoMoneda((double)NotaVentaProcesadasSaeController.getNotaVentaProcesadasSaeController(dataFormat.getdataFormat().getFecha(this.venta.jfechainicio), dataFormat.getdataFormat().getFecha(this.venta.jfechafinal)).getAbonadosOtrosDias()));
            
            JasperPrint jprint = JasperFillManager.fillReport(path, parametros, new JREmptyDataSource());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (JRException e) {
            System.out.print(e.toString());
        }
        
    }
    
    
    
    
    
}

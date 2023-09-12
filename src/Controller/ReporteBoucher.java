package Controller;

import System.dataFormat;
import View.boucher;
import java.io.IOException;
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
public class ReporteBoucher {

    static ReporteBoucher reporte = null;
    boucher vista;

    public ReporteBoucher(boucher vista) {
        this.vista = vista;
    }
    
    public static ReporteBoucher getReporteBoucher(boucher vista){
        if(reporte == null){
            reporte = new ReporteBoucher(vista);
        }
        return reporte;
    }
    
    public void getReporteVoucher() throws IOException, JRException, SQLException{
        try{
            String path = System.getProperty("user.dir")+"/Reportes/boucher/boucher.jasper";
            HashMap parametros = new HashMap();
            parametros.put("tienda", ControlController.getControlController().getData("NOM_SUCURSAL"));
            parametros.put("fecha", dataFormat.getdataFormat().getFecha(this.vista.jcalendario));
            parametros.put("contador", ControlController.getControlController().getBoucher());
            parametros.put("cmr", dataFormat.getdataFormat().formatoMoneda((double)this.vista.cmretiro.getValue()));
            parametros.put("cmc", dataFormat.getdataFormat().formatoMoneda((double)this.vista.cmcompra.getValue()));
            parametros.put("cmac", dataFormat.getdataFormat().formatoMoneda((double)this.vista.cmaretiros.getValue()));
            parametros.put("cmar", dataFormat.getdataFormat().formatoMoneda((double)this.vista.cmacompras.getValue()));
            parametros.put("car", dataFormat.getdataFormat().formatoMoneda((double)this.vista.caretiros.getValue()));
            parametros.put("cac", dataFormat.getdataFormat().formatoMoneda((double)this.vista.cacomptras.getValue()));
            parametros.put("total", dataFormat.getdataFormat().formatoMoneda((double) this.vista.cmretiro.getValue() + (double)this.vista.cmcompra.getValue() +  (double)this.vista.cmaretiros.getValue() + (double)this.vista.cmacompras.getValue() + (double)this.vista.caretiros.getValue() + (double)this.vista.cacomptras.getValue()));
            parametros.put("encargado", ControlController.getControlController().getData("NOM_ENCARGADO"));
            JasperPrint jprint = JasperFillManager.fillReport(path, parametros, new JREmptyDataSource());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
            ControlController.getControlController().Update_boucher(ControlController.getControlController().getBoucher() + 1);
        }catch(JRException e){
            System.out.print(e.toString());
        }
    }
    
}

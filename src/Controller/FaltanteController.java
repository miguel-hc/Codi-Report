package Controller;

import Model.ProductosModel;
import System.dataFormat;
import View.HomeFaltante;
import com.itextpdf.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MiguelCastellanos
 */
public class FaltanteController implements ActionListener {

    static FaltanteController faltante = null;
    HomeFaltante vista;
    DefaultTableModel tablaModel;

    public FaltanteController(HomeFaltante vista) {
        this.vista = vista;
        this.vista.txtvalidar.addActionListener(this);
        this.vista.txtguardar.addActionListener(this);
    }

    public static FaltanteController getFaltanteController(HomeFaltante vista) {
        if (faltante == null) {
            faltante = new FaltanteController(vista);
        }
        return faltante;
    }

    public void Ordenar_Producto() throws SQLException {
        for (ProductosModel str : CorteProveedoresController.getCorteProveedoresController().getProductos(dataFormat.getdataFormat().getFecha(this.vista.jfechainicio), dataFormat.getdataFormat().getFecha(this.vista.jfechafinal))) {
            if (!validar(str.getCve_art())) {
                if ("BTO".equals(str.getUni_med())) {
                    ControlController.getControlController().Insert_Par_Faltante(str.getCve_art(), str.getDescr(), str.getUni_med(), str.getFac_conv(), str.getExist(), str.getP_venta(), 1);
                } else if ("PAQ".equals(str.getUni_med())) {
                    ControlController.getControlController().Insert_Par_Faltante(str.getCve_art(), str.getDescr(), str.getUni_med(), str.getFac_conv(), str.getExist(), str.getP_venta(), 2);
                } else if ("CJ".equals(str.getUni_med())) {
                    ControlController.getControlController().Insert_Par_Faltante(str.getCve_art(), str.getDescr(), str.getUni_med(), str.getFac_conv(), str.getExist(), str.getP_venta(), 3);
                } else {
                    ControlController.getControlController().Insert_Par_Faltante(str.getCve_art(), str.getDescr(), str.getUni_med(), str.getFac_conv(), str.getExist(), str.getP_venta(), 4);
                }
            }
        }
    }

    public void llenarTabla() throws SQLException {
        tablaModel = new DefaultTableModel();
        this.vista.jdatosfaltantes.setModel(tablaModel);
        tablaModel.addColumn("CODIGO");
        tablaModel.addColumn("DESCRIPCION");
        tablaModel.addColumn("U-ENTRADA");
        tablaModel.addColumn("FACTOR");
        tablaModel.addColumn("EXISTENCIA");
        tablaModel.addColumn("VENTA");
        tablaModel.addColumn("FALTANTE");
        Object[] columna = new Object[7];
        for (ProductosModel str : ControlController.getControlController().getPar_Faltante()) {
            columna[0] = str.getCve_art();
            columna[1] = str.getDescr();
            columna[2] = str.getUni_med();
            columna[3] = str.getFac_conv();
            columna[4] = str.getExist();
            columna[5] = str.getP_venta();
            columna[6] = "";
            tablaModel.addRow(columna);
        }
    }

    public void GuardarDatos() throws SQLException, IOException, DocumentException{
        String path = System.getProperty("user.dir") + "/Reportes/Faltante/";
        ReporteFaltanteController.getReporteFaltanteController().utilJTableToPdf(tablaModel,  new File(path, "FALTANTE DEL " + dataFormat.getdataFormat().getFecha(this.vista.jfechainicio) + " AL " + dataFormat.getdataFormat().getFecha(this.vista.jfechafinal) + ".pdf"), "FALTANTE DEL" + dataFormat.getdataFormat().getFecha(this.vista.jfechainicio) + " AL " + dataFormat.getdataFormat().getFecha(this.vista.jfechafinal), 0);
        ControlController.getControlController().TruncarTabla();
    }
    
    public boolean validar(String codigo) {
        boolean vandera = false;
        List<String> lista = new ArrayList<>();
        try (FileReader fr = new FileReader(System.getProperty("user.dir") + "//System//Param//ListaProduct.txt");
                BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lista.size(); i++) {
            if (codigo == null ? lista.get(i) == null : codigo.equals(lista.get(i))) {
                vandera = true;
            }
        }
        return vandera;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.txtvalidar) {
            try {
                Ordenar_Producto();
                llenarTabla();
            } catch (SQLException ex) {
                Logger.getLogger(FaltanteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vista.txtguardar){
            try {
                GuardarDatos();
            } catch (SQLException ex) {
                Logger.getLogger(FaltanteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FaltanteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FaltanteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

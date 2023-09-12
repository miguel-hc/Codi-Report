package Controller;

import System.ReadGastos;
import System.RunView;
import System.dataFormat;
import View.CorteSae;
import View.Inicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteListenerAspelSae implements ActionListener {

    CorteProveedoresController proveedor;
    CorteSae corte;
    int iduseremp;
    dataFormat dateformat;
    ReporteSaeCorteController reporte;
    Inicio inicio;
    RunView runview;
    ConfigListener config;
    int bandera;
    static CorteListenerAspelSae corteaspesae = null;

    public CorteListenerAspelSae(CorteSae corte, Inicio inicio) throws SQLException, IOException {
        //proveedor = new CorteProveedoresController();
        dateformat = new dataFormat();
        //iniciamos el calendario de la vista con la fecha del equipo
        Calendar c2 = new GregorianCalendar();
        corte.jfecha.setCalendar(c2);
        //inicializamos los valores que resivimos por paramatro
        this.corte = corte;
        //realizamos una peticion a la bd para llenar los provedores
        proveedor.getCorteProveedoresController().ListProveedores();
        llevarProveedores();
        this.inicio = inicio;
        //implementacion del actionListener
        this.corte.provedor1.addActionListener(this);
        this.corte.provedor2.addActionListener(this);
        this.corte.provedor3.addActionListener(this);
        this.corte.provedor4.addActionListener(this);
        this.corte.provedor5.addActionListener(this);
        this.corte.provedor6.addActionListener(this);
        this.corte.provedor7.addActionListener(this);
        this.corte.provedor8.addActionListener(this);
        this.corte.provedor9.addActionListener(this);
        this.corte.jfolio1.addActionListener(this);
        this.corte.jfolio2.addActionListener(this);
        this.corte.jfolio3.addActionListener(this);
        this.corte.jfolio4.addActionListener(this);
        this.corte.jfolio5.addActionListener(this);
        this.corte.jfolio6.addActionListener(this);
        this.corte.jfolio7.addActionListener(this);
        this.corte.jfolio8.addActionListener(this);
        this.corte.jfolio9.addActionListener(this);
        this.corte.btnvalidad.addActionListener(this);
        this.corte.btnGuardar.addActionListener(this);
        this.corte.btnconfig.addActionListener(this);
        this.corte.jusuarios.addActionListener(this);
        llenarUsuarios();
        getTipoGastos();
    }

    public void getTipoGastos() throws IOException{
        for (String str : ReadGastos.getReadGastos().getGastos()){
            this.corte.jtipogastos1.addItem(str);
            this.corte.jtipogastos2.addItem(str);
            this.corte.jtipogastos3.addItem(str);
            this.corte.jtipogastos4.addItem(str);
            this.corte.jtipogastos5.addItem(str);
            this.corte.jtipogastos6.addItem(str);
            this.corte.jtipogastos7.addItem(str);
            this.corte.jtipogastos8.addItem(str);
            this.corte.jtipogastos9.addItem(str);
        }
    }
    
    public static CorteListenerAspelSae getCorteListenerAspelSae(CorteSae corte, Inicio inicio) throws SQLException, IOException{
        if (corteaspesae == null){
            corteaspesae = new CorteListenerAspelSae(corte, inicio);
        }
        return corteaspesae;
    }
    
    public void llenarUsuarios() throws SQLException {
        if (this.corte.jusuarios.getItemCount() == 0) {
            int size = PerfilesSaeController.getPerfilesSaeController().getUsuarios().size();
            for (int i = 0; i < size; i++) {
                this.corte.jusuarios.addItem(PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getUsuario());
            }
        }
    }

    public int buscarUsuario(String user) throws SQLException {
        int SizeUsuarios = PerfilesSaeController.getPerfilesSaeController().getUsuarios().size();
        System.out.println(user);
        for (int i = 0; i < SizeUsuarios; i++) {
            if (PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getUsuario() == null ? user == null : PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getUsuario().equals(user)) {
                iduseremp = PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getIdusremp();
            }
        }
        return iduseremp;
    }
    
    
    public double getGastos() {
        double gastos = 0;
        gastos = (double) corte.gasto1.getValue() + (double) corte.gasto2.getValue() + (double) corte.gasto3.getValue() + (double) corte.gasto4.getValue() + (double) corte.gasto5.getValue() + (double) corte.gasto6.getValue() + (double) corte.gasto7.getValue() + (double) corte.gasto8.getValue() + (double) corte.gasto9.getValue();
        return gastos;
    }

    public double getTotalCompra() {
        double totalcompra = 0;
        double todescuento = 0;
        if (this.corte.jfolio1.getSelectedIndex() == -1) {
            this.corte.totalpro1.setValue(0.0);
        }
        if (this.corte.jfolio2.getSelectedIndex() == -1) {
            this.corte.totalpro2.setValue(0.0);
        }
        if (this.corte.jfolio3.getSelectedIndex() == -1) {
            this.corte.totalpro3.setValue(0.0);
        }
        if (this.corte.jfolio4.getSelectedIndex() == -1) {
            this.corte.totalpro4.setValue(0.0);
        }
        if (this.corte.jfolio5.getSelectedIndex() == -1) {
            this.corte.totalpro5.setValue(0.0);
        }
        if (this.corte.jfolio6.getSelectedIndex() == -1) {
            this.corte.totalpro6.setValue(0.0);
        }
        if (this.corte.jfolio7.getSelectedIndex() == -1) {
            this.corte.totalpro7.setValue(0.0);
        }
        if (this.corte.jfolio8.getSelectedIndex() == -1) {
            this.corte.totalpro8.setValue(0.0);
        }
        if (this.corte.jfolio9.getSelectedIndex() == -1) {
            this.corte.totalpro9.setValue(0.0);
        }
        todescuento = (double) this.corte.jdescuento1.getValue() + (double) this.corte.jdescuento2.getValue() + (double) this.corte.jdescuento3.getValue() + (double) this.corte.jdescuento4.getValue() + (double) this.corte.jdescuento5.getValue() + (double) this.corte.jdescuento6.getValue() + (double) this.corte.jdescuento7.getValue() + (double) this.corte.jdescuento8.getValue() + (double) this.corte.jdescuento9.getValue();
        totalcompra = (double) this.corte.totalpro1.getValue() + (double) this.corte.totalpro2.getValue() + (double) this.corte.totalpro3.getValue() + (double) this.corte.totalpro4.getValue() + (double) this.corte.totalpro5.getValue() + (double) this.corte.totalpro6.getValue() + (double) this.corte.totalpro7.getValue() + (double) this.corte.totalpro8.getValue() + (double) this.corte.totalpro9.getValue() - todescuento;
        return totalcompra;
    }

    public void llevarProveedores() {
        int ProveedorSize = proveedor.getCorteProveedoresController().getProveedores().size();
        for (Entry<String, String> entry : proveedor.getCorteProveedoresController().getProveedores().entrySet()) {
            corte.provedor1.addItem(entry.getValue());
            corte.provedor2.addItem(entry.getValue());
            corte.provedor3.addItem(entry.getValue());
            corte.provedor4.addItem(entry.getValue());
            corte.provedor5.addItem(entry.getValue());
            corte.provedor6.addItem(entry.getValue());
            corte.provedor7.addItem(entry.getValue());
            corte.provedor8.addItem(entry.getValue());
            corte.provedor9.addItem(entry.getValue());
        }
    }

    public double getEfect() {
        double tot = 0;
        tot = (double) this.corte.L1.getValue() + (double) this.corte.L2.getValue() + (double) this.corte.L3.getValue() + (double) this.corte.moneda.getValue() + (double) this.corte.ncheque.getValue() + (double) this.corte.boucher.getValue();
        return tot;
    }

    public String BuscarIdProveedor(String name) {
        String id = "";
        for (Entry<String, String> entry : proveedor.getCorteProveedoresController().getProveedores().entrySet()) {
            if (entry.getValue().equals(name)) {
                id = entry.getKey();
            }
        }
        return id;
    }

    public void getFolios(String idproveedor, JComboBox jproveedor) throws SQLException {
        jproveedor.removeAllItems();
        for (String cadena : proveedor.getCorteProveedoresController().getFolios(idproveedor, dateformat.getdataFormat().getFecha(corte.jfecha))) {
            jproveedor.addItem(cadena);
        }
    }

    public void getMontoCompra(JComboBox jfolio, JSpinner jtotcompra) throws SQLException {
        if (jfolio.getItemCount() >= 1) {
            jtotcompra.setValue(proveedor.getCorteProveedoresController().getMontoCompra(jfolio.getSelectedItem().toString()));
        } else {
            jtotcompra.setValue(0);
        }

    }

    public void getCorteSae() {
        double cortez = 0;
        double totgeneral = getEfect() + getTotalCompra() + getGastos() + (double) corte.devolucion.getValue() + (double) corte.error.getValue();
        try {
            cortez = proveedor.getCorteProveedoresController().getCorte(dateformat.getdataFormat().getFecha(corte.jfecha), iduseremp);
        } catch (SQLException ex) {
            Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
        }
        corte.txtsubtotalgasto.setText(dateformat.formatoMoneda(getGastos()));
        corte.txttotalcompraprovedor.setText(dateformat.formatoMoneda(getTotalCompra()));
        corte.txtsubL.setText(dateformat.formatoMoneda(getEfect()));
        corte.txtsubtotalgeneral.setText(dateformat.formatoMoneda(totgeneral));
        corte.txtz.setText(dateformat.formatoMoneda(cortez));
        corte.txtfaltosobro.setText(dateformat.formatoMoneda(totgeneral - cortez));
    }

    public void Guardar() {

        int confirmado = JOptionPane.showConfirmDialog(this.corte, "¿Los Datos son correctos?");
        if (JOptionPane.OK_OPTION == confirmado) {
            try {
                double totgeneral = getEfect() + getTotalCompra() + getGastos() + (double) corte.devolucion.getValue() + (double) corte.error.getValue();
                reporte.getReporteSaeCorteController(this.corte).getReportePdf(iduseremp, dateformat.getdataFormat().getFecha(corte.jfecha), getEfect(), getTotalCompra(), getGastos(), totgeneral, "AspelSae", "");
                bandera = +1;
            } catch (SQLException ex) {
                Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.corte.provedor1 || e.getSource() == this.corte.provedor2 || e.getSource() == this.corte.provedor3 || e.getSource() == this.corte.provedor4 || e.getSource() == this.corte.provedor5 || e.getSource() == this.corte.provedor6 || e.getSource() == this.corte.provedor7 || e.getSource() == this.corte.provedor8 || e.getSource() == this.corte.provedor9) {
            try {
                getFolios(BuscarIdProveedor(this.corte.provedor1.getSelectedItem().toString()), corte.jfolio1);
                getFolios(BuscarIdProveedor(this.corte.provedor2.getSelectedItem().toString()), corte.jfolio2);
                getFolios(BuscarIdProveedor(this.corte.provedor3.getSelectedItem().toString()), corte.jfolio3);
                getFolios(BuscarIdProveedor(this.corte.provedor4.getSelectedItem().toString()), corte.jfolio4);
                getFolios(BuscarIdProveedor(this.corte.provedor5.getSelectedItem().toString()), corte.jfolio5);
                getFolios(BuscarIdProveedor(this.corte.provedor6.getSelectedItem().toString()), corte.jfolio6);
                getFolios(BuscarIdProveedor(this.corte.provedor7.getSelectedItem().toString()), corte.jfolio7);
                getFolios(BuscarIdProveedor(this.corte.provedor8.getSelectedItem().toString()), corte.jfolio8);
                getFolios(BuscarIdProveedor(this.corte.provedor9.getSelectedItem().toString()), corte.jfolio9);
            } catch (SQLException ex) {
                Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == this.corte.jfolio1 || e.getSource() == this.corte.jfolio2 || e.getSource() == this.corte.jfolio3 || e.getSource() == this.corte.jfolio4 || e.getSource() == this.corte.jfolio5 || e.getSource() == this.corte.jfolio6 || e.getSource() == this.corte.jfolio7 || e.getSource() == this.corte.jfolio8 || e.getSource() == this.corte.jfolio9) {
            try {
                getMontoCompra(corte.jfolio1, corte.totalpro1);
                getMontoCompra(corte.jfolio1, corte.totalpro1);
                getMontoCompra(corte.jfolio2, corte.totalpro2);
                getMontoCompra(corte.jfolio3, corte.totalpro3);
                getMontoCompra(corte.jfolio4, corte.totalpro4);
                getMontoCompra(corte.jfolio5, corte.totalpro5);
                getMontoCompra(corte.jfolio6, corte.totalpro6);
                getMontoCompra(corte.jfolio7, corte.totalpro7);
                getMontoCompra(corte.jfolio8, corte.totalpro8);
                getMontoCompra(corte.jfolio9, corte.totalpro9);
            } catch (SQLException ex) {
                Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == this.corte.btnvalidad) {
            getCorteSae();
        }

        if (e.getSource() == this.corte.btnGuardar) {
            if (bandera == 0) {
                Guardar();
            } else {
                int confirmado = JOptionPane.showConfirmDialog(this.corte, "Ya se ha guardado un corte, ¿Desea guardarla como nuevo?");
                if (JOptionPane.OK_OPTION == confirmado) {
                    bandera = 0;
                    Guardar();
                }
            }
        }
        if (e.getSource() == this.corte.btnconfig) {
            if (this.inicio.jDesktopPane1.getSelectedFrame() != this.runview.getConfig()) {
                this.inicio.jDesktopPane1.add(this.runview.getConfig());
                this.runview.getConfig().setVisible(true);
                try {
                    this.config.getConfigListener(this.runview.getConfig());
                } catch (SQLException ex) {
                    Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (e.getSource() == this.corte.jusuarios){
            try {
                iduseremp = buscarUsuario(this.corte.jusuarios.getSelectedItem().toString());
                System.out.println(buscarUsuario(this.corte.jusuarios.getSelectedItem().toString()));
            } catch (SQLException ex) {
                Logger.getLogger(CorteListenerAspelSae.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

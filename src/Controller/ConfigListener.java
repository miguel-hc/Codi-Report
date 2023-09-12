package Controller;

import View.Config;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MiguelCastellanos
 */
public class ConfigListener implements ActionListener {

    static ConfigListener config = null;
    Config vista;
    ConfigController controller;

    public ConfigListener(Config vista) throws SQLException {
        this.vista = vista;
        this.vista.btncargarusuario.addActionListener(this);
        this.vista.btnguardar.addActionListener(this);
        CargarUsuarios();
    }

    public static ConfigListener getConfigListener(Config vista) throws SQLException {
        if (config == null) {
            config = new ConfigListener(vista);
        }
        return config;
    }

    public void CargarUsuarios() throws SQLException {
        ConfigController.getConfigController().cargarDatosTienda(this.vista.txttiendaname, this.vista.txtencargado);
        ConfigController.getConfigController().listUser(this.vista.juser);
        ConfigController.getConfigController().getNumBoucher(this.vista.numboucher);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.btncargarusuario) {
            try {
                ConfigController.getConfigController().addUser();
                JOptionPane.showMessageDialog(vista, "Usuarios Cargados Con Exito....");
            } catch (SQLException ex) {
                Logger.getLogger(ConfigListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == this.vista.btnguardar) {
            try {
                ConfigController.getConfigController().actualizarDatos(this.vista.juser, this.vista.numboucher);
                CargarUsuarios();
                JOptionPane.showMessageDialog(vista, "Datos Actualizados con Exito...");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "error");  
            }
        }
    }

}

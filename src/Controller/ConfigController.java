package Controller;

import Model.UsuariosControlModel;
import java.sql.SQLException;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MiguelCastellanos
 */
public class ConfigController {

    static ConfigController config = null;
    PerfilesSaeController perfil;
    ControlController control;

    public static ConfigController getConfigController() {
        if (config == null) {
            config = new ConfigController();
        }
        return config;
    }

    public void addUser() throws SQLException {
        for (int i = 0; i < PerfilesSaeController.getPerfilesSaeController().getUsuarios().size(); i++) {
            ControlController.getControlController().addUser(PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getIdusremp(), PerfilesSaeController.getPerfilesSaeController().getUsuarios().get(i).getUsuario());
        }
    }

    public void cargarDatosTienda(JTextField txttiendaname, JTextField txtencargado) throws SQLException{
        txttiendaname.setText(control.getControlController().getData("NOM_SUCURSAL"));
        txtencargado.setText(control.getControlController().getData("NOM_ENCARGADO"));
    }
    
    public JTable listUser(JTable juser) throws SQLException {
        DefaultTableModel tablaModel = new DefaultTableModel();
        juser.setModel(tablaModel);
        tablaModel.addColumn("ID");
        tablaModel.addColumn("USUARIO");
        tablaModel.addColumn("NUMZ");
        Object[] columna = new Object[3];
        int sizeUser = ControlController.getControlController().getUser().size();
        for (UsuariosControlModel user : ControlController.getControlController().getUser()) {
            columna[0] = user.getId();
            columna[1] = user.getUser();
            columna[2] = user.getNumz();
            tablaModel.addRow(columna);
        }
        return juser;
    }

    public void actualizarDatos(JTable juser, JSpinner numboucher) throws SQLException{
        for (int i = 0; i < juser.getRowCount(); i++){
            System.out.println(juser.getValueAt(i, 0).toString());
            ControlController.getControlController().setNumCorte((int) Integer.parseInt(juser.getValueAt(i, 0).toString()), (int) Integer.parseInt(juser.getValueAt(i, 2).toString()));
        }
        ControlController.getControlController().Update_boucher((int)numboucher.getValue());
    }
    
    public void getNumBoucher(JSpinner numboucher) throws SQLException{
        numboucher.setValue(ControlController.getControlController().getBoucher());
    }
    
}

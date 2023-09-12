package System;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author MiguelCastellanos
 */
public class ReadParamSystem {

    Component dialog = null;

    static ReadParamSystem param = null;
    
    public static ReadParamSystem getReadParamSystem(){
        if (param == null){
            param = new ReadParamSystem();
        }
        return param;
    }
    
    public void readParamSystem() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "//System//Param//ParamBD.txt"), "UTF8")
            );
            Properties p = new Properties(System.getProperties());
            p.load(in);
            System.setProperties(p);
            if (System.getProperty("mostrarproperties").compareTo("si") == 0) {
                System.getProperties().list(System.out);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("No se encontro el Archivo");
            JOptionPane.showMessageDialog(dialog, "No se encontro el Archivo " + e);
            System.exit(-1);
        } catch (java.io.IOException e) {
            System.out.println("Ocurio algun error de I/O");
            JOptionPane.showMessageDialog(dialog, "Ocurio algun error de I/O " + e);
            System.exit(-1);
        }
    }

}

package System;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MiguelCastellanos
 */
public class ReadGastos {

    static ReadGastos gasto = null;
    
    public static ReadGastos getReadGastos(){
        if (gasto == null){
            gasto = new ReadGastos();
        }
        return gasto;
    }
    
    public List<String> getGastos() throws IOException {
        List<String> lista = new ArrayList<>();
        try (FileReader fr = new FileReader(System.getProperty("user.dir") + "//System//Param//TiposGastos.txt");
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
        return lista;
    }
    
}

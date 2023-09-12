
package System;
import View.*;

/**
 *
 * @author MiguelCastellanos
 */
public class RunView {
    
    static SelecionarUsuarioSae usuarios = null;
    static CorteCajas corteCajas = null;
    static VentasKits ventaskits = null;
    static CorteSae cortesae = null;
    static Inicio inicio = null;
    static Config config = null;
    static CorteAspelCaja corteaspelcaja = null;
    static boucher Boucher = null;
    static HomeFaltante faltante = null;

    public static Inicio getInicio(){
        if(inicio == null){
            inicio = new Inicio();
        }
        return inicio;
    }
    
    public static SelecionarUsuarioSae getVistaUsuarios(){
        if (usuarios == null){
            usuarios = new SelecionarUsuarioSae();
        }
        return usuarios;
    }
    
    public static CorteCajas getVistaCorteCajas(){
        if (corteCajas == null){
            corteCajas = new CorteCajas();
        }
        return corteCajas;
    }
    
    public static VentasKits getVentasKits(){
        if (ventaskits == null){
            ventaskits = new VentasKits();
        }
        return ventaskits;
    }
    
    public static CorteSae getCorteSae(){
        if (cortesae == null){
            cortesae = new CorteSae();
        }
        return cortesae;
    }
    
    public static Config getConfig(){
        if (config == null){
            config = new Config();
        }
        return config;
    }
      
    public static CorteAspelCaja getCorteAspelCaja(){
        if (corteaspelcaja == null){
            corteaspelcaja = new CorteAspelCaja();
        }
        return corteaspelcaja;
    }
    
    public static boucher getBoucher(){
        if (Boucher == null){
            Boucher = new boucher(); 
        }
        return Boucher;
    }
    
    public static HomeFaltante getHomeFaltante(){
        if(faltante == null){
            faltante = new HomeFaltante();
        }
        return faltante;
    }
    
}

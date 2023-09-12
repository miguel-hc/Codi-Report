package Controller;

import System.RunView;
import View.Inicio;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MiguelCastellanos
 */
public class InicioCorteSaeController implements ActionListener, Runnable {

    Inicio inicio;
    RunView runview;
    private int desktopwidth = 0;
    private int desktopheigth = 0;
    private Thread hilo;
    CorteListenerAspelCaja corteaspelcaja;

    public InicioCorteSaeController(Inicio inicio, RunView runview) {
        this.inicio = inicio;
        this.runview = runview;
        this.inicio.btncortesae.addActionListener(this);
        this.inicio.btncortecaja.addActionListener(this);
        this.inicio.btncortecajas.addActionListener(this);
        this.inicio.btnventakits.addActionListener(this);
        this.inicio.btnboucher.addActionListener(this);
        this.inicio.btnfaltante.addActionListener(this);
        run();
    }

    public void getSizeDesktop(){
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        desktopwidth = screenSize.width;
        desktopheigth = screenSize.height;
        this.inicio.setSize(new Dimension(screenSize.width,screenSize.height));
        this.inicio.jLabel1.setLocation(desktopwidth / 2-200, desktopheigth /2-200);
        this.inicio.jLabel1.setSize(500, 400);   
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.inicio.btncortesae) {
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getCorteSae()) {
                this.inicio.jDesktopPane1.add(RunView.getCorteSae());
                RunView.getCorteSae().setVisible(true);
                try {
                    CorteListenerAspelSae.getCorteListenerAspelSae(RunView.getCorteSae(), this.inicio);
                } catch (SQLException ex) {
                    Logger.getLogger(InicioCorteSaeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InicioCorteSaeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if(e.getSource() == this.inicio.btncortecaja){
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getCorteAspelCaja()) {
                this.inicio.jDesktopPane1.add(RunView.getCorteAspelCaja());
                RunView.getCorteAspelCaja().setVisible(true);
                try {
                    corteaspelcaja = new CorteListenerAspelCaja(RunView.getCorteAspelCaja(), this.inicio);
                } catch (SQLException ex) {
                    Logger.getLogger(InicioCorteSaeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InicioCorteSaeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (e.getSource() == this.inicio.btncortecajas) {
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getVistaCorteCajas()) {
                this.inicio.jDesktopPane1.add(RunView.getVistaCorteCajas());
                RunView.getVistaCorteCajas().setVisible(true);
                CorteCajasListener.getCorteListener(RunView.getVistaCorteCajas());
            }
        }

        if (e.getSource() == this.inicio.btnventakits){
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getVentasKits()){
                this.inicio.jDesktopPane1.add(RunView.getVentasKits());
                RunView.getVentasKits().setVisible(true);
                VentasKitsListener.getVentasKitsListener(RunView.getVentasKits());
            }
        }
        if (e.getSource() == this.inicio.btnboucher){
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getBoucher()){
                this.inicio.jDesktopPane1.add(RunView.getBoucher());
                RunView.getBoucher().setVisible(true);
                BoucherController.getBoucherController(RunView.getBoucher());
            }
        }
        if (e.getSource() == this.inicio.btnfaltante){
            if (this.inicio.jDesktopPane1.getSelectedFrame() != RunView.getHomeFaltante()){
                this.inicio.jDesktopPane1.add(RunView.getHomeFaltante());
                RunView.getHomeFaltante().setVisible(true);
                FaltanteController.getFaltanteController(RunView.getHomeFaltante());
            }
        }
    }

    @Override
    public void run() {
        getSizeDesktop();
        this.hilo = new Thread((Runnable) this);
        hilo.start();
    }
}

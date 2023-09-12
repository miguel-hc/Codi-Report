package Controller;

import Model.ReporteExcelModel;
import System.dataFormat;
import View.CorteAspelCaja;
import View.CorteSae;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MiguelCastellanos
 */
public class ReporteAspelCajaCorteController {

    static ReporteAspelCajaCorteController reporte = null;
    ControlController control;
    dataFormat format;
    CorteAspelCaja corte;

    public ReporteAspelCajaCorteController(CorteAspelCaja corte) {
        this.corte = corte;
    }

    public static ReporteAspelCajaCorteController getReporteSaeCorteController(CorteAspelCaja corte) {
        if (reporte == null) {
            reporte = new ReporteAspelCajaCorteController(corte);
        }
        return reporte;
    }

    public void getReportePdf(int iduseremp, String Fecha, double subl, double sufcompra, double subgastos, double subtotal, String tipoCorte, String idcaja, String idusuario) throws SQLException, IOException {
        int numzactual = 0;
        //if (!control.getControlController().ValidarRegistro(numzactual, iduseremp)) {

        try {
            String path = System.getProperty("user.dir") + "/Reportes/CorteSae/Corte.jasper";
            HashMap parametros = new HashMap();

            numzactual = CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNumeroCorte(idcaja,idusuario, Fecha);
            parametros.put("nreporte", numzactual);
            parametros.put("nombrecaja", CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNombreCaja(idcaja));
            parametros.put("totrecargas", CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getRecarga(dataFormat.getdataFormat().getFechaAspelCaja(this.corte.jfecha)));
            parametros.put("ncanceladas", CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNotasCanceladas(idcaja, Fecha, "COUNT(VVENTAS.nota)"));
            parametros.put("ntotal", CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNotasCanceladas(idcaja, Fecha, "SUM(VVENTAS.totdoc)"));

            parametros.put("tienda", control.getControlController().getData("NOM_SUCURSAL"));
            parametros.put("fecha", Fecha);
            parametros.put("l1", format.getdataFormat().formatoMoneda((double) this.corte.L1.getValue()));
            parametros.put("l2", format.getdataFormat().formatoMoneda((double) this.corte.L2.getValue()));
            parametros.put("l3", format.getdataFormat().formatoMoneda((double) this.corte.L3.getValue()));
            parametros.put("monedas", format.getdataFormat().formatoMoneda((double) this.corte.moneda.getValue()));
            parametros.put("supl", format.getdataFormat().formatoMoneda((double) this.corte.L1.getValue() + (double) this.corte.L2.getValue() + (double) this.corte.L3.getValue() + (double) this.corte.moneda.getValue()));
            parametros.put("ncheque", format.getdataFormat().formatoMoneda((double) this.corte.ncheque.getValue()));
            parametros.put("suplcheque", format.getdataFormat().formatoMoneda((double) this.corte.L1.getValue() + (double) this.corte.L2.getValue() + (double) this.corte.L3.getValue() + (double) this.corte.moneda.getValue() + (double) this.corte.ncheque.getValue()));
            parametros.put("voucher", format.getdataFormat().formatoMoneda((double) this.corte.boucher.getValue()));
            parametros.put("subvoucher", format.getdataFormat().formatoMoneda((double) this.corte.L1.getValue() + (double) this.corte.L2.getValue() + (double) this.corte.L3.getValue() + (double) this.corte.moneda.getValue() + (double) this.corte.ncheque.getValue() + (double) this.corte.boucher.getValue()));
            parametros.put("pro1nombre", this.corte.provedor1.getSelectedItem().toString());
            parametros.put("pro1monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro1.getValue() - (double) this.corte.jdescuento1.getValue()));
            parametros.put("pro2nombre", this.corte.provedor2.getSelectedItem().toString());
            parametros.put("pro2monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro2.getValue() - (double) this.corte.jdescuento2.getValue()));
            parametros.put("pro3nombre", this.corte.provedor3.getSelectedItem().toString());
            parametros.put("pro3monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro3.getValue() - (double) this.corte.jdescuento3.getValue()));
            parametros.put("pro4nombre", this.corte.provedor4.getSelectedItem().toString());
            parametros.put("pro4monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro4.getValue() - (double) this.corte.jdescuento4.getValue()));
            parametros.put("pro5nombre", this.corte.provedor5.getSelectedItem().toString());
            parametros.put("pro5monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro5.getValue() - (double) this.corte.jdescuento5.getValue()));
            parametros.put("pro6nombre", this.corte.provedor6.getSelectedItem().toString());
            parametros.put("pro6monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro6.getValue() - (double) this.corte.jdescuento6.getValue()));
            parametros.put("pro7nombre", this.corte.provedor7.getSelectedItem().toString());
            parametros.put("pro7monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro7.getValue() - (double) this.corte.jdescuento7.getValue()));
            parametros.put("pro8nombre", this.corte.provedor8.getSelectedItem().toString());
            parametros.put("pro8monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro8.getValue() - (double) this.corte.jdescuento8.getValue()));
            parametros.put("pro9nombre", this.corte.provedor9.getSelectedItem().toString());
            parametros.put("pro9monto", format.getdataFormat().formatoMoneda((double) this.corte.totalpro9.getValue() - (double) this.corte.jdescuento9.getValue()));
            parametros.put("subcompraprovedor", format.getdataFormat().formatoMoneda(sufcompra + subl));
            parametros.put("gasto1", this.corte.jtipogastos1.getSelectedItem().toString());
            parametros.put("mgasto1", format.getdataFormat().formatoMoneda((double) this.corte.gasto1.getValue()));
            parametros.put("gasto2", this.corte.jtipogastos2.getSelectedItem().toString());
            parametros.put("mgasto2", format.getdataFormat().formatoMoneda((double) this.corte.gasto2.getValue()));
            parametros.put("gasto3", this.corte.jtipogastos3.getSelectedItem().toString());
            parametros.put("mgasto3", format.getdataFormat().formatoMoneda((double) this.corte.gasto3.getValue()));
            parametros.put("gasto4", this.corte.jtipogastos4.getSelectedItem().toString());
            parametros.put("mgasto4", format.getdataFormat().formatoMoneda((double) this.corte.gasto4.getValue()));
            parametros.put("gasto5", this.corte.jtipogastos5.getSelectedItem().toString());
            parametros.put("mgasto5", format.getdataFormat().formatoMoneda((double) this.corte.gasto5.getValue()));
            parametros.put("gasto6", this.corte.jtipogastos6.getSelectedItem().toString());
            parametros.put("mgasto6", format.getdataFormat().formatoMoneda((double) this.corte.gasto6.getValue()));
            parametros.put("gasto7", this.corte.jtipogastos7.getSelectedItem().toString());
            parametros.put("mgasto7", format.getdataFormat().formatoMoneda((double) this.corte.gasto7.getValue()));
            parametros.put("gasto8", this.corte.jtipogastos8.getSelectedItem().toString());
            parametros.put("mgasto8", format.getdataFormat().formatoMoneda((double) this.corte.gasto8.getValue()));
            parametros.put("gasto9", this.corte.jtipogastos9.getSelectedItem().toString());
            parametros.put("mgasto9", format.getdataFormat().formatoMoneda((double) this.corte.gasto9.getValue()));
            parametros.put("subgastos", format.getdataFormat().formatoMoneda(subgastos + sufcompra + subl));
            parametros.put("devolucion", this.corte.devolucion.getValue());
            parametros.put("error", this.corte.error.getValue());
            parametros.put("subtotalgeneral", format.getdataFormat().formatoMoneda(subtotal));

            parametros.put("%1", this.corte.iva1.getValue() + " %");
            parametros.put("%2", this.corte.iva2.getValue() + " %");
            parametros.put("%3", this.corte.iva3.getValue() + " %");
            parametros.put("%4", this.corte.iva4.getValue() + " %");
            parametros.put("%5", this.corte.iva5.getValue() + " %");
            parametros.put("%6", this.corte.iva6.getValue() + " %");
            parametros.put("%7", this.corte.iva7.getValue() + " %");
            parametros.put("%8", this.corte.iva8.getValue() + " %");
            parametros.put("%9", this.corte.iva9.getValue() + " %");
            parametros.put("l1comentario", this.corte.l1comet.getText());
            parametros.put("l2comentario", this.corte.l2comet.getText());
            parametros.put("l3comentario", this.corte.l3comet.getText());
            parametros.put("zcomentario", this.corte.jcomentario.getText());
            
            parametros.put("obs1", this.corte.txtobsgasto1.getText());
            parametros.put("obs2", this.corte.txtobsgasto2.getText());
            parametros.put("obs3", this.corte.txtobsgasto3.getText());
            parametros.put("obs4", this.corte.txtobsgasto4.getText());
            parametros.put("obs5", this.corte.txtobsgasto5.getText());
            parametros.put("obs6", this.corte.txtobsgasto6.getText());
            parametros.put("obs7", this.corte.txtobsgasto7.getText());
            parametros.put("obs8", this.corte.txtobsgasto8.getText());
            parametros.put("obs9", this.corte.txtobsgasto9.getText());

            parametros.put("z", this.corte.txtz.getText());
            parametros.put("fs", this.corte.txtfaltosobro.getText());

            //nuevo token random
            int[] arreglo = new int[3];
            String code = "";
            for (int i = 0; i < 3; i++) {
                int numero = (int) (Math.random() * (10000 * 25000 + 100) / 25);
                if (numero % 2 == 0) {
                    arreglo[i] = numero;
                }
            }
            String[] letras = {
                "A",
                "E",
                "I",
                "0",
                "U"
            };
            for (int e = 0; e < letras.length; e++) {
                for (int i = 0; i < arreglo.length; i++) {
                    code = code + arreglo[i] + letras[e] + "";
                }
            }
            parametros.put("token", code);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametros, new JREmptyDataSource());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
            getReporteExcel(idcaja, idusuario, Fecha);
            control.getControlController().insertDataForCorte("DAT_CORTE_ASPELCAJA",numzactual, CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNombreCaja(idcaja), control.getControlController().getData("NOM_SUCURSAL"), Fecha, (double) this.corte.L1.getValue(), (double) this.corte.L2.getValue(), (double) this.corte.L3.getValue(), (double) this.corte.moneda.getValue(), (double) this.corte.ncheque.getValue(), (double) this.corte.boucher.getValue(), iduseremp, this.corte.txtz.getText(), this.corte.txtfaltosobro.getText(), (int) CorteProveedoresController.getCorteProveedoresController().getNotasCanceladasCount(Fecha), CorteProveedoresController.getCorteProveedoresController().getNotasCanceladasMontos(Fecha), CorteProveedoresController.getCorteProveedoresController().getMontoRecarga(Fecha), code);
        } catch (JRException e) {

        }
        //} else {
        //    JOptionPane.showMessageDialog(this.corte, "El numero de Z Con el usuario ya exixste...");
        //}
    }

    public void getReporteExcel(String idcaja,String idusuario, String Fecha) throws SQLException, FileNotFoundException, IOException {
        Workbook libro = new XSSFWorkbook();
        String nombreArchivo = "";
        int Numeroz = CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNumeroCorte(idcaja,idusuario, Fecha);
        String Caja = CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getNombreCaja(idcaja);
        nombreArchivo = "/ReporteAspelCaja-numZ-" + Numeroz + ".xlsx";

        Sheet hoja = libro.createSheet("Reporte");
        ArrayList<ReporteExcelModel> datReport = new ArrayList<>();
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos1.getSelectedItem().toString(), (double) this.corte.gasto1.getValue(), this.corte.provedor1.getSelectedItem().toString(), (double) this.corte.totalpro1.getValue() - (double) this.corte.jdescuento1.getValue(), this.corte.iva1.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos2.getSelectedItem().toString(), (double) this.corte.gasto2.getValue(), this.corte.provedor2.getSelectedItem().toString(), (double) this.corte.totalpro2.getValue() - (double) this.corte.jdescuento2.getValue(), this.corte.iva2.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos3.getSelectedItem().toString(), (double) this.corte.gasto3.getValue(), this.corte.provedor3.getSelectedItem().toString(), (double) this.corte.totalpro3.getValue() - (double) this.corte.jdescuento3.getValue(), this.corte.iva3.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos4.getSelectedItem().toString(), (double) this.corte.gasto4.getValue(), this.corte.provedor4.getSelectedItem().toString(), (double) this.corte.totalpro4.getValue() - (double) this.corte.jdescuento4.getValue(), this.corte.iva4.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos5.getSelectedItem().toString(), (double) this.corte.gasto5.getValue(), this.corte.provedor5.getSelectedItem().toString(), (double) this.corte.totalpro5.getValue() - (double) this.corte.jdescuento5.getValue(), this.corte.iva5.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos6.getSelectedItem().toString(), (double) this.corte.gasto6.getValue(), this.corte.provedor6.getSelectedItem().toString(), (double) this.corte.totalpro6.getValue() - (double) this.corte.jdescuento6.getValue(), this.corte.iva6.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos7.getSelectedItem().toString(), (double) this.corte.gasto7.getValue(), this.corte.provedor7.getSelectedItem().toString(), (double) this.corte.totalpro7.getValue() - (double) this.corte.jdescuento7.getValue(), this.corte.iva7.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos8.getSelectedItem().toString(), (double) this.corte.gasto8.getValue(), this.corte.provedor8.getSelectedItem().toString(), (double) this.corte.totalpro8.getValue() - (double) this.corte.jdescuento8.getValue(), this.corte.iva8.getValue() + ""));
        datReport.add(new ReporteExcelModel(this.corte.jtipogastos9.getSelectedItem().toString(), (double) this.corte.gasto9.getValue(), this.corte.provedor9.getSelectedItem().toString(), (double) this.corte.totalpro9.getValue() - (double) this.corte.jdescuento9.getValue(), this.corte.iva9.getValue() + ""));

        Row fila1 = hoja.createRow(0);
        Cell celda1 = fila1.createCell(0);
        celda1.setCellValue("TIENDA: " + control.getControlController().getData("NOM_SUCURSAL"));
        Cell celda2 = fila1.createCell(1);
        celda2.setCellValue("NUMERO Z: " + Numeroz);
        Cell celda3 = fila1.createCell(2);
        celda3.setCellValue("CAJA: " + Caja);
        Cell celda4 = fila1.createCell(3);
        celda4.setCellValue("FECHA: " + Fecha);

        String[] encabezados = {"GASTOS: ", "TOT-GASTOS: ", "COMPRA: ", "TOT-COMPRA: ", "%: "};
        int indiceFila = 3;
        Row fila = hoja.createRow(indiceFila);
        for (int i = 0; i < encabezados.length; i++) {
            String encabezado = encabezados[i];
            Cell celda = fila.createCell(i);
            celda.setCellValue(encabezado);
        }

        indiceFila++;
        for (int i = 0; i < datReport.size(); i++) {
            fila = hoja.createRow(indiceFila);
            ReporteExcelModel dat = datReport.get(i);
            fila.createCell(0).setCellValue(dat.getDescGasto());
            fila.createCell(1).setCellValue(dat.getTotGasto());
            fila.createCell(2).setCellValue(dat.getDescCompra());
            fila.createCell(3).setCellValue(dat.getTotCompra());
            fila.createCell(4).setCellValue(dat.getCompraporcentaje());
            indiceFila++;
        }

        indiceFila++;
        String[] encavezadoVentas = {"NUM.Z:", "CAJA:", "Z:", "ERROR/DEVOLUCION:", "FALTO/SOBRO:", "MONTO:", "RECARGAS:"};
        int indiceFilaVenta = indiceFila++;
        Row filaVentas = hoja.createRow(indiceFilaVenta);
        for (int i = 0; i < encavezadoVentas.length; i++) {
            String encabezado = encavezadoVentas[i];
            Cell celda = filaVentas.createCell(i);
            celda.setCellValue(encabezado);
        }

        fila = hoja.createRow(indiceFila);
        fila.createCell(0).setCellValue(Numeroz);
        fila.createCell(1).setCellValue(Caja);
        fila.createCell(2).setCellValue(this.corte.txtz.getText());
        fila.createCell(3).setCellValue((double) this.corte.devolucion.getValue() + (double) this.corte.error.getValue());
        fila.createCell(4).setCellValue("");
        fila.createCell(5).setCellValue(this.corte.txtfaltosobro.getText());
        fila.createCell(6).setCellValue(CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getRecarga(dataFormat.getdataFormat().getFechaAspelCaja(this.corte.jfecha)));

        File directorioActual = new File(System.getProperty("user.dir") + "//Reportes//Excel//");
        String ubicacion = directorioActual.getAbsolutePath();
        String ubicacionArchivoSalida = ubicacion.substring(0, ubicacion.length()) + nombreArchivo;
        System.out.println(ubicacionArchivoSalida);
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(ubicacionArchivoSalida);
            libro.write(outputStream);
            libro.close();
            File file = new File(ubicacionArchivoSalida);
            Desktop.getDesktop().open(file);
            System.out.println("");
        } catch (FileNotFoundException ex) {
            System.out.println("Error de filenotfound");
        } catch (IOException ex) {
            System.out.println("Error de IOException");
        }
    }

}

package Controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MiguelCastellanos
 */
public class ReporteFaltanteController {

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font subCategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    static ReporteFaltanteController reporte = null;
    
    public static ReporteFaltanteController getReporteFaltanteController(){
        if(reporte == null){
            reporte =  new ReporteFaltanteController();
        }
        return reporte;
    }
    
    
    public void utilJTableToPdf(DefaultTableModel jTable, File pdfNewFile, String title, double total) throws BadElementException, IOException, DocumentException {
        try {
            // We create the document and set the file name.        
            // Creamos el documento e indicamos el nombre del fichero.
            Document document = new Document(PageSize.A4, 10, 15, 10, 10);
            float[] medidaCeldas = {0.53f, 1.43f, 0.50f, 0.53f, 0.53f, 0.53f, 0.53f};
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF (No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            Image image = null;
            image = Image.getInstance("logo_super.png");
            image.scaleAbsolute(80f, 60f);
            PdfPTable table2 = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(image);
            // Propiedades de la celda
            cell.setColspan(5);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            // Agregar la celda a la tabla
            table2.addCell(cell);
            // Agregar la tabla al documento
            document.add(table2);

            // We add metadata to PDF
            // Añadimos los metadatos del PDF
            document.addTitle("Reporte de Ventas " + title);
            document.addSubject("Using iText (usando iText)");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Miguel Hernandez Castellanos");
            document.addCreator("Miguel Hernandez Castellanos");
            Font categoryFont = null;
            // First page (Primera página)
            Phrase anchor = new Phrase("Reporte " + title, categoryFont);
            //anchor.setName("Reporte de Ventas)");
            //Phrase tatol = new Phrase("TOTAL :" + total, categoryFont);
            //tatol.setName("TOTAL: " + totalVentasSae);
            // Second parameter is the number of the chapter (El segundo parámetro es el número del capítulo).
            //Chapter catPart = new Chapter(new Paragraph(anchor), 1);
            Paragraph subPara = new Paragraph("", subCategoryFont);
            //Section subCatPart = catPart.addSection(subPara);
            //subCatPart.add(new Paragraph(""));
            // Create the table (Creamos la tabla)
            PdfPTable table = new PdfPTable(jTable.getColumnCount());
            table.setWidths(medidaCeldas);
            // Now we fill the rows of the PdfPTable (Ahora llenamos las filas de PdfPTable)
            PdfPCell columnHeader;
            // Fill table columns header 
            // Rellenamos las cabeceras de las columnas de la tabla.                
            for (int column = 0; column < jTable.getColumnCount(); column++) {
                Paragraph columna1 = new Paragraph(jTable.getColumnName(column));
                columna1.getFont().setSize(8);
                table.addCell(columna1);
            }
            table.setHeaderRows(1);
            // Fill table rows (rellenamos las filas de la tabla).                
            for (int row = 0; row < jTable.getRowCount(); row++) {
                for (int column = 0; column < jTable.getColumnCount(); column++) {
                    Paragraph columna1 = new Paragraph(jTable.getValueAt(row, column).toString());
                    columna1.getFont().setSize(8);
                    table.addCell(columna1);
                }
            }
            document.add(anchor);
            document.add(table);
            //document.add(tatol);
            document.close();
            File patch = pdfNewFile;
            File file = new File(patch.getPath());
            Desktop.getDesktop().open(file);
            //JOptionPane.showMessageDialog(vista, "Archivo Guardado Con Exito!!!)","RESULTADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }

}

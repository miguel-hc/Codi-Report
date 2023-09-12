package Model;

/**
 *
 * @author MiguelCastellanos
 */
public class ReporteExcelModel {

    String DescGasto;
    double totGasto;
    String DescCompra;
    double totCompra;
    String compraporcentaje;

    public ReporteExcelModel(String DescGasto, double totGasto, String DescCompra, double totCompra, String compraporcentaje) {
        this.DescGasto = DescGasto;
        this.totGasto = totGasto;
        this.DescCompra = DescCompra;
        this.totCompra = totCompra;
        this.compraporcentaje = compraporcentaje;
    }

    public String getDescGasto() {
        return DescGasto;
    }

    public void setDescGasto(String DescGasto) {
        this.DescGasto = DescGasto;
    }

    public double getTotGasto() {
        return totGasto;
    }

    public void setTotGasto(double totGasto) {
        this.totGasto = totGasto;
    }

    public String getDescCompra() {
        return DescCompra;
    }

    public void setDescCompra(String DescCompra) {
        this.DescCompra = DescCompra;
    }

    public double getTotCompra() {
        return totCompra;
    }

    public void setTotCompra(double totCompra) {
        this.totCompra = totCompra;
    }

    public String getCompraporcentaje() {
        return compraporcentaje;
    }

    public void setCompraporcentaje(String compraporcentaje) {
        this.compraporcentaje = compraporcentaje;
    }
}


package Model;

/**
 *
 * @author MiguelCastellanos
 */
public class CorteCajasModel {

    String Caja;
    String Fecha;
    double Monto;

    public CorteCajasModel(String Caja, String Fecha, double Monto) {
        this.Caja = Caja;
        this.Fecha = Fecha;
        this.Monto = Monto;
    }

    public String getCaja() {
        return Caja;
    }

    public void setCaja(String Caja) {
        this.Caja = Caja;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double Monto) {
        this.Monto = Monto;
    }

}

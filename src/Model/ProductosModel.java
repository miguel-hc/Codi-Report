
package Model;

/**
 *
 * @author MiguelCastellanos
 */
public class ProductosModel {

    String cve_art, descr, uni_med;
    double fac_conv, exist, p_venta;

    public ProductosModel(String cve_art, String descr, String uni_med, double fac_conv, double exist, double p_venta) {
        this.cve_art = cve_art;
        this.descr = descr;
        this.uni_med = uni_med;
        this.fac_conv = fac_conv;
        this.exist = exist;
        this.p_venta = p_venta;
    }

    public String getCve_art() {
        return cve_art;
    }

    public void setCve_art(String cve_art) {
        this.cve_art = cve_art;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUni_med() {
        return uni_med;
    }

    public void setUni_med(String uni_med) {
        this.uni_med = uni_med;
    }

    public double getFac_conv() {
        return fac_conv;
    }

    public void setFac_conv(double fac_conv) {
        this.fac_conv = fac_conv;
    }

    public double getExist() {
        return exist;
    }

    public void setExist(double exist) {
        this.exist = exist;
    }

    public double getP_venta() {
        return p_venta;
    }

    public void setP_venta(double p_venta) {
        this.p_venta = p_venta;
    }

    
}

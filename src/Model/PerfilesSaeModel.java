
package Model;

/**
 *
 * @author MiguelCastellanos
 */
public class PerfilesSaeModel {
    
    String caja;
    int idusr;
    String nombre;
    String usuario;
    int idusremp;

    public PerfilesSaeModel(int idusr, String nombre, String usuario, int idusremp) {
        this.idusr = idusr;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idusremp = idusremp;
    }

    public PerfilesSaeModel(String caja, String usuario) {
        this.caja = caja;
        this.usuario = usuario;
    }
    
    public int getIdusr() {
        return idusr;
    }

    public void setIdusr(int idusr) {
        this.idusr = idusr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdusremp() {
        return idusremp;
    }

    public void setIdusremp(int idusremp) {
        this.idusremp = idusremp;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
    
    
}


package Model;

/**
 *
 * @author MiguelCastellanos
 */
public class UsuariosControlModel {

    int id;
    String user;
    int numz;

    public UsuariosControlModel(int id, String user, int numz) {
        this.id = id;
        this.user = user;
        this.numz = numz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumz() {
        return numz;
    }

    public void setNumz(int numz) {
        this.numz = numz;
    }
    
    
    
}

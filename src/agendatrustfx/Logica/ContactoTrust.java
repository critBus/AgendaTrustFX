package agendatrustfx.Logica;

/**
 * Created by Rene on 15/01/2021.
 */

public class ContactoTrust {
    private String nombre,patrocinador,telefono,wallet,tarjetaDeCredito;
    private int id;
    public ContactoTrust(String nombre, String patrocinador, String telefono, String wallet, String tarjetaDeCredito) {
        this.nombre = nombre;
        this.patrocinador = patrocinador;
        this.telefono = telefono;
        this.wallet = wallet;
        this.tarjetaDeCredito = tarjetaDeCredito;
    }

    public ContactoTrust(String nombre, String patrocinador, String telefono, String wallet, String tarjetaDeCredito, int id) {
        this.nombre = nombre;
        this.patrocinador = patrocinador;
        this.telefono = telefono;
        this.wallet = wallet;
        this.tarjetaDeCredito = tarjetaDeCredito;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTarjetaDeCredito() {
        return tarjetaDeCredito;
    }

    public void setTarjetaDeCredito(String tarjetaDeCredito) {
        this.tarjetaDeCredito = tarjetaDeCredito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

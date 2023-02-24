/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendatrustfx.Logica;

import Utiles.ClasesUtiles.BasesDeDatos.BDConect;
import Utiles.MetodosUtiles.BD;
import java.io.File;

/**
 *
 * @author Rene
 */
public interface DBTrust {

    static final String TABLA_CONTACTO_TRUST = "TABLA_CONTACTO_TRUST", COLUMNA_NOMBRE = "COLUMNA_NOMBRE",
            COLUMNA_PATROCINADOR = "COLUMNA_PATROCINADOR", COLUMNA_TELEFONO = "COLUMNA_TELEFONO", COLUMNA_WALLET = "COLUMNA_WALLET",
            COLUMNA_TARJETA_DE_CREDITO = "COLUMNA_TARJETA_DE_CREDITO", NOMBRE_BD = "BD_AGENDA_TRUST";
    //CARPETA_APP="";//,SP_VERSION="SP_VERSION";

    // static double VERSION=1.0;
    public default File getDireccionDB(){
    File direccion = new File("BD/" + NOMBRE_BD + ".sqlite");
        if (!direccion.exists()) {
            direccion = new File("../BD/" + NOMBRE_BD + ".sqlite");
        }
        return direccion;
    }
    public default BDConect getDB() throws Exception {
        
        //return getDB(BD.crearDBSQLiteSiNoExiste(NOMBRE_BD));
        return getDB(getDireccionDB());
    }

    public default BDConect getDB(File direccion) throws Exception {
        return BDConect.getConexionSQL_LITE(direccion);
    }

    public default void crearBD_DeSerNecesario() throws Exception {
//        if(getDoubSP(SP_VERSION,VERSION)<VERSION){
//            getDB().drop_table_if_exist(TABLA_CONTACTO_TRUST);
//            putSP(SP_VERSION,VERSION);
//        }
        //getDB().crearTablaSiNoExiste(TABLA_DATOS_NUMEROS,COLUMNA_NUMERO_TELEFONO, COLUMNA_NOMBRE,COLUMNA_DESCRIPCION);
        crearBD_DeSerNecesario(getDB());
    }

    public default void crearBD_DeSerNecesario(BDConect conect) throws Exception {
        conect.crearTablaSiNoExiste(TABLA_CONTACTO_TRUST, COLUMNA_NOMBRE, COLUMNA_PATROCINADOR, COLUMNA_TELEFONO, COLUMNA_WALLET, COLUMNA_TARJETA_DE_CREDITO);
    }

    public default void addContactoTrust(ContactoTrust c) throws Exception {
        addContactoTrust(getDB(), c);
    }

    public default void addContactoTrust(BDConect conect, ContactoTrust c) throws Exception {
        conect.insertar(TABLA_CONTACTO_TRUST, c.getNombre(), c.getPatrocinador(), c.getTelefono(), c.getWallet(), c.getTarjetaDeCredito());
    }

    public default ContactoTrust getContactoTrust(String nombre) throws Exception {
        Object[][] con = getDB().select_Where(TABLA_CONTACTO_TRUST, COLUMNA_NOMBRE, nombre);
        return getContactoTrust(con[0]);
    }

    public default ContactoTrust getContactoTrust(int id) throws Exception {
        return getContactoTrust(getDB().select_Id(TABLA_CONTACTO_TRUST, id));
    }

    public default ContactoTrust getContactoTrust(Object o[]) {
        return new ContactoTrust(o[1] + "", o[2] + "", o[3] + "", o[4] + "", o[5] + "", (int) o[0]);
    }

    public default ContactoTrust[] getContactoTrustAll() throws Exception {
        return getContactoTrustAll(getDB());
    }

    public default ContactoTrust[] getContactoTrustAll(BDConect conect) throws Exception {
        Object[][] con = conect.select_Todo(TABLA_CONTACTO_TRUST);
        ContactoTrust[] res = new ContactoTrust[con.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = getContactoTrust(con[i]);
        }
        return res;

    }

    public default void delete(String nombre) throws Exception {
        getDB().delete(TABLA_CONTACTO_TRUST, COLUMNA_NOMBRE, nombre);
    }

    public default void update(ContactoTrust c) throws Exception {
        getDB().update_Id(TABLA_CONTACTO_TRUST, c.getId(), COLUMNA_NOMBRE, c.getNombre(), COLUMNA_PATROCINADOR, c.getPatrocinador(), COLUMNA_TELEFONO, c.getTelefono(), COLUMNA_WALLET, c.getWallet(), COLUMNA_TARJETA_DE_CREDITO, c.getTarjetaDeCredito());
    }

    public default boolean existeNombre(String nombre) throws Exception {
        return getDB().contiene(TABLA_CONTACTO_TRUST, COLUMNA_NOMBRE, nombre);
    }

    public default void resetearBD() throws Exception {
        getDB().drop_table_if_exist(TABLA_CONTACTO_TRUST);
        crearBD_DeSerNecesario();
        //algo default
    }

    public default void initBD() throws Exception {
        crearBD_DeSerNecesario();
//        if(getDB().isEmpty(TABLA_CONTACTO_TRUST,COLUMNA_NOMBRE)){
//            resetearBD();
//        }

    }

    public default void actualizarBD(File f) throws Exception {

        //BDConect conect=BDAndroid.getDB_SQLite(this,f);
        BDConect conect = getDB(f);
        ContactoTrust contactoTrust[] = getContactoTrustAll(conect);
        //System.out.println("salida: contactoTrust.length="+contactoTrust.length);
        for (int i = 0; i < contactoTrust.length; i++) {
            //System.out.println("salida: contactoTrust[i].getNombre()="+contactoTrust[i].getNombre());
            if (!existeNombre(contactoTrust[i].getNombre())) {
                // System.out.println("salida: lo grega");
                addContactoTrust(contactoTrust[i]);
            }
            else{
                contactoTrust[i].setId(getContactoTrust(contactoTrust[i].getNombre()).getId());
                update(contactoTrust[i]);
            }
        }
    }

    public default void copiarDatosA(BDConect conect) throws Exception {
        //ContactoTrust contactoTrust[]=getContactoTrustAll(conect);
        ContactoTrust contactoTrust[] = getContactoTrustAll();
        for (int i = 0; i < contactoTrust.length; i++) {
            addContactoTrust(conect, contactoTrust[i]);
        }

    }

    public default void copiarDatosA(File direccion) throws Exception {
        BDConect conect = getDB(direccion);
        crearBD_DeSerNecesario(conect);
        copiarDatosA(conect);
    }
    
    public default void clearDB() throws Exception {
    ContactoTrust contactoTrust[] = getContactoTrustAll();
        for (int i = 0; i < contactoTrust.length; i++) {
            delete(contactoTrust[i].getNombre());
            
        }

    }

}

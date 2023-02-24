/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendatrustfx.Visual;

import Utiles.FX.Validadores.Validacion;
import static Utiles.FX.Ventanas.Controladores.Controlador.responerException;
import Utiles.FX.Ventanas.Controladores.ControladorAceptarCancelar;
import Utiles.FX.Ventanas.Controladores.ControladorAceptarCancelarDialogo;
import Utiles.FX.VentanasFX;
import Utiles.FX.VisualFX;
import agendatrustfx.Logica.ContactoTrust;
import agendatrustfx.Logica.DBTrust;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Rene
 */
public class VentanaAgregarContactoController extends ControladorAceptarCancelarDialogo implements DBTrust {

    @FXML
    private StackPane Pstack;
    @FXML
    private JFXTextField TNombre;

    @FXML
    private JFXTextField TPatrocinador;

    @FXML
    private JFXTextField TTarjeta;
    @FXML
    private JFXTextField TTelefono;

    @FXML
    private JFXTextField TWallet;

    @FXML
    private Icons525View IIcono;

    @FXML
    private Text TTitulo;

    @Override
    public void iniStage(Parent p) {
        super.iniStage(p); //To change body of generated methods, choose Tools | Templates.
        try {
            switch (VentanaPrincipalController.modoDeDialogoContacto) {
                case AGREGAR:
                    IIcono.setIcon(Icons525.USER_PLUS);
                    TTitulo.setText("Agregar");
                    setR(() -> {
                        try {

                            if (existeNombre(TNombre.getText().trim())) {
                                VentanasFX.dialogoSoloAceptar().show("Ya existe este Contacto");
                                return;
                            }
                            addContactoTrust(new ContactoTrust(TNombre.getText().trim(), TPatrocinador.getText(), TTelefono.getText(), TWallet.getText(), TTarjeta.getText()));
                            VentanaPrincipalController.ventanaPrincipalController.actualizar();
                        } catch (Exception ex) {
                            responerException(ex);
                        }

                    });
                    break;
                case EDITAR:
                    IIcono.setIcon(Icons525.EDIT);
                    TTitulo.setText("Editar");
                    final ContactoTrust c = iniCampos();
                    setR(() -> {
                        try {
                            update(new ContactoTrust(TNombre.getText().trim(), TPatrocinador.getText(), TTelefono.getText(), TWallet.getText(), TTarjeta.getText(),c.getId()));
                             VentanaPrincipalController.ventanaPrincipalController.actualizar();
                        } catch (Exception ex) {
                            responerException(ex);
                        }
                    });

                    break;
                case INFORMACION:
                    IIcono.setIcon(Icons525.ADDRESS_BOOK);
                    TTitulo.setText("Informacion");
                    setR(() -> {
                    });
                    iniCampos();
                    
                    setDisable(true,TNombre,TPatrocinador,TTarjeta,TTelefono,TTitulo,TWallet);
                    
                            
                            
                    break;
            }

            new Validacion("No puede estar vacio", v -> {
                boolean vacio = TNombre.getText().trim().isEmpty();
                //System.out.println("vacio="+vacio);
                return !vacio;
            }, TNombre).setVentanaQueValida(getVentana()).addADesactivar(getBAceptar()).start();

            
            VisualFX.soloNumeros(TTelefono);
        } catch (Exception ex) {
            responerException(ex);
        }
    }

    private ContactoTrust iniCampos() throws Exception {
        ContactoTrust cc = getContactoTrust(VentanaPrincipalController.id_DialogoContacto);
        TNombre.setText(cc.getNombre());
        TPatrocinador.setText(cc.getPatrocinador());
        TTarjeta.setText(cc.getTarjetaDeCredito());
        TTelefono.setText(cc.getTelefono());
        TWallet.setText(cc.getWallet());

        return cc;
    }

}

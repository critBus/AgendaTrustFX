/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendatrustfx.Visual;

//import Utiles.
import Utiles.FX.Ventanas.Controladores.Controlador;
import Utiles.FX.Ventanas.Controladores.ControladorResizable;
import Utiles.FX.VentanasFX;
import Utiles.FX.VisualFX;
import static Utiles.FX.VisualFX.setPredicate;
import static Utiles.FX.VisualFX.setTextBusqueda;
import Utiles.FX.objetoTreeTableView;
import Utiles.MetodosUtiles.Archivo;
import agendatrustfx.Logica.ContactoTrust;
import agendatrustfx.Logica.DBTrust;
import agendatrustfx.Logica.ModoDeDialogoContacto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author Rene
 */
public class VentanaPrincipalController extends ControladorResizable implements DBTrust {

    public static final int ANCHO_NOMBRE = 350, ANCHO_FECHA = 100, NORMAL = 100, LARGO = 400, ANCHO_MEDIO = 200;
    public static ModoDeDialogoContacto modoDeDialogoContacto;
    public static VentanaPrincipalController ventanaPrincipalController;
    public static int id_DialogoContacto;
    private DirectoryChooser dc;
    private FileChooser fc;
    private ObservableList<objetoTreeTableView> contactosTrusts;
    private JFXRadioButton rbsBusqueda[];
    private ToggleGroup tgBusqueda;
    @FXML
    private AnchorPane PAnchor;

    @FXML
    private AnchorPane PAnchorSuperior;

//    @FXML
//    private JFXButton BCerrar;
//
//    @FXML
//    private JFXButton BMinimizar;
//
//    @FXML
//    private JFXButton BMazimizar;
    @FXML
    private JFXButton BHome;

    @FXML
    private JFXButton BClearTodo;

    @FXML
    private JFXButton BActualizarTodo;

    @FXML
    private JFXTextField TBuscarDirecciones;

    @FXML
    private JFXButton BClearBusquedaDirecciones;

    @FXML
    private JFXButton BBuscarDirecciones;

    @FXML
    private JFXButton BEditarDirecciones;

    @FXML
    private JFXButton BEliminarDirecciones;

    @FXML
    private JFXButton BClearDirecciones;

    @FXML
    private JFXButton BCrearDirecciones;

    @FXML
    private JFXButton BAdministradorDirecciones;

    @FXML
    private JFXButton BGuardarDirecciones;

    @FXML
    private StackPane SPLista;

    @FXML
    private JFXTreeTableView<objetoTreeTableView> TVSelecionarDirecciones;

    @FXML
    private AnchorPane PAnchorInferior;

    @FXML
    private JFXButton BInformacionDirecciones;

    @FXML
    void apretoInformacionDirecciones(MouseEvent event) {
        try {
            modoDeDialogoContacto = ModoDeDialogoContacto.INFORMACION;
            id_DialogoContacto = ((ContactoTrust) getSeleccionado(TVSelecionarDirecciones)).getId();
            showController(VentanaAgregarContactoController.class);

        } catch (IOException ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoActualizarTodo(MouseEvent event) {
        try {
            actualizar();
        } catch (Exception ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoAdministradorDirecciones(ActionEvent event) {
try {
            File dir = fc.showOpenDialog(getVentana());
            if (dir != null) {
                actualizarBD(dir);
                 actualizar();
            }
        } catch (Exception ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoAgregarDirecciones(MouseEvent event) {
        try {
            modoDeDialogoContacto = ModoDeDialogoContacto.AGREGAR;
            showController(VentanaAgregarContactoController.class);

        } catch (IOException ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoBuscarDirecciones(MouseEvent event) {

    }

//    @FXML
//    void apretoCerrar(ActionEvent event) {
//
//    }
    @FXML
    void apretoClearBusquedaDirecciones(MouseEvent event) {
        TBuscarDirecciones.setText("");
    }

    @FXML
    void apretoClearDirecciones(MouseEvent event) {
         VentanasFX.dialogoAceptarCancelar().show("Vaciar la BD !!", ()->{
         try{
        clearDB();
        actualizar();
        } catch (Exception ex) {
            responerException(ex);
        }
         });
        
    }

    @FXML
    void apretoClearTodo(MouseEvent event) {

    }

    @FXML
    void apretoEditarDirecciones(MouseEvent event) {
        try {
            modoDeDialogoContacto = ModoDeDialogoContacto.EDITAR;
            id_DialogoContacto = ((ContactoTrust) getSeleccionado(TVSelecionarDirecciones)).getId();
            showController(VentanaAgregarContactoController.class);

        } catch (IOException ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoEliminarDirecciones(MouseEvent event) {
        VentanasFX.dialogoAceptarCancelar().show("Eliminar este Contacto", ()->{
       try {
    
    delete(((ContactoTrust)getSeleccionado(TVSelecionarDirecciones)).getNombre());
        actualizar();
        } catch (Exception ex) {
            responerException(ex);
        }
        });

    }

    @FXML
    void apretoGuardarDirecciones(ActionEvent event) {
        try {
            File dir = fc.showSaveDialog(getVentana());
            if (dir != null) {
                Archivo.copiar(getDireccionDB(), dir);
                
            }
        } catch (Exception ex) {
            responerException(ex);
        }
    }

    @FXML
    void apretoHome(MouseEvent event) {

    }

//    @FXML
//    void apretoMazimizar(ActionEvent event) {
//
//    }
//
//    @FXML
//    void apretoMinimizar(ActionEvent event) {
//
//    }
    @Override
    public void iniStage(Parent p) {
        super.iniStage(p); //To change body of generated methods, choose Tools | Templates.
        try {
            crearBD_DeSerNecesario();
            setMove(PAnchorSuperior, PAnchorInferior);
            dc = new DirectoryChooser();
            fc = getFileChooser("base de datos", ".sqlite");
            //contactosTrusts=FXCollections.observableArrayList(getContactoTrustAll());
            contactosTrusts = getObTreeTableView(getContactoTrustAll());
            enProcesoVisual(() -> {
                crearTabla(TVSelecionarDirecciones, contactosTrusts,
                        getTreeTableColumn("Nombre", ANCHO_NOMBRE, ContactoTrust::getNombre),
                        getTreeTableColumn("Patrocinador", ANCHO_NOMBRE, ContactoTrust::getPatrocinador),
                        getTreeTableColumn("Telefono", ANCHO_NOMBRE, ContactoTrust::getTelefono),
                        getTreeTableColumn("Wallet", ANCHO_NOMBRE, ContactoTrust::getWallet),
                        getTreeTableColumn("Tarjeta", ANCHO_NOMBRE, ContactoTrust::getTarjetaDeCredito)
                );
                //Controlador.<ContactoTrust>getTreeTableColumn("Nombre", ANCHO_NOMBRE, v->v.getNombre()));
            });
            ventanaPrincipalController = this;
            relacionarComponentes(TVSelecionarDirecciones, contactosTrusts, BClearDirecciones, BEditarDirecciones, BEliminarDirecciones, BInformacionDirecciones);
            rbsBusqueda = new JFXRadioButton[]{new JFXRadioButton("Todo"), new JFXRadioButton("Nombre"), new JFXRadioButton("Patrocinador"), new JFXRadioButton("Telefono"), new JFXRadioButton("Wallet"), new JFXRadioButton("Tarjeta")};
            rbsBusqueda[0].setSelected(true);
            tgBusqueda = new ToggleGroup();
            tgBusqueda.getToggles().addAll(rbsBusqueda);

            VisualFX.setRbsBusqueda(BBuscarDirecciones, rbsBusqueda, tgBusqueda);
            setPredicate(TVSelecionarDirecciones, TBuscarDirecciones, v -> {
                ContactoTrust c = (ContactoTrust) v;
                switch (getI(tgBusqueda)) {
                    case 0:
                        return c.getNombre() + " " + c.getPatrocinador() + " " + c.getTarjetaDeCredito() + " " + c.getTelefono() + " " + c.getWallet();

                    case 1:
                        return c.getNombre();
                    case 2:
                        return c.getPatrocinador();
                    case 3:
                        return c.getTelefono();
                    case 4:
                        return c.getWallet();
                    case 5:
                        return c.getTarjetaDeCredito();
                }
                return "";
            }, tgBusqueda, () -> rbsBusqueda[getI(tgBusqueda)].getText(), BBuscarDirecciones);//"Buscar por " +
            //BBuscarDirecciones.setContextMenu(new ContextMenu());
            //VisualFX.setTextBusqueda(TBuscarDirecciones, nombreEA);
        } catch (Exception ex) {
            responerException(ex);
        }
    }

    @Override
    public void actualizar() throws Exception {
        super.actualizar(); //To change body of generated methods, choose Tools | Templates.

        VisualFX.actualizarObs(contactosTrusts, getContactoTrustAll());
    }

//    private void actualizarRBsBusqueda() {
//        int indice = getI(tgBusqueda);
//        TBuscarDirecciones.setText("Buscar por " + rbsBusqueda[indice].getText());
//        switch (indice) {
//            case 0:
//
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                break;
//            case 5:
//                break;
//        }
//    }
}

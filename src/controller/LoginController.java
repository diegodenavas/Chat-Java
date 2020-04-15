package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    protected BorderPane base;

    @FXML
    private TextField tfUsuario, tfContraseña;
    @FXML
    private Label labelError;

    private Parent ventana1, ventana2;
    private Usuario usuario;



    //Método para el boton entrar del login
    public void Entrar(Event event) throws IOException {

        usuario = new Usuario();
        usuario.setNick(tfUsuario.getText());
        usuario.setContraseña(tfContraseña.getText());

        try {
            if(usuario.verificarLogin()){
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(new Scene(ventana1));

                //Metodo antigüo para abrir una ventana, es mejor el de arriba.
                /*
                ventana1 = FXMLLoader.load(getClass().getResource("/view/nuevaVentana.fxml"));
                base.setCenter(ventana1);
                base.getChildren().remove(anchorBase);
                 */
            }
            else labelError.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Método para el boton registrarse del login
    public void Registrarse(Event event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(new Scene(ventana2));
    }


    //Método que se carga en initialize() para dar al boton entrar al pulsar enter
    public void EntrarConEnter(){
        tfUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        Entrar(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tfContraseña.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        Entrar(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //Metodo obligatorio de la intefaz Initializable
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Precarga el método para entrar con la tecla enter
        EntrarConEnter();

        //Precarga de las ventanas a las que podemos acceder desde el login
        try {
            ventana1 = FXMLLoader.load(getClass().getResource("/view/nuevaVentana.fxml"));
            ventana2 = FXMLLoader.load(getClass().getResource("/view/registro.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package client.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.model.Usuario;

import java.io.IOException;


public class RegistroController{

    @FXML
    private TextField tfUsuario, tfContraseña, tfNombre, tfPrimerApellido, tfSegundoApellido;
    @FXML
    private Text errorLinea1, errorLinea2;

    private FXMLLoader fxmlLoader;
    private LoginController loginController;
    private Usuario usuario;








    public void Ingresar(Event event) throws IOException {
        if (tfUsuario.getText().length() > 20 || tfNombre.getText().length() > 20 ||
                tfPrimerApellido.getText().length() > 20 || tfSegundoApellido.getText().length() > 20) {
            errorLinea1.setText("* Los campos usuario, nombre y apellidos no pueden ");
            errorLinea1.setVisible(true);
            errorLinea2.setVisible(true);
            return;
        }

        if (tfContraseña.getText().length() > 30) {
            errorLinea1.setText("* El campo contraseña no puede tener mas de 30 caractéres");
            errorLinea1.setVisible(true);
            return;
        }

        usuario = new Usuario(tfUsuario.getText(), tfContraseña.getText(), tfNombre.getText(), tfPrimerApellido.getText(), tfSegundoApellido.getText());

        if (usuario.verificarRegistro()) {
            Stage newStage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/client/view/ventanaMensajes.fxml"));
                Scene scene = new Scene(root);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.toFront();
                appStage.show();

                //Así abrimos una ventana nueva.
                /*
                AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ventanaMensajes.fxml"));
                Scene scene = new Scene(page);
                newStage.setScene(scene);
                newStage.show();
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

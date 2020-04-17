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

    private Usuario usuario;



    public void Ingresar(Event event) throws IOException {

    }
}

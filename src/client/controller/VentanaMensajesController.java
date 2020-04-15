package client.controller;

import client.model.Mensaje;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class VentanaMensajesController implements Initializable {

    @FXML
    private TextArea tfVentanaChat;
    @FXML
    private TextField tfVentanaEnvio;

    public void Enviar() throws IOException {
        Socket socket = new Socket("192.168.1.35", 9999);
        ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());

        Mensaje mensaje = new Mensaje();
    }


    public void EnviarConEnter(){
        tfVentanaEnvio.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    EnviarMensaje();
                }
            }
        });
    }


    public void EnviarMensaje(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EnviarConEnter();
    }
}

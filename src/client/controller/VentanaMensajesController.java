package client.controller;

import client.model.Mensaje;
import client.model.Usuario;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class VentanaMensajesController implements Initializable, Runnable {

    @FXML
    private TextArea tfVentanaChat;
    @FXML
    private TextField tfVentanaEnvio;

    Usuario usuario;


    public VentanaMensajesController(){

    }



    public void EnviarMensaje() throws IOException {
        Socket socket = new Socket("192.168.1.35", 9999);
        ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());

        Mensaje mensaje = new Mensaje(usuario, tfVentanaEnvio.getText());

        objectInputStream.writeObject(mensaje);
        objectInputStream.close();
        socket.close();
    }



    public void RecibirMensaje(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        Socket socket= serverSocket.accept();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Mensaje mensaje = (Mensaje) objectInputStream.readObject();

        tfVentanaChat.appendText(mensaje.getTextoMensaje());
    }


    //Método para enviar el mensaje al pulsar la tecla enter.
    public void EnviarConEnter(){
        tfVentanaEnvio.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        EnviarMensaje();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EnviarConEnter();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9998);
            RecibirMensaje(serverSocket);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

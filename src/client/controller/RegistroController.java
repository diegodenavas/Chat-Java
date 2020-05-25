package client.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.model.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class RegistroController implements Initializable, Runnable{

    @FXML
    private TextField tfUsuario, tfContraseña, tfNombre, tfPrimerApellido, tfSegundoApellido;
    @FXML
    private Text errorLinea1, errorLinea2;

    private Parent ventana1;

    private Usuario usuario;
    private Boolean registroUsuario;
    private Thread thread;


    public void Ingresar(Event event) throws IOException {
        usuario = new Usuario(tfUsuario.getText(), tfContraseña.getText(), tfNombre.getText(), tfPrimerApellido.getText(), tfSegundoApellido.getText(), InetAddress.getLocalHost().getHostAddress());
        usuario.verificarRegistro();

        thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (registroUsuario != null) {
            if (registroUsuario) {
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(new Scene(ventana1));
            } else{
                errorLinea1.setVisible(true);
                errorLinea2.setVisible(true);
            }
        }
        else {
            errorLinea1.setText("Conexion fallida");
            errorLinea1.setVisible(true);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Precarga el método para entrar con la tecla enter


        //Precarga de las ventanas a las que podemos acceder desde el login
        try {
            ventana1 = FXMLLoader.load(getClass().getResource("/client/view/ventanaAmigos.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9993);

            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            registroUsuario = objectInputStream.readBoolean();

            objectInputStream.close();
            socket.close();
            serverSocket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

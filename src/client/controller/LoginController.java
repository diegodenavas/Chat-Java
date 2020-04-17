package client.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import client.model.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Runnable{

    @FXML
    protected BorderPane base;

    @FXML
    private TextField tfUsuario, tfContraseña;
    @FXML
    private Label labelError;

    private Parent ventana1, ventana2;
    private Usuario usuario;
    private Boolean verificacionLogin;
    private Thread thread;


    public LoginController(){

    }


    //Método para el boton entrar del login
    public void Entrar(Event event) throws IOException {

        usuario = new Usuario(tfUsuario.getText(), tfContraseña.getText(), InetAddress.getLocalHost().getHostAddress());
        usuario.verificarLogin();

        thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (verificacionLogin != null) {
            if (verificacionLogin) {
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(new Scene(ventana1));
            } else{
                labelError.setVisible(true);
            }
        }
        else {
            labelError.setText("Conexion fallida");
            labelError.setVisible(true);
        }
    }


    //Método para el boton registrarse del login
    public void Registrarse(Event event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(new Scene(ventana2));
    }


    //Método que se carga en initialize() para dar al boton entrar al pulsar enter
    public void EntrarConEnter() {
        tfUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
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
                if (event.getCode() == KeyCode.ENTER) {
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
            ventana1 = FXMLLoader.load(getClass().getResource("/client/view/ventanaAmigos.fxml"));
            ventana2 = FXMLLoader.load(getClass().getResource("/client/view/registro.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9997);

            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            verificacionLogin = objectInputStream.readBoolean();

            objectInputStream.close();
            socket.close();
            serverSocket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

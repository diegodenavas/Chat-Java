package client.controller;

import client.model.ConectaConServidor;
import client.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.Event;

import java.io.IOException;
import java.sql.*;

public class VentanaAmigosController {

    @FXML
    private Button amigo1, amigo2, amigo3, amigo4, amigo5, amigo6, amigo7, amigo8, amigo9, amigo10, amigo11, amigo12;
    @FXML
    private TextField tfBuscarAmigo;
    @FXML
    private Label labelNoEncuentra;

    private Usuario usuario;
    private Button[] arrayButton;

    public VentanaAmigosController(){
        arrayButton = new Button[]{amigo1, amigo2, amigo3, amigo4, amigo5, amigo6, amigo7, amigo8, amigo9, amigo10, amigo11, amigo12};
    }




    public void AñadirAmigo() throws SQLException {
        String amigo = tfBuscarAmigo.getText();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT nick FROM usuario WHERE nick = '" + amigo + "'");

        while (resultSet.next()){
            if(resultSet.getString(1).equals(amigo)){
                amigo1.setText(amigo);
                amigo1.setVisible(true);
            }
            else labelNoEncuentra.setVisible(true);
        }
    }


    public void EntrarAlChat(Event event) throws IOException, SQLException {

        Object botonPulsado = event.getSource();
        usuario = new Usuario();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        for(int i = 0; i < arrayButton.length; i++)
            if(botonPulsado == arrayButton[i]){ 
                resultSet = statement.executeQuery("SELECT nick, ip FROM usuario WHERE nick='" + arrayButton[i].getText() + "'");
        }

        while (resultSet.next()){
            usuario.setNick(resultSet.getString(1));
            usuario.setIp(resultSet.getString(2));
        }

        resultSet.close();
        statement.close();
        connection.close();

        Parent parent = FXMLLoader.load(getClass().getResource("/client/view/ventanaMensajes.fxml"));

        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(new Scene(parent));
    }
}

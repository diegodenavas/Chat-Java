package model;

import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class Usuario {

    private String nick, contraseña, nombre, apellido1, apellido2, ip;

    //Constructor para el login ya que usamos los setters de nombre y contraseña
    public Usuario(){

    }

    //Constructor para el registro


    public Usuario(String nick, String contraseña, String nombre, String apellido1, String apellido2) {
        this.nick = nick;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public boolean verificarLogin() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT nick, contraseña FROM usuario where nick='"+getNick()+"' and contraseña='"+getContraseña()+"'");
        while (resultSet.next()){
            if(resultSet.getString(1).equalsIgnoreCase(getNick()) && resultSet.getString(2).equalsIgnoreCase(getContraseña())){
                statement.close();
                connection.close();
                return true;
            }
            else{
                statement.close();
                connection.close();
                return false;
            }
        }
        statement.close();
        connection.close();
        return false;
    }


    public boolean verificarRegistro(){

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT nick FROM usuario WHERE nick='" + nick + "'");

            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(nick)) {
                    statement.close();
                    connection.close();
                    return false;
                }
            }

            statement.executeUpdate("INSERT INTO usuario(nick, contraseña, nombre, apellido1, apellido2) VALUES('" + nick + "', '" + contraseña + "', '" + nombre + "', '" + apellido1 + "', '" + apellido2 + "')");

            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Interacción con la base de datos fallida");
            return false;
        }

        return true;
    }




    // Getters && Setters
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

package client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Usuario implements Serializable, Runnable{

    private String nick, contraseña, nombre, apellido1, apellido2, ip;


    //Constructor sin datos por si hiciese falta
    public  Usuario(){}

    //Constructor para el login ya que usamos los setters de nombre y contraseña
    public Usuario(String nick, String contraseña, String ip){
        this.nick = nick;
        this.contraseña = contraseña;
        this.ip = ip;
    }

    //Constructor para el registro con todos los datos
    public Usuario(String nick, String contraseña, String nombre, String apellido1, String apellido2, String ip) {
        this.nick = nick;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.ip = ip;
    }


    public void verificarLogin() throws IOException {
        Socket socket = new Socket("192.168.1.35", 9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        socket.close();
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



    @Override
    public void run() {


    }
}

package server;

import client.model.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class VerificaLogin implements Runnable{

    Usuario usuario;

    public VerificaLogin(){

    }


    public boolean verificarLogin(Usuario usuario) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT nick, contraseña FROM usuario where nick='" + usuario.getNick() + "' and contraseña='" + usuario.getContraseña() + "'");
        while (resultSet.next()){
            if(resultSet.getString(1).equals(usuario.getNick()) && resultSet.getString(2).equals(usuario.getContraseña())){
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



    @Override
    public void run() {
        try {
            while (true) {
                ServerSocket serverSocket = new ServerSocket(9999);
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                usuario = (Usuario)objectInputStream.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

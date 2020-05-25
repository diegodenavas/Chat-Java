package server;

import client.model.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class VerificaRegistro implements Runnable{
    Usuario usuario;


    public boolean verificarRegistro(Usuario usuario) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT nick FROM usuario where nick='" + usuario.getNick()+ "'");
        if (resultSet.next()){

            resultSet.close();
            statement.close();
            connection.close();
            return false;
        }
        else {
            statement.executeUpdate("INSERT INTO usuario(nick, contraseña, nombre, apellido1, apellido2, ip) VALUES ('" + usuario.getNick() + "', '" +
                    usuario.getContraseña() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido1() + "', '" + usuario.getApellido2() +
                    "', '" + usuario.getIp() + "')");
            statement.close();
            connection.close();
            return true;
        }
    }



    @Override
    public void run() {
        try {
            while (true) {
                ServerSocket serverSocket = new ServerSocket(9996);

                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                usuario = (Usuario) objectInputStream.readObject();

                //areatexto.append(usuario.getNick() + ":" + usuario.getContraseña());
                objectInputStream.close();
                socket.close();

                Socket salida = new Socket("192.168.1.35", 9995);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(salida.getOutputStream());
                objectOutputStream.writeBoolean(verificarRegistro(usuario));

                objectOutputStream.close();
                salida.close();
                serverSocket.close();
                return;
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

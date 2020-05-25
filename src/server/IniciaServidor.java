package server;

import client.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class IniciaServidor extends JFrame implements Runnable{

    private	JTextArea areatexto;
    Usuario usuario;

    public IniciaServidor(){
        setBounds(1200,300,280,350);

        JPanel milamina= new JPanel();
        milamina.setLayout(new BorderLayout());

        areatexto=new JTextArea();
        milamina.add(areatexto,BorderLayout.CENTER);

        add(milamina);

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        try {
            while (true) {
                ServerSocket serverSocket = new ServerSocket(9999);

                VerificaLogin verificaLogin = new VerificaLogin();
                VerificaRegistro verificaRegistro = new VerificaRegistro();

                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                usuario = (Usuario) objectInputStream.readObject();


                if (verificaLogin.verificarLogin(usuario)) areatexto.append("Verificado correctamente\n");
                else areatexto.append("no verificado\n");


                //areatexto.append(usuario.getNick() + ":" + usuario.getContraseña());
                objectInputStream.close();
                socket.close();

                Socket salida = new Socket("192.168.1.35", 9997);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(salida.getOutputStream());
                objectOutputStream.writeBoolean(verificaLogin.verificarLogin(usuario));

                objectOutputStream.close();
                salida.close();
                serverSocket.close();
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

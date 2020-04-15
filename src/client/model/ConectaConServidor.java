package client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConectaConServidor {


    public void EnviaUsuarioAServidor(Usuario usuario) throws IOException {

        Socket socket = new Socket("192.168.1.35", 9999);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(usuario);
    }
}

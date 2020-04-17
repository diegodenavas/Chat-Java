package client.model;

import java.io.Serializable;

public class Mensaje implements Serializable {

    private Usuario usuario;
    private String textoMensaje;


    public Mensaje(Usuario usuario, String textoMensaje){
        this.usuario = usuario;
        this.textoMensaje = textoMensaje;
    }




    //Getters && Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public void setTextoMensaje(String textoMensaje) {
        this.textoMensaje = textoMensaje;
    }
}

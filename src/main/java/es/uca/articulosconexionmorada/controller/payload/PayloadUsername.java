package es.uca.articulosconexionmorada.controller.payload;

public class PayloadUsername {

    public String uuid;

    public String username;

    public String token;

    public PayloadUsername(String uuid, String username, String token) {
        this.uuid = uuid;
        this.username = username;
        this.token = token;
    }
}

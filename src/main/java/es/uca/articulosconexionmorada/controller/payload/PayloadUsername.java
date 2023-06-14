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

    public PayloadUsername() {}

    //getters and setters
    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

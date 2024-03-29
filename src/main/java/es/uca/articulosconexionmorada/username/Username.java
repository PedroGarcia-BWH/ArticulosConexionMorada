package es.uca.articulosconexionmorada.username;

import com.sun.istack.NotNull;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Username {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID Id;

    @Column(unique=true)
    @NotNull
    private String username;


    @NotNull
    private String firebaseId;


    @NotNull
    private String firebaseToken;

    public Username() {}

    public Username(String username, String firebaseId, String firebaseToken) {
        this.username = username;
        this.firebaseId = firebaseId;
        this.firebaseToken = firebaseToken;
    }

    public UUID getId() {
        return Id;
    }
    public void setId(UUID id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}

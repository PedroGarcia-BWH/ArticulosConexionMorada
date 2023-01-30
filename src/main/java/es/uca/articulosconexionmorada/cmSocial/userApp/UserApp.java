package es.uca.articulosconexionmorada.cmSocial.userApp;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class UserApp {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column
    private String firebaseUUID;

    public UserApp(){}

    public UserApp(String firebaseUUID) {
        this.firebaseUUID = firebaseUUID;
    }

    //getters
    public UUID getId() {
        return id;
    }
    public String getFirebaseUUID() {
        return firebaseUUID;
    }

    //setters
    public void setFirebaseUUID(String firebaseUUID) {
        this.firebaseUUID = firebaseUUID;
    }
}

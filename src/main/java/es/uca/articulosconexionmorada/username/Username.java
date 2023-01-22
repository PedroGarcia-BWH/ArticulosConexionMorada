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

    public Username() {}

    public Username(String username) {
        this.username = username;
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
}

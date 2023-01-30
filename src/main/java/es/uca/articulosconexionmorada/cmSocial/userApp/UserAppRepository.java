package es.uca.articulosconexionmorada.cmSocial.userApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, UUID> {
    UserApp findByFirebaseUUID(String firebaseUUID);
}

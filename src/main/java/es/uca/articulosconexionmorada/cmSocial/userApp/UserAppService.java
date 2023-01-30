package es.uca.articulosconexionmorada.cmSocial.userApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    public UserApp findByFirebaseUUID(String firebaseUUID){
        return userAppRepository.findByFirebaseUUID(firebaseUUID);
    }

    public UserApp save(UserApp userApp){
        return userAppRepository.save(userApp);
    }

}

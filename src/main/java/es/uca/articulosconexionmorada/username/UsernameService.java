package es.uca.articulosconexionmorada.username;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsernameService {
    private UsernameRepository usernameRepository;

    public UsernameService(UsernameRepository usernameRepository) {
        this.usernameRepository = usernameRepository;
    }

    public Optional<Username> findByUsername(String username) {
        return usernameRepository.findByUsername(username);
    }

    public void save(Username username) {
        usernameRepository.save(username);
    }

    public void delete(Username username) {
        usernameRepository.delete(username);
    }

    public Username findByUuid(String uuid) {
        return usernameRepository.findByFirebaseId(uuid);
    }

    public List<Username> finbByUsernameContainingIgnoreCase(String username) {
        return usernameRepository.findByUsernameContainingIgnoreCase(username);
    }

    public Username findByFirebaseId(String firebaseId) {
        return usernameRepository.findByFirebaseId(firebaseId);
    }

    public Optional<Username> findByToken(String token) {
        return usernameRepository.findByFirebaseToken(token);
    }

}

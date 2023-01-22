package es.uca.articulosconexionmorada.username;

import org.springframework.stereotype.Service;

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


}

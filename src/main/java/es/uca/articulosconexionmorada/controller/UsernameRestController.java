package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.controller.payload.PayloadUsername;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UsernameRestController {
    @Autowired
    private UsernameService usernameService;

    @PostMapping("/addUsername")
    public boolean addUsername(@RequestBody PayloadUsername payloadUsername){
        System.out.println("Username: " + payloadUsername.username + " FirebaseId: " + payloadUsername.uuid);
        if(usernameService.findByUsername(payloadUsername.username).isPresent()) return false;
        if(usernameService.findByToken(payloadUsername.token).isPresent()){
            Optional<Username> usernamePresent = usernameService.findByToken(payloadUsername.token);
            usernamePresent.get().setFirebaseToken("null");
        }
        //replace ""
        payloadUsername.username = payloadUsername.username.replace("\"", "");
        System.out.println("username add: " + payloadUsername.username);
        Username user = new Username(payloadUsername.username, payloadUsername.uuid, payloadUsername.token);
        usernameService.save(user);
        return true;
    }

    @GetMapping("/getUsername/{username}")
    public boolean getUsername(@PathVariable String username) {
        System.out.println("username: " + username);
        return usernameService.findByUsername(username).isPresent();
    }

    @DeleteMapping("/deleteUsername/{username}")
    public boolean deleteUsername(@PathVariable String username) {
        System.out.println("username delete: " + username);
        if(usernameService.findByUsername(username).isPresent()) {
            usernameService.delete(usernameService.findByUsername(username).get());
            return true;
        }
        return false;
    }

    @PutMapping("/update/token")
    public boolean updateToken(@RequestBody PayloadUsername payloadUsername) {
        Username username = usernameService.findByFirebaseId(payloadUsername.uuid);
        if(username != null) {
            if(usernameService.findByToken(payloadUsername.token).isPresent()) {
                Optional<Username> usernamePresent = usernameService.findByToken(payloadUsername.token);
                usernamePresent.get().setFirebaseToken("null");
            }
            username.setFirebaseToken(payloadUsername.token);
            usernameService.save(username);
            System.out.println("token updated");
        }
        return false;
    }
}

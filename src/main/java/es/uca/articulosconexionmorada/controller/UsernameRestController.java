package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.controller.payload.PayloadUsername;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsernameRestController {
    @Autowired
    private UsernameService usernameService;

    @PostMapping("/addUsername")
    public boolean addUsername(@RequestBody PayloadUsername payloadUsername){
        System.out.println("Username: " + payloadUsername.username + " FirebaseId: " + payloadUsername.uuid);
        if(usernameService.findByUsername(payloadUsername.username).isPresent()) return false;
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
}

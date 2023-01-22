package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsernameRestController {
    @Autowired
    private UsernameService usernameService;

    @PostMapping("/addUsername")
    public boolean addUsername(@RequestBody String username) {
        if(usernameService.findByUsername(username).isPresent()) return false;
        System.out.println("username add: " + username);
        Username user = new Username(username);
        usernameService.save(user);
        return true;
    }

    @GetMapping("/getUsername/{username}")
    public boolean getUsername(@PathVariable String username) {
        System.out.println("username: " + username);
        return usernameService.findByUsername(username).isPresent();
    }
}

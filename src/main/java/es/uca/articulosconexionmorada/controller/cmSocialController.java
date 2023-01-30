package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppRepository;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cmSocialController {

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private HiloService hiloService;

    @Autowired
    private SeguidoresService seguidoresService;

    @PostMapping("/addUserUuid")
    public Boolean addUserUuid(String uuid){
        UserApp userApp = new UserApp(uuid);
        userAppService.save(userApp);
        return true;
    }

    
}

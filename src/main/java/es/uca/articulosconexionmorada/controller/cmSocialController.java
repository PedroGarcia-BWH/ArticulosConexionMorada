package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppRepository;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/getlastHilos")
    public List<Hilo> getlastHilos(){
        return hiloService.findByHiloPadreIsNullOrderByDateCreation();
    }

    @GetMapping("/getSeguidores/{uuid}")
    public int getSeguidores(@PathVariable String uuid){
        UserApp userApp = userAppService.findByFirebaseUUID(uuid);
        return seguidoresService.countBySeguidores(userApp);
    }

    @GetMapping("/getSeguidos/{uuid}")
    public int getSeguidos(@PathVariable String uuid){
        UserApp userApp = userAppService.findByFirebaseUUID(uuid);
        return seguidoresService.countBySeguido(userApp);
    }
}

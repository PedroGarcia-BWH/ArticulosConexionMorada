package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.dislike.Dislike;
import es.uca.articulosconexionmorada.cmSocial.dislike.DislikeService;
import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.like.Like;
import es.uca.articulosconexionmorada.cmSocial.like.LikeRepository;
import es.uca.articulosconexionmorada.cmSocial.like.LikeService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppRepository;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class cmSocialController {

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private HiloService hiloService;

    @Autowired
    private SeguidoresService seguidoresService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private DislikeService dislikeService;

    @PostMapping("/add/UserUuid")
    public Boolean addUserUuid(String uuid){
        UserApp userApp = new UserApp(uuid);
        userAppService.save(userApp);
        return true;
    }

    @GetMapping("/get/lastHilos/{uuid}")
    public List<PayloadHilo> getlastHilos(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByHiloPadreIsNullOrderByDateCreation();

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();

        for(Hilo hilo : hilos){
            PayloadHilo payloadHilo = new PayloadHilo(hilo, likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.likeExists(hilo, userAppService.findByFirebaseUUID(uuid)), dislikeService.dislikeExists(hilo,
                    userAppService.findByFirebaseUUID(uuid)));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;
    }

    @GetMapping("/get/lastHilosSeguidos/{uuid}")
    public List<PayloadHilo> getLastHilosSeguidos(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByAutorAndHiloPadreIsNullOrderByDateCreation(userAppService.findByFirebaseUUID(uuid));

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        for(Hilo hilo: hilos){
            PayloadHilo payloadHilo = new PayloadHilo(hilo, likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.likeExists(hilo, userAppService.findByFirebaseUUID(uuid)), dislikeService.dislikeExists(hilo,
                    userAppService.findByFirebaseUUID(uuid)));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;

    }

    @PostMapping("/add/hilo")
    public void addHilo(Hilo hilo){
        hiloService.save(hilo);
    }

    @DeleteMapping("/delete/hilo")
    public void deleteHilo(Hilo hilo){
        hilo.setDateElimination(new Date());
        hiloService.save(hilo);
    }

    @GetMapping("/get/Seguidores/{uuid}")
    public int getSeguidores(@PathVariable String uuid){
        UserApp userApp = userAppService.findByFirebaseUUID(uuid);
        return seguidoresService.countBySeguidores(userApp);
    }

    @GetMapping("/get/Seguidos/{uuid}")
    public int getSeguidos(@PathVariable String uuid){
        UserApp userApp = userAppService.findByFirebaseUUID(uuid);
        return seguidoresService.countBySeguido(userApp);
    }

    @PostMapping("/add/like")
    public void addLike(Hilo hilo, String uuid){
        Like like = new Like(hilo, userAppService.findByFirebaseUUID(uuid));
        likeService.save(like);
    }

    @PostMapping("/add/dislike")
    public void addDislike(Hilo hilo, String uuid){
        Dislike disLike = new Dislike(hilo, userAppService.findByFirebaseUUID(uuid));
        dislikeService.save(disLike);
    }

    @DeleteMapping("/delete/like")
    public void deleteLike(Hilo hilo, String uuid){
        Optional<Like> like = likeService.findByHiloAndUserApp(hilo, userAppService.findByFirebaseUUID(uuid));
        if(like.isPresent()){
            likeService.delete(like.get());
        }
    }

    @DeleteMapping("/delete/dislike")
    public void deleteDislike(Hilo hilo, String uuid){
        Optional<Dislike> dislike = dislikeService.findByHiloAndUserApp(hilo, userAppService.findByFirebaseUUID(uuid));
        if(dislike.isPresent()){
            dislikeService.delete(dislike.get());
        }
    }
}

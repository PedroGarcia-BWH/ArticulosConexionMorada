package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.dislike.Dislike;
import es.uca.articulosconexionmorada.cmSocial.dislike.DislikeService;
import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.like.Like;
import es.uca.articulosconexionmorada.cmSocial.like.LikeRepository;
import es.uca.articulosconexionmorada.cmSocial.like.LikeService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameRepository;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class cmSocialController {

    @Autowired
    private UsernameService usernameService;

    @Autowired
    private HiloService hiloService;

    @Autowired
    private SeguidoresService seguidoresService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private DislikeService dislikeService;
    @Autowired
    private UsernameRepository usernameRepository;

    @GetMapping("/get/lastHilos/{uuid}")
    public List<PayloadHilo> getlastHilos(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByHiloPadreIsNullOrderByDateCreation();

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;
        for(Hilo hilo : hilos){

            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();

            PayloadHilo payloadHilo = new PayloadHilo(hilo.getAutor().getFirebaseId(),hilo.getMensaje(), hiloPadreUuid , hilo.getDateCreation(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.likeExists(hilo, usernameService.findByFirebaseId(uuid)), dislikeService.dislikeExists(hilo,
                    usernameService.findByFirebaseId(uuid)));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;
    }

    @GetMapping("/get/lastHilosSeguidos/{uuid}")
    public List<PayloadHilo> getLastHilosSeguidos(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByAutorAndHiloPadreIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;

        for(Hilo hilo: hilos){

            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();

            System.out.println("Hilo: " + hilo.getMensaje() + " - " + hilo.getAutor().getFirebaseId());

            PayloadHilo payloadHilo = new PayloadHilo(hilo.getAutor().getFirebaseId(),hilo.getMensaje(), hiloPadreUuid, hilo.getDateCreation(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.likeExists(hilo, usernameService.findByFirebaseId(uuid)), dislikeService.dislikeExists(hilo,
                    usernameService.findByFirebaseId(uuid)));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;

    }

    @PostMapping("/add/hilo")
    public void addHilo(@RequestBody PayloadHilo payloadHilo){
        System.out.println(payloadHilo.getAutorUuid());
        Hilo hilo = new Hilo(usernameService.findByFirebaseId(payloadHilo.getAutorUuid()), payloadHilo.getMensaje(), null);
        hiloService.save(hilo);
    }

    @DeleteMapping("/delete/hilo")
    public void deleteHilo(Hilo hilo){
        hilo.setDateElimination(new Date());
        hiloService.save(hilo);
    }

    @GetMapping("/get/Seguidores/{uuid}")
    public int getSeguidores(@PathVariable String uuid){
        Username userApp = usernameService.findByFirebaseId(uuid);
        return seguidoresService.countBySeguidores(userApp);
    }

    @GetMapping("/get/Seguidos/{uuid}")
    public int getSeguidos(@PathVariable String uuid){
        Username userApp = usernameService.findByFirebaseId(uuid);
        return seguidoresService.countBySeguido(userApp);
    }

    @PostMapping("/add/like")
    public void addLike(Hilo hilo, String uuid){
        Like like = new Like(hilo, usernameService.findByFirebaseId(uuid));
        likeService.save(like);
    }

    @PostMapping("/add/dislike")
    public void addDislike(Hilo hilo, String uuid){
        Dislike disLike = new Dislike(hilo, usernameService.findByFirebaseId(uuid));
        dislikeService.save(disLike);
    }

    @DeleteMapping("/delete/like")
    public void deleteLike(Hilo hilo, String uuid){
        Optional<Like> like = likeService.findByHiloAndUserApp(hilo, usernameService.findByFirebaseId(uuid));
        if(like.isPresent()){
            likeService.delete(like.get());
        }
    }

    @DeleteMapping("/delete/dislike")
    public void deleteDislike(Hilo hilo, String uuid){
        Optional<Dislike> dislike = dislikeService.findByHiloAndUserApp(hilo, usernameService.findByFirebaseId(uuid));
        if(dislike.isPresent()){
            dislikeService.delete(dislike.get());
        }
    }

    @GetMapping("/search/hilos/{search}")
    public List<PayloadHilo> searchHilos(@PathVariable String search){
        List<Hilo> hilos = hiloService.findByMensajeContainingIgnoreCase(search);
        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;

        for (Hilo hilo : hilos){
            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();
            else hiloPadreUuid = null;
            PayloadHilo payloadHilo = new PayloadHilo(hilo.getAutor().getFirebaseId(), hilo.getMensaje(), hiloPadreUuid, hilo.getDateCreation(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo), false, false);
            payloadHilos.add(payloadHilo);
        }
        return payloadHilos;
    }

    @GetMapping("/search/usuarios/{search}")
    public List<PayloadUsername> searchUsuarios(@PathVariable String search){
        List<PayloadUsername> payloadUsernames = new ArrayList<PayloadUsername>();
        for (Username username : usernameService.finbByUsernameContainingIgnoreCase(search)) {
              payloadUsernames.add(new PayloadUsername(username.getFirebaseId(), username.getUsername()));

        }
        return payloadUsernames;
    }
}

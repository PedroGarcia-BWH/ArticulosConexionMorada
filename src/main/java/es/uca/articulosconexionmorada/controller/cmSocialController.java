package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.dislike.Dislike;
import es.uca.articulosconexionmorada.cmSocial.dislike.DislikeService;
import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.like.Like;
import es.uca.articulosconexionmorada.cmSocial.like.LikeService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificacionHilo;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificacionPersona;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificationPersonaService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.Seguidores;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;;
import es.uca.articulosconexionmorada.controller.payload.PayloadHilo;
import es.uca.articulosconexionmorada.controller.payload.PayloadSeguidores;
import es.uca.articulosconexionmorada.controller.payload.PayloadUsername;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameRepository;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private NotificationHiloService notificacionHiloService;

    @Autowired
    private NotificationPersonaService notificacionPersonaService;

    @GetMapping("/get/lastHilos/{uuid}")
    public List<PayloadHilo> getlastHilos(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByHiloPadreIsNullOrderByDateCreation();

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;
        for(Hilo hilo : hilos){

            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();

            PayloadHilo payloadHilo = new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(),hilo.getMensaje(), hiloPadreUuid , hilo.getDateCreation().toString(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.getLike(hilo.getId().toString(), uuid), dislikeService.getDislike(hilo.getId().toString(), uuid));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;
    }

    //version placeholder
    @GetMapping("/get/lastHilosSeguidos/{uuid}")
    public List<PayloadHilo> getLastHilosSeguidos(@PathVariable String uuid){
        List<Seguidores> seguidores = seguidoresService.findBySeguido(usernameService.findByFirebaseId(uuid));

        System.out.println("seguidores: " + seguidores.size());

        List<Hilo> hilos = new ArrayList<Hilo>();

        for(Seguidores seguidor : seguidores){
            hilos.addAll(hiloService.findByAutorOrderByDateCreation(seguidor.getSeguidor()));
        }

        System.out.println("Hilos: " + hilos.size());

        //Ordenamos los hilos por fecha de creacion
        Collections.sort(hilos, new Comparator<Hilo>() {
            public int compare(Hilo o1, Hilo o2) {
                return o1.getDateCreation().compareTo(o2.getDateCreation());
            }
        });

        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;

        for(Hilo hilo: hilos){

            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();

            //System.out.println("Hilo: " + hilo.getMensaje() + " - " + hilo.getAutor().getFirebaseId() + " - " + hilo.getId().toString());

            PayloadHilo payloadHilo = new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(),hilo.getMensaje(), hiloPadreUuid, hilo.getDateCreation().toString(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.getLike(hilo.getId().toString(), uuid), dislikeService.getDislike(hilo.getId().toString(), uuid));
            payloadHilos.add(payloadHilo);
        }

        return payloadHilos;

    }

    @PostMapping("/add/hilo")
    public void addHilo(@RequestBody PayloadHilo payloadHilo){
        if(payloadHilo.getHiloPadreUuid() != null) {
            Optional<Hilo> hiloPadre = hiloService.findById(UUID.fromString(payloadHilo.getHiloPadreUuid()));
            if (hiloPadre.isPresent()) {
                NotificacionHilo notificacionHilo = new NotificacionHilo(hiloPadre.get().getAutor(), hiloPadre.get(), "te ha respondido en un hilo", new Date(), null);
                notificacionHiloService.save(notificacionHilo);
            }
        }
        Hilo hilo = new Hilo(usernameService.findByFirebaseId(payloadHilo.getAutorUuid()), payloadHilo.getMensaje(), payloadHilo.getHiloPadreUuid() != null ? hiloService.findById(UUID.fromString(payloadHilo.getHiloPadreUuid())).get() : null);
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

    @GetMapping("/get/SeguidorExist/{uuidSeguidor}/{uuidSeguido}")
    public boolean getSeguidorExist(@PathVariable String uuidSeguidor, @PathVariable String uuidSeguido){
        Username seguidor = usernameService.findByFirebaseId(uuidSeguidor);
        Username seguido = usernameService.findByFirebaseId(uuidSeguido);
        Optional<Seguidores> seguidores = seguidoresService.findBySeguidorAndSeguido(seguidor, seguido);
        return seguidores.isPresent();
    }


    @PostMapping("/add/Seguidor")
    public void addSeguidor(@RequestBody PayloadSeguidores payloadSeguidores){
        System.out.println("Seguidor: ");
        Username seguidor = usernameService.findByFirebaseId(payloadSeguidores.seguidorUuid);
        Username seguido = usernameService.findByFirebaseId(payloadSeguidores.seguidoUuid);

        Optional<Seguidores> opSeguidores = seguidoresService.findBySeguidorAndSeguido(seguidor, seguido);
        System.out.println("Seguidor: " + seguidor.getFirebaseId() + " - Seguido: " + seguido.getFirebaseId());
        if(!opSeguidores.isPresent()){
            Seguidores seguidores = new Seguidores(seguidor, seguido);
            seguidoresService.save(seguidores);

            NotificacionPersona notificacionPersona = new NotificacionPersona(seguidor,seguido,"te ha seguido", new Date(), null);
            notificacionPersonaService.save(notificacionPersona);
        }
    }

    @DeleteMapping("/delete/Seguidor/{uuidSeguidor}/{uuidSeguido}")
    public void deleteSeguidor(@PathVariable String uuidSeguidor, @PathVariable String uuidSeguido){
        Username seguidor = usernameService.findByFirebaseId(uuidSeguidor);
        Username seguido = usernameService.findByFirebaseId(uuidSeguido);
        Optional<Seguidores> seguidores = seguidoresService.findBySeguidorAndSeguido(seguidor, seguido);

        if(seguidores.isPresent()) seguidoresService.delete(seguidores.get());
    }


    @PostMapping("/add/like")
    public void addLike(@RequestBody PayloadHilo payloadHilo){
        Like like = new Like(hiloService.findById(UUID.fromString(payloadHilo.getIdHilo())).get(), usernameService.findByFirebaseId(payloadHilo.getAutorUuid()));

        if(!likeService.likeExists(like.getHilo(), like.getUserApp())){
            likeService.save(like);

            NotificacionHilo notificacionHilo = new NotificacionHilo(like.getHilo().getAutor(), like.getHilo(), "le gusta tu hilo", new Date(), null);
            notificacionHiloService.save(notificacionHilo);
        }

    }

    @PostMapping("/add/dislike")
    public void addDislike(@RequestBody PayloadHilo payloadHilo){
        Dislike disLike = new Dislike(hiloService.findById(UUID.fromString(payloadHilo.getIdHilo())).get(), usernameService.findByFirebaseId(payloadHilo.getAutorUuid()));

        if(!dislikeService.dislikeExists(disLike.getHilo(), disLike.getUserApp())){
            dislikeService.save(disLike);

            NotificacionHilo notificacionHilo = new NotificacionHilo(disLike.getHilo().getAutor(), disLike.getHilo(), "no le gusta tu hilo", new Date(), null);
            notificacionHiloService.save(notificacionHilo);
        }
    }

    @DeleteMapping("/delete/like/{idHilo}/{uuid}")
    public void deleteLike(@PathVariable String idHilo,@PathVariable String uuid){
        Optional<Like> like = likeService.findByHiloAndUserApp(hiloService.findById(UUID.fromString(idHilo)).get(), usernameService.findByFirebaseId(uuid));
        if(like.isPresent()){
            likeService.delete(like.get());
        }
    }

    @DeleteMapping("/delete/dislike/{idHilo}/{uuid}")
    public void deleteDislike(@PathVariable String idHilo,@PathVariable String uuid){
        Optional<Dislike> dislike = dislikeService.findByHiloAndUserApp(hiloService.findById(UUID.fromString(idHilo)).get(), usernameService.findByFirebaseId(uuid));
        if(dislike.isPresent()){
            dislikeService.delete(dislike.get());
        }
    }

    @GetMapping("/get/like/{idHilo}/{uuid}")
    public boolean getLike(@PathVariable String idHilo,@PathVariable String uuid){
        return likeService.getLike(idHilo, uuid);
    }

    @GetMapping("/get/dislike/{idHilo}/{uuid}")
    public boolean getDislike(@PathVariable String idHilo,@PathVariable String uuid){
        return dislikeService.getDislike(idHilo, uuid);
    }

    @GetMapping("/search/hilos/{search}/{uuid}")
    public List<PayloadHilo> searchHilos(@PathVariable String search, @PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByMensajeContainingIgnoreCase(search);
        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;

        for (Hilo hilo : hilos){
            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();
            else hiloPadreUuid = null;
            PayloadHilo payloadHilo = new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(), hilo.getMensaje(), hiloPadreUuid, hilo.getDateCreation().toString(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo),
                    likeService.getLike(hilo.getId().toString(), uuid), dislikeService.getDislike(hilo.getId().toString(), uuid));
            payloadHilos.add(payloadHilo);
        }
        return payloadHilos;
    }

    @GetMapping("/search/usuarios/{search}")
    public List<PayloadUsername> searchUsuarios(@PathVariable String search){
        List<PayloadUsername> payloadUsernames = new ArrayList<PayloadUsername>();
        for (Username username : usernameService.finbByUsernameContainingIgnoreCase(search)) {
              payloadUsernames.add(new PayloadUsername(username.getFirebaseId(), username.getUsername(), username.getFirebaseToken()));

        }
        return payloadUsernames;
    }

    @GetMapping("/get/hilos/{uuid}")
    public List<PayloadHilo> getHilosUser(@PathVariable String uuid){
        List<Hilo> hilos = hiloService.findByAutorOrderByDateCreation(usernameService.findByFirebaseId(uuid));
        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();
        String hiloPadreUuid = null;

        for (Hilo hilo : hilos){
            if(hilo.getHiloPadre() != null) hiloPadreUuid = hilo.getHiloPadre().getId().toString();
            else hiloPadreUuid = null;
            PayloadHilo payloadHilo = new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(), hilo.getMensaje(), hiloPadreUuid, hilo.getDateCreation().toString(),
                    likeService.countByHilo(hilo), dislikeService.countByHilo(hilo), false, false);
            payloadHilos.add(payloadHilo);
        }
        return payloadHilos;
    }

    @GetMapping("/get/respuesta/{idHilo}/{uuid}")
    public List<PayloadHilo> getRespuestas(@PathVariable String idHilo, @PathVariable String uuid){
        System.out.println("ID HILO: " + idHilo);
        Optional<Hilo> hiloSelected = hiloService.findById(UUID.fromString(idHilo));
        List<Hilo> hilos = new ArrayList<Hilo>();
        List<PayloadHilo> payloadHilos = new ArrayList<PayloadHilo>();

        if(hiloSelected.isPresent()){

            if(hiloSelected.get().getHiloPadre() == null) {
                payloadHilos.add(new PayloadHilo(hiloSelected.get().getId().toString(), hiloSelected.get().getAutor().getFirebaseId(), hiloSelected.get().getMensaje(),
                        null, hiloSelected.get().getDateCreation().toString(),
                        likeService.countByHilo(hiloSelected.get()), dislikeService.countByHilo(hiloSelected.get()),
                        likeService.getLike(idHilo, uuid), dislikeService.getDislike(idHilo, uuid)));

                hilos = hiloService.findByHiloPadreOrderByDateCreation(hiloSelected.get());

            }else {
                Hilo hilo = hiloService.findById(hiloSelected.get().getHiloPadre().getId()).get();

                payloadHilos.add(new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(), hilo.getMensaje(),
                        null, hilo.getDateCreation().toString(),
                        likeService.countByHilo(hilo), dislikeService.countByHilo(hilo), false, false));

                hilos = hiloService.findByHiloPadreOrderByDateCreation(hilo);

            }

            for (Hilo hilo : hilos){
                PayloadHilo payloadHilo = new PayloadHilo(hilo.getId().toString(), hilo.getAutor().getFirebaseId(), hilo.getMensaje(),
                        hilo.getHiloPadre().getId().toString(), hilo.getDateCreation().toString(),
                        likeService.countByHilo(hilo), dislikeService.countByHilo(hilo), false, false);
                payloadHilos.add(payloadHilo);
            }

        }
        return payloadHilos;
    }
}

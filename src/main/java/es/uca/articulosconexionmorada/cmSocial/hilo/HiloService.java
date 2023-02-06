package es.uca.articulosconexionmorada.cmSocial.hilo;

import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HiloService {

     @Autowired
     private HiloRepository hiloRepository;

     public Hilo save(Hilo hilo){
         return hiloRepository.save(hilo);
     }

     public void delete(Hilo hilo){
         hilo.setDateElimination(new Date());
         hiloRepository.save(hilo);
     }

     public List<Hilo> findByAutorAndHiloPadreIsNullOrderByDateCreation(UserApp userApp){
         return hiloRepository.findByAutorAndHiloPadreIsNullOrderByDateCreation(userApp);
     }

     public List<Hilo> findByHiloPadreIsNullOrderByDateCreation(){
         return hiloRepository.findByHiloPadreIsNullOrderByDateCreation();
     }


}

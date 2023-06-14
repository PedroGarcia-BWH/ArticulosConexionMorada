package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificacionHilo;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloRepository;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificacionPersona;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificationPersonaService;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class NotificationMessageControllerTest {
    private MockMvc mockMvc;

    @Mock
    private NotificationHiloService notificationHiloService;

    @Mock
    private NotificationPersonaService notificacionPersonaService;

    @Mock
    private UsernameService usernameService;

    @Mock
    private NotificationHiloRepository notificationHiloRepository;

    @Autowired
    private NotificationMessageController notificationMessageController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationMessageController).build();
    }

    @Test
    public void testGetNotificationsHilo() throws Exception {
        UUID uuidNotificacion1 = UUID.randomUUID();
        UUID uuidNotificacion2 = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();
        Username username = new Username();
        username.setId(uuidUser);
        username.setFirebaseId(uuidUser.toString());
        username.setUsername("username");
        NotificacionHilo notificacionHilo1 = new NotificacionHilo();
        notificacionHilo1.setId(uuidNotificacion1);
        notificacionHilo1.setUserNotificado(username);
        notificacionHilo1.setHilo(new Hilo());
        notificacionHilo1.setMensaje("Mensaje de la notificación hilo 1");
        notificacionHilo1.setDateCreation(new Date());

        NotificacionHilo notificacionHilo2 = new NotificacionHilo();
        notificacionHilo2.setId(uuidNotificacion2);
        notificacionHilo2.setUserNotificado(username);
        notificacionHilo2.setHilo(new Hilo());
        notificacionHilo2.setMensaje("Mensaje de la notificación hilo 2");
        notificacionHilo2.setDateCreation(new Date());

        List<NotificacionHilo> notificacionHilos = new ArrayList<>();
        notificacionHilos.add(notificacionHilo1);
        notificacionHilos.add(notificacionHilo2);

        when(notificationHiloService.findByUserAndDateEliminationIsNullOrderByDateCreation(any(Username.class))).thenReturn(notificacionHilos);

        mockMvc.perform(get("/get/Notifications/hilo/{uuid}", uuidUser))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetNotificationsPersona() throws Exception {
        UUID uuidNotificacion1 = UUID.randomUUID();
        UUID uuidNotificacion2 = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();
        Username username = new Username();
        username.setId(uuidUser);
        username.setFirebaseId(uuidUser.toString());
        NotificacionPersona notificacionPersona1 = new NotificacionPersona();
        notificacionPersona1.setId(uuidNotificacion1);
        notificacionPersona1.setUserNotificado(username);
        notificacionPersona1.setMensaje("Mensaje de la notificación persona 1");
        notificacionPersona1.setDateCreation(new Date());

        NotificacionPersona notificacionPersona2 = new NotificacionPersona();
        notificacionPersona2.setId(uuidNotificacion2);
        notificacionPersona2.setUserNotificado(username);
        notificacionPersona2.setMensaje("Mensaje de la notificación persona 2");
        notificacionPersona2.setDateCreation(new Date());

        List<NotificacionPersona> notificacionPersonas = new ArrayList<>();
        notificacionPersonas.add(notificacionPersona1);
        notificacionPersonas.add(notificacionPersona2);

        when(notificacionPersonaService.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(any(Username.class))).thenReturn(notificacionPersonas);

        mockMvc.perform(get("/get/Notifications/persona/{uuid}", uuidUser.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotifications() throws Exception {
        when(notificationHiloService.findByUserAndDateEliminationIsNullOrderByDateCreation(any(Username.class))).thenReturn(new ArrayList<>());
        when(notificacionPersonaService.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(any(Username.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(delete("/delete/Notifications/{uuid}", UUID.randomUUID().toString()))
                .andExpect(status().isOk());

    }

}

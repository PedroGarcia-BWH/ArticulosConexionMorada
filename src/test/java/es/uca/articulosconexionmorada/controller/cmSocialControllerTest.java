package es.uca.articulosconexionmorada.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uca.articulosconexionmorada.cmSocial.dislike.Dislike;
import es.uca.articulosconexionmorada.cmSocial.dislike.DislikeService;
import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.cmSocial.like.Like;
import es.uca.articulosconexionmorada.cmSocial.like.LikeService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificationPersonaService;
import es.uca.articulosconexionmorada.cmSocial.reporte.ReporteService;
import es.uca.articulosconexionmorada.cmSocial.seguidores.Seguidores;
import es.uca.articulosconexionmorada.cmSocial.seguidores.SeguidoresService;
import es.uca.articulosconexionmorada.controller.payload.PayloadHilo;
import es.uca.articulosconexionmorada.controller.payload.PayloadReporte;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameRepository;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class cmSocialControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UsernameService usernameService;

    @Mock
    private HiloService hiloService;

    @Mock
    private SeguidoresService seguidoresService;

    @Mock
    private LikeService likeService;

    @Mock
    private DislikeService dislikeService;

    @Mock
    private UsernameRepository usernameRepository;

    @Mock
    private NotificationHiloService notificacionHiloService;

    @Mock
    private NotificationPersonaService notificacionPersonaService;

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private cmSocialController socialController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(socialController).build();
    }

    @Test
    public void testGetlastHilos() throws Exception {
        List<Hilo> hilos = new ArrayList<>();
        // Agregar hilos a la lista
        when(hiloService.findByHiloPadreIsNullOrderByDateCreation()).thenReturn(hilos);

        mockMvc.perform(get("/get/lastHilos/{uuid}", "some-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetLastHilosSeguidos() throws Exception {
        List<Seguidores> seguidores = new ArrayList<>();
        // Agregar seguidores a la lista
        when(seguidoresService.findBySeguido(any())).thenReturn(seguidores);
        when(hiloService.findByAutorOrderByDateCreation(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/get/lastHilosSeguidos/{uuid}", "some-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testAddHilo() throws Exception {
        PayloadHilo payloadHilo = new PayloadHilo();
        payloadHilo.setAutorUuid("some-uuid");
        payloadHilo.setMensaje("Test message");

        when(usernameService.findByFirebaseId("some-uuid")).thenReturn(new Username());
        when(hiloService.findById(any())).thenReturn(java.util.Optional.of(new Hilo()));

        mockMvc.perform(post("/add/hilo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payloadHilo)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSeguidores() throws Exception {
        Username userApp = new Username();
        userApp.setFirebaseId("some-uuid");

        when(usernameService.findByFirebaseId("some-uuid")).thenReturn(userApp);
        when(seguidoresService.countBySeguidores(userApp)).thenReturn(5);

        mockMvc.perform(get("/get/Seguidores/{uuid}", "some-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    public void testGetSeguidos() throws Exception {
        Username userApp = new Username();
        userApp.setFirebaseId("some-uuid");

        when(usernameService.findByFirebaseId("some-uuid")).thenReturn(userApp);
        when(seguidoresService.countBySeguido(userApp)).thenReturn(3);

        mockMvc.perform(get("/get/Seguidos/{uuid}", "some-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    public void testGetSeguidorExist() throws Exception {
        Username seguidor = new Username();
        seguidor.setFirebaseId("seguidor-uuid");

        Username seguido = new Username();
        seguido.setFirebaseId("seguido-uuid");

        when(usernameService.findByFirebaseId("seguidor-uuid")).thenReturn(seguidor);
        when(usernameService.findByFirebaseId("seguido-uuid")).thenReturn(seguido);

        when(seguidoresService.findBySeguidorAndSeguido(seguidor, seguido)).thenReturn(Optional.of(new Seguidores()));

        mockMvc.perform(get("/get/SeguidorExist/{uuidSeguidor}/{uuidSeguido}", "seguidor-uuid", "seguido-uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testDeleteSeguidor() throws Exception {
        Username seguidor = new Username();
        seguidor.setFirebaseId("seguidor-uuid");

        Username seguido = new Username();
        seguido.setFirebaseId("seguido-uuid");

        Seguidores seguidores = new Seguidores();
        seguidores.setSeguidor(seguidor);
        seguidores.setSeguido(seguido);

        when(usernameService.findByFirebaseId("seguidor-uuid")).thenReturn(seguidor);
        when(usernameService.findByFirebaseId("seguido-uuid")).thenReturn(seguido);
        when(seguidoresService.findBySeguidorAndSeguido(seguidor, seguido)).thenReturn(Optional.of(seguidores));

        mockMvc.perform(delete("/delete/Seguidor/{uuidSeguidor}/{uuidSeguido}", "seguidor-uuid", "seguido-uuid"))
                .andExpect(status().isOk());

    }

    @Test
    public void testAddLike() throws Exception {
        Hilo hilo = new Hilo();
        UUID uuid = UUID.randomUUID();
        hilo.setId(uuid);

        Username userApp = new Username();
        userApp.setFirebaseId("user-uuid");

        when(hiloService.findById(uuid)).thenReturn(Optional.of(hilo));
        when(usernameService.findByFirebaseId("user-uuid")).thenReturn(userApp);
        when(likeService.likeExists(hilo, userApp)).thenReturn(false);

        String requestBody = "{\"idHilo\":\"" + uuid.toString() + "\",\"autorUuid\":\"user-uuid\"}";

        mockMvc.perform(post("/add/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    public void testAddDislike() throws Exception {
        Hilo hilo = new Hilo();
        UUID uuid = UUID.randomUUID();
        hilo.setId(uuid);

        Username userApp = new Username();
        userApp.setFirebaseId("user-uuid");

        when(hiloService.findById(uuid)).thenReturn(Optional.of(hilo));
        when(usernameService.findByFirebaseId("user-uuid")).thenReturn(userApp);
        when(dislikeService.dislikeExists(hilo, userApp)).thenReturn(false);

        String requestBody = "{\"idHilo\":\"" + uuid.toString() + "\",\"autorUuid\":\"user-uuid\"}";

        mockMvc.perform(post("/add/dislike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }


    @Test
    public void testDeleteLike() throws Exception {
        Hilo hilo = new Hilo();
        UUID uuid = UUID.randomUUID();
        hilo.setId(uuid);

        Username userApp = new Username();
        userApp.setFirebaseId("user-uuid");

        Like like = new Like();
        like.setHilo(hilo);
        like.setUserApp(userApp);

        when(hiloService.findById(uuid)).thenReturn(Optional.of(hilo));
        when(usernameService.findByFirebaseId("user-uuid")).thenReturn(userApp);
        when(likeService.findByHiloAndUserApp(hilo, userApp)).thenReturn(Optional.of(like));

        mockMvc.perform(delete("/delete/like/{idHilo}/{uuid}", uuid, "user-uuid"))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteDislike() throws Exception {
        Hilo hilo = new Hilo();
        UUID uuid = UUID.randomUUID();
        hilo.setId(uuid);

        Username userApp = new Username();
        userApp.setFirebaseId("user-uuid");

        Dislike dislike = new Dislike();
        dislike.setHilo(hilo);
        dislike.setUserApp(userApp);

        when(hiloService.findById(uuid)).thenReturn(Optional.of(hilo));
        when(usernameService.findByFirebaseId("user-uuid")).thenReturn(userApp);
        when(dislikeService.findByHiloAndUserApp(hilo, userApp)).thenReturn(Optional.of(dislike));

        mockMvc.perform(delete("/delete/dislike/{idHilo}/{uuid}", uuid, "user-uuid"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLike() throws Exception {
        UUID uuidHilo = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();

        when(likeService.getLike(uuidHilo.toString(), uuidUser.toString())).thenReturn(true);

        mockMvc.perform(get("/get/like/{idHilo}/{uuid}", uuidHilo, uuidUser))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetDislike() throws Exception {
        UUID uuidHilo = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();
        when(dislikeService.getDislike(uuidHilo.toString(), uuidUser.toString())).thenReturn(true);

        mockMvc.perform(get("/get/dislike/{idHilo}/{uuid}", uuidHilo, uuidUser))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @Test
    public void testSearchHilos() throws Exception {
        UUID uuidHilo = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();
        List<Hilo> hilos = new ArrayList<>();
        Hilo hilo = new Hilo();
        hilo.setId(uuidHilo);
        hilo.setMensaje("Mensaje de ejemplo");
        hilo.setAutor(new Username());
        hilo.setDateCreation(new Date());
        hilos.add(hilo);

        when(hiloService.findByMensajeContainingIgnoreCase("example")).thenReturn(hilos);
        when(likeService.countByHilo(any(Hilo.class))).thenReturn(0L);
        when(dislikeService.countByHilo(any(Hilo.class))).thenReturn(0L);
        when(likeService.getLike(uuidHilo.toString(), uuidUser.toString())).thenReturn(false);
        when(dislikeService.getDislike(uuidHilo.toString(), uuidUser.toString())).thenReturn(false);

        mockMvc.perform(get("/search/hilos/{search}/{uuid}", "example", uuidUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idHilo").value(uuidHilo.toString()))
                .andExpect(jsonPath("$[0].mensaje").value("Mensaje de ejemplo"))
                .andExpect(jsonPath("$[0].hiloPadreUuid").doesNotExist())
                .andExpect(jsonPath("$[0].dateCreation").exists())
                .andExpect(jsonPath("$[0].likes").value(0))
                .andExpect(jsonPath("$[0].dislikes").value(0))
                .andExpect(jsonPath("$[0].liked").value(false))
                .andExpect(jsonPath("$[0].disliked").value(false));
    }

    @Test
    public void testSearchUsuarios() throws Exception {
        List<Username> usernames = new ArrayList<>();
        Username username = new Username();
        username.setFirebaseId("user-uuid");
        username.setUsername("exampleuser");
        username.setFirebaseToken("firebase-token");
        usernames.add(username);

        when(usernameService.finbByUsernameContainingIgnoreCase("example")).thenReturn(usernames);

        mockMvc.perform(get("/search/usuarios/{search}", "example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value("user-uuid"))
                .andExpect(jsonPath("$[0].username").value("exampleuser"))
                .andExpect(jsonPath("$[0].token").value("firebase-token"));
    }

    @Test
    public void testGetHilosUser() throws Exception {
        UUID uuidHilo = UUID.randomUUID();
        List<Hilo> hilos = new ArrayList<>();
        Hilo hilo = new Hilo();
        hilo.setId(uuidHilo);
        hilo.setMensaje("Mensaje de ejemplo");
        hilo.setAutor(new Username());
        hilo.setDateCreation(new Date());
        hilos.add(hilo);

        when(hiloService.findByAutorOrderByDateCreation(any(Username.class))).thenReturn(hilos);
        when(likeService.countByHilo(any(Hilo.class))).thenReturn(0L);
        when(dislikeService.countByHilo(any(Hilo.class))).thenReturn(0L);

        mockMvc.perform(get("/get/hilos/{uuid}", uuidHilo))
                .andExpect(status().isOk());

    }
    @Test
    public void testGetRespuestas() throws Exception {
        UUID uuidHiloPadre = UUID.randomUUID();
        UUID uuidHiloRespuesta1 = UUID.randomUUID();
        UUID uuidHiloRespuesta2 = UUID.randomUUID();
        UUID uuidUser = UUID.randomUUID();
        Username username = new Username();
        username.setId(uuidUser);
        username.setFirebaseId(uuidUser.toString());
        Hilo hiloPadre = new Hilo();
        hiloPadre.setId(uuidHiloPadre);
        hiloPadre.setMensaje("Mensaje del hilo padre");
        hiloPadre.setAutor(username);
        hiloPadre.setDateCreation(new Date());

        Hilo hiloRespuesta1 = new Hilo();
        hiloRespuesta1.setId(uuidHiloRespuesta1);
        hiloRespuesta1.setMensaje("Mensaje de la respuesta 1");
        hiloRespuesta1.setAutor(username);
        hiloRespuesta1.setDateCreation(new Date());
        hiloRespuesta1.setHiloPadre(hiloPadre);

        Hilo hiloRespuesta2 = new Hilo();
        hiloRespuesta2.setId(uuidHiloRespuesta2);
        hiloRespuesta2.setMensaje("Mensaje de la respuesta 2");
        hiloRespuesta2.setAutor(username);
        hiloRespuesta2.setDateCreation(new Date());
        hiloRespuesta2.setHiloPadre(hiloPadre);

        List<Hilo> hilosRespuestas = new ArrayList<>();
        hilosRespuestas.add(hiloRespuesta1);
        hilosRespuestas.add(hiloRespuesta2);

        when(hiloService.findById(uuidHiloPadre)).thenReturn(Optional.of(hiloPadre));
        when(hiloService.findByHiloPadreOrderByDateCreation(hiloPadre)).thenReturn(hilosRespuestas);
        when(likeService.countByHilo(any(Hilo.class))).thenReturn(0L);
        when(dislikeService.countByHilo(any(Hilo.class))).thenReturn(0L);
        when(likeService.getLike(any(String.class), any(String.class))).thenReturn(false);
        when(dislikeService.getDislike(any(String.class), any(String.class))).thenReturn(false);

        mockMvc.perform(get("/get/respuesta/{idHilo}/{uuid}", uuidHiloPadre, uuidUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idHilo").value(uuidHiloPadre.toString()))
                .andExpect(jsonPath("$[0].autorUuid").exists())
                .andExpect(jsonPath("$[0].mensaje").value("Mensaje del hilo padre"))
                .andExpect(jsonPath("$[0].hiloPadreUuid").doesNotExist())
                .andExpect(jsonPath("$[0].dateCreation").exists())
                .andExpect(jsonPath("$[0].likes").value(0))
                .andExpect(jsonPath("$[0].dislikes").value(0))
                .andExpect(jsonPath("$[0].liked").value(false))
                .andExpect(jsonPath("$[0].disliked").value(false))
                .andExpect(jsonPath("$[1].idHilo").value(uuidHiloRespuesta1.toString()))
                .andExpect(jsonPath("$[1].autorUuid").exists())
                .andExpect(jsonPath("$[1].mensaje").value("Mensaje de la respuesta 1"))
                .andExpect(jsonPath("$[1].hiloPadreUuid").value(uuidHiloPadre.toString()))
                .andExpect(jsonPath("$[1].dateCreation").exists())
                .andExpect(jsonPath("$[1].likes").value(0))
                .andExpect(jsonPath("$[1].dislikes").value(0))
                .andExpect(jsonPath("$[1].liked").value(false))
                .andExpect(jsonPath("$[1].disliked").value(false))
                .andExpect(jsonPath("$[2].idHilo").value(uuidHiloRespuesta2.toString()))
                .andExpect(jsonPath("$[2].autorUuid").exists())
                .andExpect(jsonPath("$[2].mensaje").value("Mensaje de la respuesta 2"))
                .andExpect(jsonPath("$[2].hiloPadreUuid").value(uuidHiloPadre.toString()))
                .andExpect(jsonPath("$[2].dateCreation").exists())
                .andExpect(jsonPath("$[2].likes").value(0))
                .andExpect(jsonPath("$[2].dislikes").value(0))
                .andExpect(jsonPath("$[2].liked").value(false))
                .andExpect(jsonPath("$[2].disliked").value(false));

    }

    @Test
    public void testAddReporte() throws Exception {
        UUID uuidHilo = UUID.randomUUID();
        UUID uuidReportado = UUID.randomUUID();
        UUID uuidReportador = UUID.randomUUID();
        PayloadReporte payloadReporte = new PayloadReporte();
        payloadReporte.setReportadoUuid(uuidReportado.toString());
        payloadReporte.setReportadorUuid(uuidReportador.toString());
        payloadReporte.setMotivo("Motivo del reporte");
        payloadReporte.setDescripcion("Descripción del reporte");
        payloadReporte.setMensajeUuid(uuidHilo.toString());

        when(reporteService.reporteExists(uuidReportado.toString(), uuidReportador.toString(), uuidHilo.toString())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(post("/reporte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payloadReporte)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("true", response);
    }

        private String asJsonString(Object obj) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}

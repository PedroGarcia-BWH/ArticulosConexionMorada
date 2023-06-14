package es.uca.articulosconexionmorada.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.uca.articulosconexionmorada.controller.payload.PayloadPuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.ChatService;
import es.uca.articulosconexionmorada.sistemaCompanero.marker.MarkerService;
import es.uca.articulosconexionmorada.sistemaCompanero.mensaje.MensajeService;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompaneroService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SistemaCompaneroRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PuntoCompaneroService puntoCompaneroService;

    @Mock
    private MarkerService markerService;

    @Mock
    private ChatService chatService;

    @Mock
    private MensajeService mensajeService;

    @InjectMocks
    private SistemaCompaneroRestController sistemaCompaneroRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sistemaCompaneroRestController).build();
    }

    @Test
    public void testPuntoCompaneroAll() throws Exception {
        List<PayloadPuntoCompanero> puntoCompaneros = new ArrayList<>();
        PayloadPuntoCompanero payloadPuntoCompanero = new PayloadPuntoCompanero();
        payloadPuntoCompanero.setId("1231234");
        payloadPuntoCompanero.setUuidSolicitante("12345");
        payloadPuntoCompanero.setUuidAceptante("54321");
        payloadPuntoCompanero.setMarkerOrigenLatitud("36.5");
        payloadPuntoCompanero.setMarkerOrigenLongitud("-6.2");
        payloadPuntoCompanero.setMarkerOrigenTitulo("Origen");
        payloadPuntoCompanero.setMarkerDestinoLatitud("36.5");
        payloadPuntoCompanero.setMarkerDestinoLongitud("-6.2");
        payloadPuntoCompanero.setMarkerDestinoTitulo("Destino");
        payloadPuntoCompanero.setDateEvento("2020-12-12T12:12:12");
        // Agregar datos de ejemplo a la lista puntoCompaneros
        puntoCompaneros.add(payloadPuntoCompanero);

        when(puntoCompaneroService.allPuntoCompañeroActive()).thenReturn(puntoCompaneros);

        mockMvc.perform(get("/puntoCompanero/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].uuidSolicitante").exists())
                .andExpect(jsonPath("$[0].uuidAceptante").exists())
                .andExpect(jsonPath("$[0].markerOrigenLatitud").exists())
                .andExpect(jsonPath("$[0].markerOrigenLongitud").exists())
                .andExpect(jsonPath("$[0].markerOrigenTitulo").exists())
                .andExpect(jsonPath("$[0].markerDestinoLatitud").exists())
                .andExpect(jsonPath("$[0].markerDestinoLongitud").exists())
                .andExpect(jsonPath("$[0].markerDestinoTitulo").exists())
                .andExpect(jsonPath("$[0].dateEvento").exists());
    }

    @Test
    public void testPuntoCompaneroAdd() throws Exception {
        // Crear un objeto PayloadPuntoCompanero de ejemplo
        PayloadPuntoCompanero payloadPuntoCompanero = new PayloadPuntoCompanero();
        payloadPuntoCompanero.setUuidSolicitante("12345");
        payloadPuntoCompanero.setUuidAceptante("54321");
        payloadPuntoCompanero.setMarkerOrigenLatitud("36.5");
        payloadPuntoCompanero.setMarkerOrigenLongitud("-6.2");
        payloadPuntoCompanero.setMarkerOrigenTitulo("Origen");
        payloadPuntoCompanero.setMarkerDestinoLatitud("36.5");
        payloadPuntoCompanero.setMarkerDestinoLongitud("-6.2");
        payloadPuntoCompanero.setMarkerDestinoTitulo("Destino");
        payloadPuntoCompanero.setDateEvento("2020-12-12T12:12:12");

        mockMvc.perform(post("/puntoCompanero/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payloadPuntoCompanero)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPuntoCompaneroAccept() throws Exception {
        UUID uuid = UUID.randomUUID();
        PuntoCompanero puntoCompanero = new PuntoCompanero();

        puntoCompanero.setId(uuid);
        puntoCompanero.setUuidSolicitante("12345");
        puntoCompanero.setUuidAceptante(null);

        when(puntoCompaneroService.findById(uuid.toString())).thenReturn(Optional.of(puntoCompanero));


        mockMvc.perform(put("/puntoCompanero/accept/{id}/{uuid}", uuid, "123456"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    public void testPuntoCompanero_NotFound() throws Exception {
        String puntoCompaneroId = "1";

        when(puntoCompaneroService.findById(puntoCompaneroId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/puntoCompanero/{id}", puntoCompaneroId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    // Método auxiliar para convertir objetos a formato JSON
    private static String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }
}
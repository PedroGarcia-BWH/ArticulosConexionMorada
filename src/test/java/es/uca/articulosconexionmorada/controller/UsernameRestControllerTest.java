package es.uca.articulosconexionmorada.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uca.articulosconexionmorada.controller.payload.PayloadUsername;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UsernameRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UsernameService usernameService;

    @InjectMocks
    private UsernameRestController usernameRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usernameRestController).build();
    }

    @Test
    public void testAddUsername() throws Exception {
        PayloadUsername payloadUsername = new PayloadUsername();
        payloadUsername.setUsername("testusername");
        payloadUsername.setUuid("testuuid");
        payloadUsername.setToken("testtoken");

        when(usernameService.findByUsername(payloadUsername.getUsername())).thenReturn(Optional.empty());

        mockMvc.perform(post("/addUsername")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payloadUsername)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // Aquí puedes realizar más verificaciones si es necesario
    }

    @Test
    public void testGetUsername() throws Exception {
        String username = "testusername";

        when(usernameService.findByUsername(username)).thenReturn(Optional.of(new Username()));

        mockMvc.perform(get("/getUsername/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @Test
    public void testDeleteUsername() throws Exception {
        String username = "testusername";

        when(usernameService.findByUsername(username)).thenReturn(Optional.of(new Username()));

        mockMvc.perform(delete("/deleteUsername/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @Test
    public void testUpdateToken() throws Exception {
        PayloadUsername payloadUsername = new PayloadUsername();
        payloadUsername.setUuid("testuuid");
        payloadUsername.setToken("testtoken");

        when(usernameService.findByFirebaseId(payloadUsername.getUuid())).thenReturn(new Username());

        mockMvc.perform(put("/update/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payloadUsername)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

    }

    // Método de utilidad para convertir objetos a formato JSON
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
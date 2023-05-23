package es.uca.articulosconexionmorada.firebase;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationData {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;
}

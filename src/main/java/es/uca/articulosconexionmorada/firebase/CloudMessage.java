package es.uca.articulosconexionmorada.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudMessage {

    public static void sendNotification(NotificationData notificationData) throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ClassPathResource("conexion-morada-firebase-adminsdk-yfw4e-42a173e4d3.json").getInputStream());


        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp.initializeApp(firebaseOptions);
        }

        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        Notification notification = Notification.builder()
                .setTitle(notificationData.getTitle())
                .setBody(notificationData.getBody())
                .setImage(notificationData.getImage())
                .build();

        Message message = Message.builder()
                .setToken(notificationData.getRecipientToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);

        }catch (FirebaseMessagingException firebaseMessagingException){
            System.out.println("Error al enviar el mensaje: " + firebaseMessagingException.getMessage());
        }

    }
}

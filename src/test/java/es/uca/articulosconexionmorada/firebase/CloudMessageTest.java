package es.uca.articulosconexionmorada.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CloudMessageTest {

    @Test
    public void testSendNotification() throws IOException {

        // Configuración de los datos de la notificación
        NotificationData notificationData = new NotificationData();
        notificationData.setTitle("Test Notification");
        notificationData.setBody("This is a test notification");
        notificationData.setImage("https://example.com/image.jpg");
        notificationData.setRecipientToken("recipientToken");

        // Llamada a la función que queremos probar
        CloudMessage.sendNotification(notificationData);
    }
}
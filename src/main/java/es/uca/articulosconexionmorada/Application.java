package es.uca.articulosconexionmorada;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.firebase.CloudMessage;
import es.uca.articulosconexionmorada.firebase.NotificationData;
import es.uca.articulosconexionmorada.sistemaCompanero.mensaje.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Date;

@EntityScan(basePackages = {"es.uca.articulosconexionmorada"})
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ArticleService articleService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	@Override
	public void run(String... args) throws Exception {

		NotificationData notificationData = new NotificationData();
		notificationData.setBody("Cuerpo de la notificación");
		notificationData.setTitle("Título de la notificación");
		notificationData.setRecipientToken("f2Dkam4_QCKb1A1MZ9F0Aj:APA91bGaQjEblKFWZfgPciSUXmqp7VPPSpoPZGVTejXXT6w2vgCc78hsOCTMLLW3fo-6XDhS9czwEKYajlMIsQTSsKSANE1a2b_al-_9YXP9J4FlR2dcQtb1HspYEOvZoK2hIPNCffFz");
		//CloudMessage.sendNotification(notificationData);
		/*articleService.save(new Article("La violencia sexual en España: un problema que aún no se erradica", "La violencia sexual es una forma de violencia que afecta a millones de mujeres en España. En este artículo se analizan las causas y las consecuencias de la violencia sexual en España, y se proponen medidas para prevenir y combatir este flagelo.",
				"La violencia sexual es una forma de violencia que afecta a millones de mujeres en España. Según datos del Instituto de la Mujer, en España una de cada tres mujeres ha sufrido algún tipo de violencia sexual en su vida. Además, el número de denuncias por violación y agresiones sexuales ha aumentado en los últimos años, a pesar de las campañas de sensibilización y las medidas de prevención y protección.\n" +
						"\n" +
						"Una de las principales causas de la violencia sexual en España es la desigualdad entre hombres y mujeres. La sociedad española sigue siendo machista y estereotipada, lo que alimenta la discriminación y el acoso hacia las mujeres. Además, el sistema de justicia y el ámbito laboral no siempre protegen adecuadamente a las mujeres víctimas de violencia sexual, y no siempre se toman medidas eficaces para prevenir y combatir este flagelo.\n" +
						"\n" +
						"La violencia sexual tiene graves consecuencias tanto para las víctimas como para la sociedad en general. Las mujeres víctimas de violencia sexual sufren daños físicos y psicológicos, y a menudo tienen que dejar sus hogares y sus trabajos. Además, la violencia sexual tiene un costo económico para la sociedad, ya que se generan gastos en servicios sociales, sanitarios y de seguridad.\n" +
						"\n" +
						"Para prevenir y combatir la violencia sexual en España, es necesario adoptar medidas de prevención y protección eficaces. En primer lugar, es esencial promover la igualdad entre hombres y mujeres, mediante la educación y la sensibilización sobre igualdad de género y diversidad. Además, es importante fortalecer el sistema de justicia y el ámbito laboral, mediante la formación de los profesionales y la adopción de medidas de protección y prevención. También es fundamental establecer medidas de protección y ayuda para las víctimas de violencia sexual, como refugios y servicios de atención psicológica y jurídica.", "http://192.168.0.19:80/articulosConexionMorada/vioSexual.webp"));
	*/
	}

}

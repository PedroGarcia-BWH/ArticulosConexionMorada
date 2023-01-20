package es.uca.articulosconexionmorada;

import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.indexador.Indexador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ArticleService articleService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//run

	@Override
	public void run(String... args) throws Exception {
		Indexador indexador = new Indexador(articleService);
		indexador.index();
	}

}

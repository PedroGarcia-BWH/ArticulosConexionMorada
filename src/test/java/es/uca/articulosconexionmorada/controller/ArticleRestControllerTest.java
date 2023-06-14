package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.controller.payload.PayloadArticle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleRestControllerTest {
    //Prueba de lastArticles cuando se proporcionan preferencias válidas y se encuentran artículos
    @Test
    public void testLastArticlesWithValidPreferencesAndArticlesFound() {
        // Preparar datos de prueba
        PayloadArticle payloadArticle = new PayloadArticle();
        payloadArticle.setNumberArticles(3);
        List<String> preferences = Arrays.asList("", "", "", "city","");
        payloadArticle.setPreferences(preferences);

        ArticleService articleService = mock(ArticleService.class);
        List<Article> articles = Arrays.asList(
                new Article("title1", "description1", "body1", "urlFrontPage1", "city", "comunidad1"),
                new Article("title2", "description2", "body2", "urlFrontPage2", "city", "comunidad2"),
                new Article("title3", "description3", "body3", "urlFrontPage3", "city", "comunidad3")
        );
        when(articleService.findByCityAndAndEliminationDateIsNull("city")).thenReturn(articles);

        ArticleRestController controller = new ArticleRestController(articleService);
        //controller.setArticleService(articleService);

        // Ejecutar método bajo prueba
        List<Article> result = controller.lastArticles(payloadArticle);

        // Verificar resultados
        assertEquals(3, result.size());
        // Aquí puedes agregar más aserciones para verificar los detalles de los artículos devueltos
    }
    //Prueba de lastArticles cuando no se proporcionan preferencias y se encuentran artículos:
    @Test
    public void testLastArticlesWithNoPreferencesAndArticlesFound() {
        // Preparar datos de prueba
        PayloadArticle payloadArticle = new PayloadArticle();
        payloadArticle.setNumberArticles(2);
        List<String> preferences = Arrays.asList("", "", "", "", "");
        payloadArticle.setPreferences(preferences);

        ArticleService articleService = mock(ArticleService.class);
        List<Article> articles = Arrays.asList(
                new Article("title1", "description1", "body1", "urlFrontPage1", null, null),
                new Article("title2", "description2", "body2", "urlFrontPage2", null, null)
        );
        when(articleService.findByEliminationDateIsNull()).thenReturn(articles);

        ArticleRestController controller = new ArticleRestController(articleService);

        // Ejecutar método bajo prueba
        List<Article> result = controller.lastArticles(payloadArticle);

        // Verificar resultados
        assertEquals(2, result.size());
        // Aquí puedes agregar más aserciones para verificar los detalles de los artículos devueltos
    }
    //Prueba de lastArticles cuando no se encuentran artículos:
    @Test
    public void testLastArticlesWithNoArticlesFound() {
        // Preparar datos de prueba
        PayloadArticle payloadArticle = new PayloadArticle();
        payloadArticle.setNumberArticles(3);
        List<String> preferences = Arrays.asList("", "", "", "city", "");
        payloadArticle.setPreferences(preferences);

        ArticleService articleService = mock(ArticleService.class);
        List<Article> articles = Collections.emptyList();
        when(articleService.findByCityAndAndEliminationDateIsNull("city")).thenReturn(articles);

        ArticleRestController controller = new ArticleRestController(articleService);

        // Ejecutar método bajo prueba
        List<Article> result = controller.lastArticles(payloadArticle);

        // Verificar resultados
        assertEquals(0, result.size());
    }
    //Prueba de query cuando se proporciona una comunidad válida:
    @Test
    public void testQueryWithValidCommunity() throws IOException, URISyntaxException, InterruptedException {
        // Preparar datos de prueba
        String query = "query";
        int numberArticles = 5;
        String comunidad = "Community";
        String city = "null";

        ArticleService articleService = mock(ArticleService.class);
        List<Article> articles = Arrays.asList(
                new Article("title1", "description1", "body1", "urlFrontPage1", "city", "comunidad1"),
                new Article("title2", "description2", "body2", "urlFrontPage2", "city", "comunidad2")
        );
        when(articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidad(query, comunidad)).thenReturn(articles);

        ArticleRestController controller = new ArticleRestController(articleService);

        // Ejecutar método bajo prueba
        List<Article> result = controller.query(query, numberArticles, comunidad, city);

        // Verificar resultados
        assertEquals(2, result.size());
        // Aquí puedes agregar más aserciones para verificar los detalles de los artículos devueltos
    }
}

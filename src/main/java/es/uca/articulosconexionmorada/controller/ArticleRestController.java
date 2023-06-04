package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.controller.payload.PayloadArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ArticleRestController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/lastArticles")
    public List<Article> lastArticles(@RequestBody PayloadArticle payloadArticle) {
        List<Article> lastArticles = new ArrayList<>();
        ArrayList<String> emptyPreferences = new ArrayList<>();

        for(int i=0; i<3; i++) emptyPreferences.add("");

        System.out.println(new Date() + "--LastArticles recibido (preferences): " + payloadArticle.getPreferences());
        List<Article> articles = new ArrayList<>();
        int i = 0;
        if(!payloadArticle.getPreferences().get(3).equals("")){
             articles = articleService.findByCityAndAndEliminationDateIsNull(payloadArticle.getPreferences().get(3));
        }else if(!payloadArticle.getPreferences().get(4).equals("")){
             articles = articleService.findByComunidadAndAndEliminationDateIsNull(payloadArticle.getPreferences().get(4));
        }else{
             articles = articleService.findByEliminationDateIsNull();
        }
        if(payloadArticle.getNumberArticles() > 0){
            if(articles.size() > 0){
                if(!equals(payloadArticle.getPreferences(), emptyPreferences)){

                    while(lastArticles.size() < payloadArticle.getNumberArticles() && i < articles.size()){
                        if(payloadArticle.getPreferences().contains(articles.get(i).getCategory())){
                            lastArticles.add(articles.get(i));
                        }
                        i++;
                    }
                }else{
                    for(int j = 0; j < payloadArticle.getNumberArticles() && j < articles.size(); j++){
                        lastArticles.add(articles.get(j));
                    }
                }
            }
        }
        return lastArticles;
    }

    /*@GetMapping("/query/{query}/{numberArticles}/{comunidad}/{city}")
    List<Article> query(@PathVariable String query, @PathVariable int numberArticles, @PathVariable String comunidad, @PathVariable String city) throws IOException, URISyntaxException, InterruptedException {
        Consulta consulta = new Consulta(articleService, numberArticles);
        System.out.println(new Date() + "--Consulta recibida: " + query);
        ///System.out.println(consulta.Consulta(query));
        return consulta.Consulta(query);
    }*/

    @GetMapping("/query/{query}/{numberArticles}/{comunidad}/{city}")
    List<Article> query(@PathVariable String query, @PathVariable int numberArticles, @PathVariable String comunidad, @PathVariable String city) throws IOException, URISyntaxException, InterruptedException {
        if(!comunidad.equals("null")){
            System.out.println( " Comunidad: " + articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidad(query, comunidad).size());
            return articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidad(query, comunidad);
        }else if (!city.equals("null")){
            System.out.println( " City: " + city + " " + articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndCity(query, city).size());
            return articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndCity(query, city);
        }else{
            System.out.println("Pais" + articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidadIsNullAndCityIsNull(query).size());
            return articleService.findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidadIsNullAndCityIsNull(query);
        }
    }

    private boolean equals(List<String> list1, List<String> list2){
        if(list1.size() != list2.size()) return false;
        for(int i = 0; i < list1.size(); i++){
            if(!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }
}

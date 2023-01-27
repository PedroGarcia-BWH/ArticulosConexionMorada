package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.consulta.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
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

        int i = 0;
        if(payloadArticle.getNumberArticles() > 0){
            List<Article> articles = articleService.findByEliminationDateIsNull();
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

    @GetMapping("/query/{query}/{numberArticles}")
    List<Article> query(@PathVariable String query, @PathVariable int numberArticles) throws IOException, URISyntaxException, InterruptedException {
        Consulta consulta = new Consulta(articleService, numberArticles);
        System.out.println(new Date() + "--Consulta recibida: " + query);
        ///System.out.println(consulta.Consulta(query));
        return consulta.Consulta(query);
    }

    private boolean equals(List<String> list1, List<String> list2){
        if(list1.size() != list2.size()) return false;
        for(int i = 0; i < list1.size(); i++){
            if(!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }
}

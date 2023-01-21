package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.consulta.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleRestController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/lastArticles")
    public List<Article> lastArticles(@RequestBody PayloadArticle payloadArticle) {
        List<Article> lastArticles = new ArrayList<>();
        System.out.println("payloadArticle: " + payloadArticle.getPreferences());
        int i = 0;
        if(payloadArticle.getNumberArticles() > 0){
            List<Article> articles = articleService.findByEliminationDateIsNull();
            if(articles.size() > 0){
                if(payloadArticle.getPreferences().size() > 0){
                    while(i < payloadArticle.getNumberArticles() && i < articles.size()){
                        if(payloadArticle.getPreferences().contains(articles.get(i).getCategory())){
                            lastArticles.add(articles.get(i));
                            i++;
                        }
                    }
                }else{
                    for(int j = 0; j < payloadArticle.getNumberArticles() && j < articles.size(); j++){
                        lastArticles.add(articles.get(i));
                    }
                }
            }
        }
        return lastArticles;
    }

    @GetMapping("/query/{query}/{numberArticles}")
    List<Article> query(@PathVariable String query, @PathVariable int numberArticles) throws IOException, URISyntaxException, InterruptedException {
        Consulta consulta = new Consulta(articleService, numberArticles);
        System.out.println("query: " + query);
        ///System.out.println(consulta.Consulta(query));
        return consulta.Consulta(query);
    }
}

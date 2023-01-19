package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    while(i < payloadArticle.getNumberArticles() && i <= articles.size()){
                        if(payloadArticle.getPreferences().contains(articles.get(i).getCategory())){
                            lastArticles.add(articles.get(i));
                            i++;
                        }
                    }
                }else{
                    for(int j = 0; j < payloadArticle.getNumberArticles() && j <= articles.size(); j++){
                        lastArticles.add(articles.get(i));
                    }
                }
            }
        }
        return lastArticles;
    }
}

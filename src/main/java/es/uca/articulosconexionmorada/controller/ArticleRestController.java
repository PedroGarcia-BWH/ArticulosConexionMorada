package es.uca.articulosconexionmorada.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestController {

    @PostMapping("/lastArticles")
    public PayloadArticle lastArticles(@RequestBody PayloadArticle payloadArticle) {
        return new PayloadArticle();
    }
}

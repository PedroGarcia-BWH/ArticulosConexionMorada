package es.uca.articulosconexionmorada.controller;



import es.uca.articulosconexionmorada.article.Article;

import java.util.List;

public class PayloadArticle {
    private String id;
    private String status;
    private List<String> preferences;
    private Integer numberArticles;
    private List<Article> articles;

    public PayloadArticle() {}

    public PayloadArticle(String id, String status, List<String> preferences, Integer numberArticles, List<Article> articles) {
        this.id = id;
        this.status = status;
        this.preferences = preferences;
        this.numberArticles = numberArticles;
        this.articles = articles;
    }

    //getters
    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public List<String> getPreferences() {
        return preferences;
    }
    public Integer getNumberArticles() {
        return numberArticles;
    }
    public List<Article> getArticles() {
        return articles;
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
    public void setNumberArticles(Integer numberArticles) {
        this.numberArticles = numberArticles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

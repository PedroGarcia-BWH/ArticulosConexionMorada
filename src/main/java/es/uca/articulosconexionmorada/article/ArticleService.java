package es.uca.articulosconexionmorada.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleRespository articleRepository;

    public ArticleService() {}

    public ArticleService(ArticleRespository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findByEliminationDateIsNull() {
        return articleRepository.findByeliminationDateIsNull();
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> findById(UUID id) {
        return articleRepository.findById(id);
    }

    public boolean deleteById(UUID id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setEliminationDate(new Date());
            articleRepository.save(article.get());
            return true;
        }
        return false;
    }

}

package es.uca.articulosconexionmorada.article;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRespository extends JpaRepository<Article, UUID> {
    public List<Article> findByeliminationDateIsNull();
}

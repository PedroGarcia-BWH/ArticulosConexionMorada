package es.uca.articulosconexionmorada.article;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRespository extends JpaRepository<Article, UUID> {
    public List<Article> findByeliminationDateIsNull();

    public List<Article> findByCityAndAndEliminationDateIsNull(String city);

    public List<Article> findByComunidadAndAndEliminationDateIsNull(String comunidad);

    List<Article> findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidad(String query, String query2, String query3, String comunidad);

    List<Article> findByTitleContainingOrDescriptionContainingOrBodyContainingAndCity(String query, String query2, String query3, String city);

    List<Article> findByTitleContainingOrDescriptionContainingOrBodyContainingAndComunidadIsNullAndCityIsNull(String query, String query2, String query3);
}

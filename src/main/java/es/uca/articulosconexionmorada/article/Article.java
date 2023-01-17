package es.uca.articulosconexionmorada.article;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Article {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID Id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private String urlFrontPage;

    @Column
    private Date creationDate;

    @Column
    private Date eliminationDate;

}

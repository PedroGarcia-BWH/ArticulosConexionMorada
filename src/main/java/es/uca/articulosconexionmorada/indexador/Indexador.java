package es.uca.articulosconexionmorada.indexador;

import com.google.gson.Gson;
import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.container.Container;
import es.uca.articulosconexionmorada.container.ContainerDocId;
import es.uca.articulosconexionmorada.container.JsonContainer;
import es.uca.articulosconexionmorada.container.docIDpeso;
import es.uca.articulosconexionmorada.preprocessing.preprocesamiento;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Indexador {
    static HashMap<String, Integer> fTerm;
    static HashMap<String, docIDpeso> indiceInvertido = new HashMap<String, docIDpeso>();

    static HashMap<String, Double > longPesoDoc = new HashMap<String, Double>();

    private ArticleService articleService;

    public Indexador(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void index() throws IOException, URISyntaxException {
        preprocesamiento preprocesamiento = new preprocesamiento();
        ArrayList<String> asTerm;

        List<Article> articles = articleService.findByEliminationDateIsNull();

        for (Article article : articles) {
            fTerm = new HashMap<String, Integer>();
            String sTexto  = article.getTitle() + " " + article.getDescription() + " " + article.getBody();
            asTerm = preprocesamiento.preprocessing(sTexto);
            conteoElementos(asTerm);
            calcularTF_paso2(article.getId().toString());
        }

        calcularIDF(articles.size());
        //imprmir en fichero indiceInvertido
        imprimirIndiceInvertido();
        //imprimir en fichero longPesoDoc
        imprimirLongPesoDoc();
    }


    public static void conteoElementos(ArrayList<String> asTerm) {

        for(String sTerm : asTerm) {
            if(fTerm.containsKey(sTerm)) {
                fTerm.put(sTerm, fTerm.get(sTerm) + 1);
            } else {
                fTerm.put(sTerm, 1);
            }
        }

    }

    public static void calcularTF_paso2(String sNombreFichero) {
        //System.out.println(sNombreFichero);
        for (String sTerm : fTerm.keySet()) {
            double tf = (double) 1 + Math.log(fTerm.get(sTerm)) / Math.log(2);

            if(!indiceInvertido.containsKey(sTerm)) indiceInvertido.put(sTerm, new docIDpeso());

            indiceInvertido.get(sTerm).docId.put(sNombreFichero, tf);
            //System.out.println(indiceInvertido.keySet());
        }
        //System.out.println(indiceInvertido);
        //System.out.println(fTerm);
    }

    public static void calcularIDF (int nDocs) {
        for (String sTerm : indiceInvertido.keySet()) {
            double idf = (double) Math.log(nDocs / indiceInvertido.get(sTerm).docId.size()) / Math.log(2);

            indiceInvertido.get(sTerm).setIDF(idf);

            //calcular peso de cada documento
            for (String sDoc : indiceInvertido.get(sTerm).docId.keySet()) {
                double peso = indiceInvertido.get(sTerm).docId.get(sDoc) * indiceInvertido.get(sTerm).getIDF();

                if(!longPesoDoc.containsKey(sDoc)) longPesoDoc.put(sDoc, 0.0);
                longPesoDoc.put(sDoc, longPesoDoc.get(sDoc) +  peso);
            }
        }
        for(String sDoc : longPesoDoc.keySet()) {
            longPesoDoc.put(sDoc, Math.sqrt(longPesoDoc.get(sDoc)));
        }
    }

    public static void imprimirLongPesoDoc() {
        FileWriter fichero = null;
        PrintWriter pw ;
        try
        {
            fichero = new FileWriter("longPesoDoc.txt");
            pw = new PrintWriter(fichero);

            for (String sDoc : longPesoDoc.keySet()) {
                //imprmir en fichero peso.txt
                pw.println(sDoc + " " + longPesoDoc.get(sDoc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void imprimirIndiceInvertido() {
        String path = "indiceInvertido.json";
        JsonContainer jsonContainer = new JsonContainer();
        List<ContainerDocId> aDocIDpeso = new ArrayList<ContainerDocId>();
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson =  new Gson();
            for(String sTerm : indiceInvertido.keySet()) {
                // jsonContainer.container.add(new Container(sTerm, ));
                aDocIDpeso = new ArrayList<ContainerDocId>();
                for(String sDoc : indiceInvertido.get(sTerm).docId.keySet()) {
                    aDocIDpeso.add(new ContainerDocId(sDoc, indiceInvertido.get(sTerm).docId.get(sDoc)));
                }
                jsonContainer.container.add(new Container(sTerm, indiceInvertido.get(sTerm).getIDF(), aDocIDpeso));
            }
            String jsonString = gson.toJson(jsonContainer);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

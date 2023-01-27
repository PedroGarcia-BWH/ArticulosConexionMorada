package es.uca.articulosconexionmorada.consulta;

import com.google.gson.Gson;
import es.uca.articulosconexionmorada.article.Article;
import es.uca.articulosconexionmorada.article.ArticleService;
import es.uca.articulosconexionmorada.container.Container;
import es.uca.articulosconexionmorada.container.ContainerDocId;
import es.uca.articulosconexionmorada.container.JsonContainer;
import es.uca.articulosconexionmorada.container.docIDpeso;
import es.uca.articulosconexionmorada.preprocessing.preprocesamiento;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class Consulta {
    private static preprocesamiento _preprocesamiento ;
    static HashMap<String, Double> longDocumento = new HashMap<String, Double>();
    static HashMap<String, docIDpeso> indiceInvertido = new HashMap<String, docIDpeso>();

    static HashMap<String, Double> docId = new HashMap<String, Double>();

    private List<Article> articles = new ArrayList<Article>();

    private ArticleService articleService;

    private int n;

    public Consulta(ArticleService articleService, int n) {
        this.articleService = articleService;
        this.n = n;
    }

    public  List<Article> Consulta(String sConsulta) throws IOException, URISyntaxException, InterruptedException {
        _preprocesamiento = new preprocesamiento();
        leerLongDocumento();
        leerIndiceInvertido();
        ArrayList<String> asPreprocesado =  _preprocesamiento.preprocessing(sConsulta);
        ranking(asPreprocesado);
        //sort docId
        List<Ranking> aRanking = sort();
        int i=0;
        //get articles
        for (Ranking ranking : aRanking) {
            //System.out.println(ranking.doc);
            Optional<Article> opArticle = articleService.findById(UUID.fromString(ranking.doc));
            if (opArticle.isPresent()) {
                articles.add(opArticle.get());
            }
            if(i==n-1) break;
        }

        return articles;
    }

    public static List<Ranking> sort() {
        List<String> aKeys = new ArrayList<String>(docId.keySet());
        List<Double> aValues = new ArrayList<Double>(docId.values());
        List<Ranking> sorted = new ArrayList<Ranking>();

        for (int i = 0; i < aKeys.size(); i++) {
            sorted.add(new Ranking(aKeys.get(i), aValues.get(i)));
        }


        Collections.sort(sorted, Comparator.comparing(Ranking::getPeso));
        Collections.reverse(sorted);
        return sorted;
    }

    public static void ranking(ArrayList<String> asPreprocesado) throws IOException { //preguntar antonio pag 61
        //recorremos el indice invertido
        for (String sTermino : asPreprocesado) {
            if(indiceInvertido.containsKey(sTermino)) {
                for(String sDocIdpeso : indiceInvertido.get(sTermino).docId.keySet()) {
                    //calculamos el peso
                    double dPeso = indiceInvertido.get(sTermino).docId.get(sDocIdpeso) * indiceInvertido.get(sTermino).getIDF();
                    //System.out.println("Peso: " + indiceInvertido.get(sTermino).docId.get(sDocIdpeso));
                    //añadimos el peso al docId
                    if(docId.containsKey(sDocIdpeso)) docId.put(sDocIdpeso, docId.get(sDocIdpeso) + dPeso);
                    else docId.put(sDocIdpeso, dPeso);
                }

            }
        }
        //System.out.println(docId);
        for(String sDocId : docId.keySet()) {
            //System.out.println(" Peso: " + docId.get(sDocId) + " Long: " + longDocumento.get(sDocId));
            //System.out.println("hola");
            docId.put(sDocId, docId.get(sDocId) / longDocumento.get(sDocId));
        }
        //System.out.println(indiceInvertido);
    }

    public static void leerLongDocumento() throws IOException {
        String filePath = "longPesoDoc.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                longDocumento.put(parts[0], Double.parseDouble(parts[1]));
            }
        }
    }

    public static void leerIndiceInvertido() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("indiceInvertido.json"));
        JsonContainer jsonContainer = gson.fromJson(br, JsonContainer.class);
        for(Container container : jsonContainer.container) {
            indiceInvertido.put(container.sTermino, new docIDpeso());
            indiceInvertido.get(container.sTermino).setIDF(container.IDF);
            for(ContainerDocId containerDocId : container.aDocIDpeso) {
                //System.out.println(containerDocId.iPeso);
                indiceInvertido.get(container.sTermino).docId.put(containerDocId.sDocID, containerDocId.iPeso);
            }
        }
        //System.out.println(indiceInvertido.get("formation").docId.get("000084578400037"));
    }

}

package main;

import main.documents.Dvd;
import main.documents.Document;
import main.documents.Livre;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Mediatheque {
    private Map<Integer, Abonne> abonnes;
    private Map<Integer, Document> documents;

    public Mediatheque() {
        abonnes = new HashMap<>();
        documents = new HashMap<>();
        initAbonnes();
        initDocuments();
    }

    private void initAbonnes() {
        abonnes.put(1, new Abonne(1, "Alice", LocalDate.of(2000, 5, 15)));
        abonnes.put(2, new Abonne(2, "Bob", LocalDate.of(2010, 3, 22)));
        abonnes.put(3, new Abonne(3, "Charlie", LocalDate.of(1995, 8, 30)));
    }

    private void initDocuments() {
        documents.put(101, new Dvd(101, "Film Action", true));
        documents.put(102, new Dvd(102, "Film Famille", false));

        documents.put(1, new Livre(1, "Livre Test", 300));
    }

    public Abonne getAbonne(int numero) {
        return abonnes.get(numero);
    }

    public Document getDocument(int numero) {
        return documents.get(numero);
    }
}